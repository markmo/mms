package mms.common.models.business;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

/**
 * User: markmo
 * Date: 17/05/13
 * Time: 4:30 PM
 */
@Embeddable
public class AccessPrivilegesPK implements Serializable {

    private static final long serialVersionUID = -5345050914584919391L;

    @ManyToOne
    @JoinColumn(name = "business_term_id")
    @JsonIgnore
    private BusinessTerm businessTerm;

    @ManyToOne
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    public BusinessTerm getBusinessTerm() {
        return businessTerm;
    }

    public void setBusinessTerm(BusinessTerm businessTerm) {
        this.businessTerm = businessTerm;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    @Override
    public String toString() {
        return String.format("AccessPrivilegesPK{BusinessTerm=%s(%s); " +
                "UserGroup=%s(%s)}",
                businessTerm.getName(), businessTerm.getId(),
                userGroup.getName(), userGroup.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessPrivilegesPK that = (AccessPrivilegesPK) o;

        if (!businessTerm.equals(that.businessTerm)) return false;
        if (!userGroup.equals(that.userGroup)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = businessTerm.hashCode();
        result = 31 * result + userGroup.hashCode();
        return result;
    }
}
