package models.domain.relational;

import static utils.JPA_Helper.getSingleResult;

import javax.persistence.*;

import org.hibernate.envers.Audited;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

import models.domain.AuditedModel;

/**
 * User: markmo
 * Date: 27/09/12
 * Time: 7:29 PM
 */
@Entity
@Audited
public class TableType extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "table_type_id")
    private Long id;

    @Column(name = "table_type_name")
    @Constraints.Required
    private String name;

    public static TableType findByName(String name) {
        return getSingleResult(TableType.class,
                JPA.em().createQuery(
                        "select t from TableType t where t.name = ?1"
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

        TableType tableType = (TableType) o;

        if (id != null ? !id.equals(tableType.id) : tableType.id != null) return false;
        if (name != null ? !name.equals(tableType.name) : tableType.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
