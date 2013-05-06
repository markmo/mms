package mms.common.models.business;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 23/04/13
 * Time: 11:49 AM
 */
@Entity
public class DatasourceUserGroup {

    @EmbeddedId
    private DatasourceUserGroupPK pk;

    @ManyToOne
    @JoinColumn(name = "alias_id")
    private Alias alias;

    public DatasourceUserGroupPK getPk() {
        return pk;
    }

    public void setPk(DatasourceUserGroupPK pk) {
        this.pk = pk;
    }

    public Alias getAlias() {
        return alias;
    }

    public void setAlias(Alias alias) {
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatasourceUserGroup that = (DatasourceUserGroup) o;

        if (pk != null ? !pk.equals(that.pk) : that.pk != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return pk != null ? pk.hashCode() : 0;
    }
}