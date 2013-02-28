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
 * Time: 5:12 PM
 */
@Entity
@Table(name = "ds_data_type")
@Audited
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class DataType extends AuditedModel {

    @Id
    @GeneratedValue//(strategy = GenerationType.SEQUENCE, generator = "public.ds_data_type_data_type_id_seq_1")
    @Column(name = "data_type_id")
    public long id;

    @Column(name = "data_type_name")
    @Constraints.Required
    public String name;

    public static DataType findByName(String name) {
        return getSingleResult(DataType.class,
                JPA.em().createQuery(
                        "select t from DataType t where t.name = ?1"
                )
                        .setParameter(1, name)
        );
    }
}
