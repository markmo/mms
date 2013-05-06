package mms.common.models.business;

import java.io.Serializable;
import javax.persistence.*;

import mms.common.models.Datasource;

/**
 * User: markmo
 * Date: 23/04/13
 * Time: 11:43 AM
 */
@Embeddable
public class DatasourceStakeholderPK implements Serializable {

    private static final long serialVersionUID = -2172113655989973493L;

    @ManyToOne
    @JoinColumn(name = "datasource_id")
    private Datasource datasource;

    @ManyToOne
    @JoinColumn(name = "stakeholder_role_id")
    private StakeholderRole stakeholderRole;

    @ManyToOne
    @JoinColumn(name = "stakeholder_group_id")
    private StakeholderGroup stakeholderGroup;

    public Datasource getDatasource() {
        return datasource;
    }

    public void setDatasource(Datasource datasource) {
        this.datasource = datasource;
    }

    public StakeholderRole getStakeholderRole() {
        return stakeholderRole;
    }

    public void setStakeholderRole(StakeholderRole stakeholderRole) {
        this.stakeholderRole = stakeholderRole;
    }

    public StakeholderGroup getStakeholderGroup() {
        return stakeholderGroup;
    }

    public void setStakeholderGroup(StakeholderGroup stakeholderGroup) {
        this.stakeholderGroup = stakeholderGroup;
    }

    @Override
    public String toString() {
        return String.format("DatasourceStakeholderRolePK{Datasource=%s(%s); " +
                "StakeholderRole=%s(%s); StakeholderGroup=%s(%s)}",
                datasource.getName(), datasource.getId(),
                stakeholderRole.getName(), stakeholderRole.getId(),
                stakeholderGroup.getName(), stakeholderGroup.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatasourceStakeholderPK that = (DatasourceStakeholderPK) o;

        if (!datasource.equals(that.datasource)) return false;
        if (!stakeholderGroup.equals(that.stakeholderGroup)) return false;
        if (!stakeholderRole.equals(that.stakeholderRole)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = datasource.hashCode();
        result = 31 * result + stakeholderRole.hashCode();
        result = 31 * result + stakeholderGroup.hashCode();
        return result;
    }
}
