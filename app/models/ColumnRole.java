package models;

import javax.persistence.*;
import java.util.List;

/**
 * User: markmo
 * Date: 20/10/12
 * Time: 4:15 PM
 */
@Entity
@javax.persistence.Table(name="ds_column_role")
public class ColumnRole extends AuditedModel {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public.ds_column_role_column_role_id_seq_1")
    @javax.persistence.Column(name="column_role_id")
    public Long id;

    @javax.persistence.Column(name="column_role_name")
    public String name;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "role")
//    @OrderBy("name")
//    public List<Column> columns;
}
