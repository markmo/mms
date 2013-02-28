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
 * Date: 26/09/12
 * Time: 5:13 PM
 */
@Entity
@Table(name = "ds_sql_data_type")
@Audited
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class SqlDataType extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "sql_data_type_id")
    public long id;

    @Column(name = "sql_data_type_name")
    @Constraints.Required
    public String name;

    public static SqlDataType findByName(String name) {
        return getSingleResult(SqlDataType.class,
                JPA.em().createQuery(
                        "select t from SqlDataType t where t.name = ?1"
                )
                        .setParameter(1, name)
        );
    }
}
