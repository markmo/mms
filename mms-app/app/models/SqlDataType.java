package models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;
import play.data.validation.Constraints;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 5:13 PM
 */
@Entity
@javax.persistence.Table(name = "ds_sql_data_type")
@Audited
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class SqlDataType extends AuditedModel {

    @Id
    @GeneratedValue//(strategy = GenerationType.SEQUENCE, generator = "public.ds_sql_data_type_sql_data_type_id_seq_1")
    @javax.persistence.Column(name = "sql_data_type_id")
    public long id;

    @javax.persistence.Column(name = "sql_data_type_name")
    @Constraints.Required
    public String name;
}
