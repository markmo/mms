package mms.common.models.business;

import java.io.Serializable;
import javax.persistence.*;

import mms.common.models.Datasource;

/**
 * User: markmo
 * Date: 23/04/13
 * Time: 11:49 AM
 */
@Embeddable
public class DatasourceUserGroupPK implements Serializable {

    private static final long serialVersionUID = 2485354788927024799L;

    @ManyToOne
    @JoinColumn(name = "datasource_id")
    private Datasource datasource;

    @ManyToOne
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    public Datasource getDatasource() {
        return datasource;
    }

    public void setDatasource(Datasource datasource) {
        this.datasource = datasource;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    @Override
    public String toString() {
        return String.format("DatasourceUserGroupPK{Datasource=%s(%s); " +
                "UserGroup=%s(%s)}",
                datasource.getName(), datasource.getId(),
                userGroup.getName(), userGroup.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatasourceUserGroupPK that = (DatasourceUserGroupPK) o;

        if (!datasource.equals(that.datasource)) return false;
        if (!userGroup.equals(that.userGroup)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = datasource.hashCode();
        result = 31 * result + userGroup.hashCode();
        return result;
    }
}
