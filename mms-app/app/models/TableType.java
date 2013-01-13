package models;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.*;
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
    @GeneratedValue//(strategy = GenerationType.SEQUENCE, generator = "public.ds_table_type_table_type_id_seq_1")
    @Column(name = "table_type_id")
    public long id;

    @Column(name = "table_type_name")
    @Constraints.Required
    public String name;

    public static TableType findByName(String name) {
        TableType tableType = null;
        try {
            tableType = JPA.em().createQuery("select t from TableType t where t.name = ?1",
                    TableType.class)
                    .setParameter(1, name)
                    .getSingleResult();
        } catch (NoResultException e) {
            // ignore
        }
        return tableType;
    }
}
