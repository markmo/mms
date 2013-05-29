package mms.common.models.business;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.*;
import com.github.cleverage.elasticsearch.Indexable;

/**
 * User: markmo
 * Date: 3/05/13
 * Time: 7:04 PM
 */
@Entity
public class BusinessTermStakeholderPerson implements Indexable {

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

    @SuppressWarnings("unchecked")
    public Map toIndex() {
        HashMap map = new HashMap();
        map.put("objectType", "Responsibility");
        if (pk != null) {
            map.put("person", getPerson().getName());
            map.put("role", getStakeholderRole().getName());
        }
        return map;
    }

    public Indexable fromIndex(Map map) {
        return this;
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
