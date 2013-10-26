package models.domain;

import static utils.JPA_Helper.getSingleResult;

import javax.persistence.*;
import javax.persistence.Column;

import org.hibernate.envers.Audited;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 24/11/12
 * Time: 6:45 PM
 */
@Entity
@Audited
public class FilterType {

    @Id
    @GeneratedValue
    @Column(name = "filter_type_id")
    private Long id;

    @Column(name = "filter_type_name")
    @Constraints.Required
    private String name;

    public static FilterType findByName(String name) {
        return getSingleResult(FilterType.class,
                JPA.em().createQuery(
                        "select t from FilterType t where t.name = ?1"
                )
                        .setParameter(1, name)
        );
    }

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

        FilterType that = (FilterType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
