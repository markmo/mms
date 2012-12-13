package models;

import javax.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;

/**
 * User: markmo
 * Date: 20/10/12
 * Time: 4:12 PM
 */
@Entity
@javax.persistence.Table(name = "ds_table_role")
@Audited
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class TableRole extends AuditedModel {

    @Id
    @GeneratedValue//(strategy = GenerationType.SEQUENCE, generator = "public.ds_table_role_table_role_id_seq_1")
    @javax.persistence.Column(name = "table_role_id")
    public long id;

    @javax.persistence.Column(name = "table_role_name")
    public String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "role")
    @OrderBy("name")
    @JsonIgnore
    public List<Table> tables;
}
