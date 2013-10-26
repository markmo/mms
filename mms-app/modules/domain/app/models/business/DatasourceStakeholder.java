package models.domain.business;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * User: markmo
 * Date: 23/04/13
 * Time: 11:42 AM
 */
@Entity
public class DatasourceStakeholder {

    @EmbeddedId
    private DatasourceStakeholderPK pk;

    public DatasourceStakeholderPK getPk() {
        return pk;
    }

    public void setPk(DatasourceStakeholderPK pk) {
        this.pk = pk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatasourceStakeholder that = (DatasourceStakeholder) o;

        if (pk != null ? !pk.equals(that.pk) : that.pk != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return pk != null ? pk.hashCode() : 0;
    }
}
