package models;

import play.data.validation.Constraints;

import javax.persistence.*;
import javax.persistence.Column;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 5:12 PM
 */
@Entity
@javax.persistence.Table(name="ds_data_type")
public class DataType extends AuditedModel {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public.ds_data_type_data_type_id_seq_1")
    @Column(name="data_type_id")
    public Long id;

    @Column(name="data_type_name")
    @Constraints.Required
    public String name;
}
