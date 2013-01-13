package models;

import javax.persistence.*;
import javax.persistence.Column;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 5:12 PM
 */
@Entity
@javax.persistence.Table(name = "ds_data_type")
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
        DataType dataType = null;
        try {
            dataType = JPA.em().createQuery("select t from DataType t where t.name = ?1",
                    DataType.class)
                    .setParameter(1, name)
                    .getSingleResult();
        } catch (NoResultException e) {
            // ignore
        }
        return dataType;
    }
}
