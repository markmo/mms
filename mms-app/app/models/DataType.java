package models;

import javax.persistence.*;
import javax.persistence.Column;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;
import play.data.validation.Constraints;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 5:12 PM
 */
@Entity
@javax.persistence.Table(name = "ds_data_type")
@Audited
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class DataType extends AuditedModel {

    @Id
    @GeneratedValue//(strategy = GenerationType.SEQUENCE, generator = "public.ds_data_type_data_type_id_seq_1")
    @Column(name = "data_type_id")
    public long id;

    @Column(name = "data_type_name")
    @Constraints.Required
    public String name;
}
