package controllers.account;

import java.util.*;

import be.objectify.deadbolt.java.actions.*;
import com.google.common.base.Joiner;
import org.code_factory.jpa.nestedset.*;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import scala.Tuple3;

import models.account.*;
import models.common.Page;

/**
 * User: markmo
 * Date: 8/06/13
 * Time: 4:16 PM
 */
public class SecuritySubjects extends Controller {

    @Transactional(readOnly = true)
    @Restrict({@Group(Application.ADMIN_ROLE), @Group(Application.SUPERADMIN_ROLE)})
    public static Result list(int pageIndex, String sortBy, String order,
                              String filterColumn, String filterValue) {
        NestedSetManager nsm = new JpaNestedSetManager(JPA.em());
        Set<String[]> closedNodes = getClosedNodes(getClosedNodes());
        Set<String> closedIds = getClosedIds(closedNodes);
        User localUser = Application.getLocalUser(session());
        Long organizationId = null;
        if (!localUser.isInRole("superadmin")) {
            organizationId = localUser.organization.id;
        }
        Page<SecuritySubject> page = SecuritySubject.page(pageIndex, 10, sortBy, order, filterColumn, filterValue, closedNodes, organizationId);
        List<Tuple3<Node<SecuritySubject>, SecuritySubject, Boolean>> list = new ArrayList<>();
        for (SecuritySubject subject : page.getList()) {
            int id = subject.getId();
            Boolean open = !closedIds.contains(String.valueOf(id));
            Node<SecuritySubject> node = nsm.getNode(subject);
            if (!isAncestorClosed(node, closedIds)) {
                list.add(new Tuple3<>(node, subject, open));
            }
        }
        Page<Tuple3<Node<SecuritySubject>, SecuritySubject, Boolean>> newPage =
                new Page<>(list, page.getTotalRowCount(), pageIndex, 10);
        return ok(
                views.html.subjects.render(newPage, sortBy, order, filterColumn, filterValue)
        );
    }

    private static boolean isAncestorClosed(Node node, Set<String> closedNodes) {
        if (node.hasParent()) {
            return closedNodes.contains(String.valueOf(node.getParent().getId())) ||
                    isAncestorClosed(node.getParent(), closedNodes);
        }
        return false;
    }

    private static Set<String> getClosedIds(Set<String[]> closedNodes) {
        if (closedNodes.isEmpty()) return Collections.emptySet();
        Set<String> closedIds = new HashSet<>();
        for (String[] bounds : closedNodes) {
            closedIds.add(bounds[0]);
        }
        return closedIds;
    }

    private static Set<String[]> getClosedNodes(Set<String> closedNodes) {
        if (closedNodes.isEmpty()) return Collections.emptySet();
        Set<String[]> closedSet = new HashSet<>();
        for (String bounds : closedNodes) {
            closedSet.add(bounds.split(","));
        }
        return closedSet;
    }

    private static Set<String> getClosedNodes() {
        String closed = session("closed");
        Set<String> closedSet;
        if (closed == null || closed.isEmpty()) {
            closedSet = new HashSet<>();
        } else {
            closedSet = new HashSet<>(Arrays.asList(closed.split(";")));
        }
        return closedSet;
    }

    @Transactional(readOnly = true)
    public static Result updateTreeDisplay(int nodeId, String bounds,
                                           boolean open, int pageIndex,
                                           String sortBy, String order,
                                           String filterColumn, String filterValue) {
        Set<String> closedSet = getClosedNodes();
//        String id = String.valueOf(nodeId);
        if (open) {
            closedSet.remove(bounds);
        } else {
            closedSet.add(bounds);
        }
        session("closed", Joiner.on(';').join(closedSet));
        return list(pageIndex, sortBy, order, filterColumn, filterValue);
    }

    @Transactional
    public static Result delete() {
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        if (params.containsKey("btnDelete")) {
            String[] deletions = params.get("deletions[]");
            for (int i = 0; i < deletions.length; i++) {
                int id = Integer.parseInt(deletions[i]);
                SecuritySubject subject = JPA.em().find(SecuritySubject.class, id);
                if (subject != null) {
                    subject.roles.clear();
                    if (subject instanceof User) {
                        TokenAction.deleteByUser((User)subject);
                    }
                    JPA.em().remove(subject);
                }
            }
            if (deletions.length == 1) {
                flash("success", "The selected user/group has been deleted");
            } else {
                flash("success", "The selected users/groups have been deleted");
            }
        } else if (params.containsKey("btnActivate")) {
            String[] activeUsers = params.get("activeUsers[]");
            String[] inactiveUsers = params.get("inactiveUsers[]");
            List<String> activations = Arrays.asList(params.get("activations[]"));
            // test for users that have been deactivated
            if (activeUsers != null) {
                for (String i : activeUsers) {
                    if (!activations.contains(i)) {
                        int id = Integer.parseInt(i);
                        User user = JPA.em().find(User.class, id);
                        if (user != null) {
                            user.active = false;
                            JPA.em().persist(user);
                        }
                    }
                }
            }
            // test for users that have been activated
            if (inactiveUsers != null) {
                for (String i : inactiveUsers) {
                    if (activations.contains(i)) {
                        int id = Integer.parseInt(i);
                        User user = JPA.em().find(User.class, id);
                        if (user != null) {
                            user.active = true;
                            JPA.em().persist(user);
                        }
                    }
                }
            }
            flash("success", "Activations have been successfully updated");
        }
        return redirect(routes.SecuritySubjects.list(0, "name", "asc", "", ""));
    }
}
