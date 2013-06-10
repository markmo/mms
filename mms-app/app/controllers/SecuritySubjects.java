package controllers;

import java.util.*;

import com.google.common.base.Joiner;
import org.code_factory.jpa.nestedset.*;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import scala.Tuple3;

import models.AbstractSubject;
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
        Page<AbstractSubject> page = AbstractSubject.page(pageIndex, 10, sortBy, order, filter);
        List<Tuple3<Node<AbstractSubject>, AbstractSubject, Boolean>> list = new ArrayList<>();
        Set<String> closedNodes = getClosedNodes();
        for (AbstractSubject subject : page.getList()) {
            int id = subject.getId();
            Boolean open = !closedNodes.contains(String.valueOf(id));
            Node<AbstractSubject> node = nsm.getNode(subject);
            if (!isAncestorClosed(node, closedNodes)) {
                list.add(new Tuple3<>(node, subject, open));
            }
        }
        Page<Tuple3<Node<AbstractSubject>, AbstractSubject, Boolean>> newPage =
                new Page<>(list, page.getTotalRowCount(), pageIndex, 10);
        return ok(
                views.html.subjects.render(newPage, sortBy, order, filter)
        );
    }

    @Transactional(readOnly = true)
    public static Result updateTreeDisplay(int nodeId, int open, int pageIndex,
                                           String sortBy, String order,
                                           String filter) {
        Set<String> closedSet = getClosedNodes();
        String id = String.valueOf(nodeId);
        if (open == 1) {
            closedSet.remove(id);
        } else {
            closedSet.add(id);
        }
        session("closed", Joiner.on(',').join(closedSet));
        return list(pageIndex, sortBy, order, filter);
    }

    private static boolean isAncestorClosed(Node node, Set<String> closedNodes) {
        return node.hasParent() &&
                (closedNodes.contains(String.valueOf(node.getParent().getId())) ||
                 isAncestorClosed(node.getParent(), closedNodes));
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

    public static Result delete() {
        return ok();
    }
}
