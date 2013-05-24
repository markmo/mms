package mms.common.models.business;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.*;

/**
 * User: markmo
 * Date: 3/05/13
 * Time: 7:04 PM
 */
@Entity
public class BusinessTermStakeholderPerson {

    @EmbeddedId
    @JsonIgnore
    private BusinessTermStakeholderPersonPK pk;

    public BusinessTermStakeholderPersonPK getPk() {
        return pk;
    }

    public void setPk(BusinessTermStakeholderPersonPK pk) {
        this.pk = pk;
    }

    public Person getPerson() {
        return (pk != null) ? pk.getPerson() : null;
    }

    public StakeholderRole getStakeholderRole() {
        return (pk != null) ? pk.getStakeholderRole() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessTermStakeholderPerson that = (BusinessTermStakeholderPerson) o;

        if (pk != null ? !pk.equals(that.pk) : that.pk != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return pk != null ? pk.hashCode() : 0;
    }
}
