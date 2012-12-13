package nz.co.datascience.mms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User: markmo
 * Date: 20/10/12
 * Time: 8:56 PM
 */
public class ColumnRoleFactory {

    private static List<ColumnRole> roles;

    private static void initialize() {
        roles = new ArrayList<ColumnRole>();
    }

    static {
        initialize();
    }

    public static ColumnRole getRole(String roleName) {
        if (roleName == null) {
            return null;
        }
        for (ColumnRole role : roles) {
            if (role.getName().equals(roleName)) {
                return role;
            }
        }
        ColumnRole role = new ColumnRole();
        role.setName(roleName);
        roles.add(role);
        return role;
    }
}
