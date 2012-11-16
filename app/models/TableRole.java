package models;

import javax.persistence.*;
import java.util.List;

/**
 * User: markmo
 * Date: 20/10/12
 * Time: 4:12 PM
 */
@Entity
@javax.persistence.Table(name="ds_table_role")
public class TableRole extends AuditedModel {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public.ds_table_role_table_role_id_seq_1")
    @javax.persistence.Column(name="table_role_id")
    public Long id;

    @javax.persistence.Column(name="table_role_name")
    public String name;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "role")
//    @OrderBy("name")
//    public List<Table> tables;
}
