package mms.common.models.business;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 27/05/13
 * Time: 5:21 PM
 */
@Entity
public class ConceptType {

    @Id
    @GeneratedValue
    @Column(name = "concept_type_id")
    private Long id;

    @Column(name = "concept_type_name")
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

        ConceptType that = (ConceptType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
