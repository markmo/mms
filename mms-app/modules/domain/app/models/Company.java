package models.domain;

import javax.persistence.Column;
import javax.persistence.*;

import org.hibernate.envers.Audited;
import play.data.validation.Constraints;

/**
 * User: markmo
 * Date: 28/02/13
 * Time: 3:36 PM
 */
@Entity
@Audited
public class Company extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "company_id")
    private Long id;

    @Column(name = "company_name")
    @Constraints.Required
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (id != null ? !id.equals(company.id) : company.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
