package models;

import play.data.validation.Constraints;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 5:13 PM
 */
@Entity
@javax.persistence.Table(name="ds_sql_data_type")
public class SqlDataType extends AuditedModel {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public.ds_sql_data_type_sql_data_type_id_seq_1")
    @javax.persistence.Column(name="sql_data_type_id")
    public Long id;

    @javax.persistence.Column(name="sql_data_type_name")
    @Constraints.Required
    public String name;
}
