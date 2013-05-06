package mms.common.models.business;

import java.io.Serializable;
import javax.persistence.*;

/**
 * User: markmo
 * Date: 22/04/13
 * Time: 3:01 PM
 */
@Embeddable
public class BusinessTermStakeholderPK implements Serializable {

    private static final long serialVersionUID = -8490665220728836747L;

    @ManyToOne
    @JoinColumn(name = "business_term_id")
    private BusinessTerm businessTerm;

    @ManyToOne
    @JoinColumn(name = "stakeholder_role_id")
    private StakeholderRole stakeholderRole;

    @ManyToOne
    @JoinColumn(name = "stakeholder_group_id")
    private StakeholderGroup stakeholderGroup;

    @Override
    public String toString() {
        return String.format("BusinessTermStakeholderPK{BusinessTerm=%s(%s); " +
                "StakeholderRole=%s(%s); StakeholderGroup=%s(%s)}",
                businessTerm.getName(), businessTerm.getId(),
                stakeholderRole.getName(), stakeholderRole.getId(),
                stakeholderGroup.getName(), stakeholderGroup.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessTermStakeholderPK that = (BusinessTermStakeholderPK) o;

        if (!businessTerm.equals(that.businessTerm)) return false;
        if (!stakeholderGroup.equals(that.stakeholderGroup)) return false;
        if (!stakeholderRole.equals(that.stakeholderRole)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = businessTerm.hashCode();
        result = 31 * result + stakeholderRole.hashCode();
        result = 31 * result + stakeholderGroup.hashCode();
        return result;
    }
}
