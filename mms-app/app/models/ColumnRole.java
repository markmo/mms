package models;

import static controllers.Application.getSingleResult;

import java.util.List;
import javax.persistence.Table;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 20/10/12
 * Time: 4:15 PM
 */
@Entity
@Table(name = "ds_column_role")
@Audited
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class ColumnRole extends AuditedModel {

    @Id
    @GeneratedValue//(strategy = GenerationType.SEQUENCE, generator = "public.ds_column_role_column_role_id_seq_1")
    @javax.persistence.Column(name = "column_role_id")
    public long id;

    @javax.persistence.Column(name = "column_role_name")
    public String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "role")
    @OrderBy("name")
    @JsonIgnore
    public List<Column> columns;

    public static ColumnRole findByName(String name) {
        return getSingleResult(ColumnRole.class,
                JPA.em().createQuery(
                        "select r from ColumnRole r where r.name = ?1"
                )
                        .setParameter(1, name)
        );
    }
}
