package mms.common.models;

import javax.persistence.*;
import javax.persistence.Column;

import org.hibernate.envers.Audited;
import play.data.validation.Constraints;

/**
 * User: markmo
 * Date: 28/02/13
 * Time: 3:58 PM
 */
@Entity
@Audited
public class DatasourceType extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "datasource_type_id")
    private Long id;

    @Column(name = "datasource_type_name")
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

        DatasourceType that = (DatasourceType) o;

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
