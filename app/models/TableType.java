package models;

import play.data.validation.Constraints;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 27/09/12
 * Time: 6:27 PM
 */
@Entity
@javax.persistence.Table(name="ds_table_type")
public class TableType extends AuditedModel {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public.ds_table_type_table_type_id_seq_1")
    @javax.persistence.Column(name="table_type_id")
    public Long id;

    @javax.persistence.Column(name="table_type_name")
    @Constraints.Required
    public String name;
}
