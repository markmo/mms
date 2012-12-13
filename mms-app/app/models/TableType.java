package models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;
import play.data.validation.Constraints;

/**
 * User: markmo
 * Date: 27/09/12
 * Time: 6:27 PM
 */
@Entity
@javax.persistence.Table(name = "ds_table_type")
@Audited
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class TableType extends AuditedModel {

    @Id
    @GeneratedValue//(strategy = GenerationType.SEQUENCE, generator = "public.ds_table_type_table_type_id_seq_1")
    @javax.persistence.Column(name = "table_type_id")
    public long id;

    @javax.persistence.Column(name = "table_type_name")
    @Constraints.Required
    public String name;
}
