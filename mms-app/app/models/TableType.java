package models;

import static controllers.Application.getSingleResult;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.*;

import org.hibernate.envers.Audited;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 27/09/12
 * Time: 6:27 PM
 */
@Entity
@Table(name = "ds_table_type")
@Audited
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class TableType extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "table_type_id")
    public long id;

    @Column(name = "table_type_name")
    @Constraints.Required
    public String name;

    public static TableType findByName(String name) {
        return getSingleResult(TableType.class,
                JPA.em().createQuery(
                        "select t from TableType t where t.name = ?1"
                )
                        .setParameter(1, name)
        );
    }
}
