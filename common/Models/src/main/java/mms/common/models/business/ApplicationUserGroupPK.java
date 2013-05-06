package mms.common.models.business;

import java.io.Serializable;
import javax.persistence.*;

/**
 * User: markmo
 * Date: 23/04/13
 * Time: 11:18 AM
 */
@Embeddable
public class ApplicationUserGroupPK implements Serializable {

    private static final long serialVersionUID = 6586011138194981461L;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    @Override
    public String toString() {
        return String.format("ApplicationUserGroupPK{Application=%s(%s); " +
                "UserGroup=%s(%s)}",
                application.getName(), application.getId(),
                userGroup.getName(), userGroup.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationUserGroupPK that = (ApplicationUserGroupPK) o;

        if (!application.equals(that.application)) return false;
        if (!userGroup.equals(that.userGroup)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = application.hashCode();
        result = 31 * result + userGroup.hashCode();
        return result;
    }
}
