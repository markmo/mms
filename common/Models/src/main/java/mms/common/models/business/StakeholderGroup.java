package mms.common.models.business;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 22/04/13
 * Time: 2:53 PM
 */
@Entity
public class StakeholderGroup {

    @Id
    @GeneratedValue
    @Column(name = "stakeholder_group_id")
    private Long id;

    @Column(name = "stakeholder_group_name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StakeholderGroup that = (StakeholderGroup) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
