package models.domain;

import static utils.JPA_Helper.getSingleResult;

import javax.persistence.Column;
import javax.persistence.*;

import org.hibernate.envers.Audited;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 24/09/12
 * Time: 4:07 PM
 */
@Entity
@Table(name = "datatype")
@Audited
public class DataType extends AuditedModel {

    enum Type {
        STRING
        ,DATE
        ,NUMERIC
        ,BOOLEAN
        ,ERROR
    }

    public static DataType findByName(String name) {
        return getSingleResult(DataType.class,
                JPA.em().createQuery(
                        "select t from DataType t where t.name = ?1"
                )
                        .setParameter(1, name)
        );
    }

    @Id
    @GeneratedValue
    @Column(name = "datatype_id")
    private Long id;

    @Column(name = "datatype_name")
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

        DataType dataType = (DataType) o;

        if (id != null ? !id.equals(dataType.id) : dataType.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
