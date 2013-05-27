package mms.common.models.business;

import java.util.regex.Pattern;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * User: markmo
 * Date: 17/05/13
 * Time: 4:30 PM
 */
@Entity
public class AccessPrivileges {

    @EmbeddedId
    private AccessPrivilegesPK pk;

    private String access;

    public AccessPrivilegesPK getPk() {
        return pk;
    }

    public void setPk(AccessPrivilegesPK pk) {
        this.pk = pk;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        if (access != null && !access.isEmpty()) {
            String lower = access.toLowerCase();
            if (lower.matches("^(c|r|u|d)+$")) {
                this.access = lower;
            }
        }
    }

    public boolean hasAccess(String rights) {
        if (rights == null || rights.isEmpty()) return false;
        for (Character r : rights.toLowerCase().toCharArray()) {
            if (access.indexOf(r) == -1) return false;
        }
        return true;
    }

    public boolean hasCreateAccess() {
        return hasAccess("c");
    }

    public boolean hasReadAccess() {
        return hasAccess("r");
    }

    public boolean hasUpdateAccess() {
        return hasAccess("u");
    }

    public boolean hasDeleteAccess() {
        return hasAccess("d");
    }

    public UserGroup getUserGroup() {
        return (pk != null) ? pk.getUserGroup() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessPrivileges that = (AccessPrivileges) o;

        if (pk != null ? !pk.equals(that.pk) : that.pk != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return pk != null ? pk.hashCode() : 0;
    }
}
