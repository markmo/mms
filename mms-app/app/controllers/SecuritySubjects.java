package controllers;

import java.util.*;

import com.google.common.base.Joiner;
import org.code_factory.jpa.nestedset.*;
import org.code_factory.jpa.nestedset.Node;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import scala.Tuple3;

import models.SecuritySubject;
import models.Page;

/**
 * User: markmo
 * Date: 8/06/13
 * Time: 4:16 PM
 */
public class SecuritySubjects extends Controller {

    @Transactional(readOnly = true)
    public static Result list(int pageIndex, String sortBy, String order, String filter) {
        NestedSetManager nsm = new JpaNestedSetManager(JPA.em());
        Page<SecuritySubject> page = SecuritySubject.page(pageIndex, 10, sortBy, order, filter);
        List<Tuple3<Node<SecuritySubject>, SecuritySubject, Boolean>> list = new ArrayList<>();
        Set<String> closedNodes = getClosedNodes();
        for (SecuritySubject subject : page.getList()) {
            int id = subject.getId();
            Boolean open = !closedNodes.contains(String.valueOf(id));
            Node<SecuritySubject> node = nsm.getNode(subject);
            if (!isAncestorClosed(node, closedNodes)) {
                list.add(new Tuple3<>(node, subject, open));
            }
        }
        Page<Tuple3<Node<SecuritySubject>, SecuritySubject, Boolean>> newPage =
                new Page<>(list, page.getTotalRowCount(), pageIndex, 10);
        return ok(
                views.html.subjects.render(newPage, sortBy, order, filter)
        );
    }

    private static boolean isAncestorClosed(Node node, Set<String> closedNodes) {
        if (node.hasParent()) {
            return closedNodes.contains(String.valueOf(node.getParent().getId())) ||
                    isAncestorClosed(node.getParent(), closedNodes);
        }
        return false;
    }

    private static Set<String> getClosedNodes() {
        String closed = session("closed");
        Set<String> closedSet;
        if (closed == null) {
            closedSet = new HashSet<>();
        } else {
            closedSet = new HashSet<>(Arrays.asList(closed.split(",")));
        }
        return closedSet;
    }

    @Transactional(readOnly = true)
    public static Result updateTreeDisplay(int nodeId, boolean open, int pageIndex,
                                           String sortBy, String order,
                                           String filter) {
        Set<String> closedSet = getClosedNodes();
        String id = String.valueOf(nodeId);
        if (open) {
            closedSet.remove(id);
        } else {
            closedSet.add(id);
        }
        session("closed", Joiner.on(',').join(closedSet));
        return list(pageIndex, sortBy, order, filter);
    }

    public static Result delete() {
        return ok();
    }
}
