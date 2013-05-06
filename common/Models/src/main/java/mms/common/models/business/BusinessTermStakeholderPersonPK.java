package mms.common.models.business;

import java.io.Serializable;
import javax.persistence.*;

/**
 * User: markmo
 * Date: 3/05/13
 * Time: 7:00 PM
 */
@Embeddable
public class BusinessTermStakeholderPersonPK implements Serializable {

    private static final long serialVersionUID = 3048514470429071047L;

    @ManyToOne
    @JoinColumn(name = "business_term_id")
    private BusinessTerm businessTerm;

    @ManyToOne
    @JoinColumn(name = "stakeholder_role_id")
    private StakeholderRole stakeholderRole;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Override
    public String toString() {
        return String.format("BusinessTermStakeholderPK{BusinessTerm=%s(%s); " +
                "StakeholderRole=%s(%s); Person=%s(%s)}",
                businessTerm.getName(), businessTerm.getId(),
                stakeholderRole.getName(), stakeholderRole.getId(),
                person.getName(), person.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessTermStakeholderPersonPK that = (BusinessTermStakeholderPersonPK) o;

        if (!businessTerm.equals(that.businessTerm)) return false;
        if (!person.equals(that.person)) return false;
        if (!stakeholderRole.equals(that.stakeholderRole)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = businessTerm.hashCode();
        result = 31 * result + stakeholderRole.hashCode();
        result = 31 * result + person.hashCode();
        return result;
    }
}
