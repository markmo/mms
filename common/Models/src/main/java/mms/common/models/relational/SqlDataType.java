package mms.common.models.relational;

import static utils.JPA_Helper.getSingleResult;

import javax.persistence.*;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

import mms.common.models.AuditedModel;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 1:49 PM
 *
 * An SQLDataType is used to reference any datatype associated with a column.
 */
@Entity
@Table(name = "sql_datatype")
@Audited
public class SqlDataType extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "sql_datatype_id")
    private Long id;

    @Column(name = "sql_datatype_name")
    @Constraints.Required
    private String name;

    public static SqlDataType findByName(String name) {
        return getSingleResult(SqlDataType.class,
                JPA.em().createQuery(
                        "select t from SqlDataType t where t.name = ?1"
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

        SqlDataType that = (SqlDataType) o;

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
