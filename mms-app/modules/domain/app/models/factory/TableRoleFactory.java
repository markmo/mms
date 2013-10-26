package models.domain.factory;

import java.util.ArrayList;
import java.util.List;

import models.domain.relational.TableRole;

/**
 * User: markmo
 * Date: 21/10/12
 * Time: 3:54 PM
 */
public class TableRoleFactory {

    private static List<TableRole> roles;

    private static void initialize() {
        roles = new ArrayList<TableRole>();
    }

    static {
        initialize();
    }

    public static TableRole getRole(String roleName) {
        if (roleName == null) {
            return null;
        }
        for (TableRole role : roles) {
            if (role.getName().equals(roleName)) {
                return role;
            }
        }
        TableRole role = new TableRole();
        role.setName(roleName);
        roles.add(role);
        return role;
    }
}
