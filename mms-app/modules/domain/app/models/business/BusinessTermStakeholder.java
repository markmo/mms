package models.domain.business;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 22/04/13
 * Time: 3:00 PM
 */
@Entity
public class BusinessTermStakeholder {

    @EmbeddedId
    private BusinessTermStakeholderPK pk;

    public BusinessTermStakeholderPK getPk() {
        return pk;
    }

    public void setPk(BusinessTermStakeholderPK pk) {
        this.pk = pk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessTermStakeholder that = (BusinessTermStakeholder) o;

        if (pk != null ? !pk.equals(that.pk) : that.pk != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return pk != null ? pk.hashCode() : 0;
    }
}
