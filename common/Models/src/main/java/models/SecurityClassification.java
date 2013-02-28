package models;

import org.hibernate.envers.Audited;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 * User: markmo
 * Date: 26/02/13
 * Time: 6:34 PM
 */
@Entity
@Table(name = "security_classification")
@Audited
public class SecurityClassification {

    @Id
    @GeneratedValue
    @Column(name = "security_class_id")
    private Long id;

    @Column(name = "security_class_name")
    @Constraints.Required
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

        SecurityClassification that = (SecurityClassification) o;

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
