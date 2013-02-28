package models;

import static controllers.Application.getSingleResult;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 20/10/12
 * Time: 4:12 PM
 */
@Entity
@javax.persistence.Table(name = "ds_table_role")
@Audited
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class TableRole extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "table_role_id")
    public long id;

    @Column(name = "table_role_name")
    public String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "role")
    @OrderBy("name")
    @JsonIgnore
    public List<Table> tables;

    public static TableRole findByName(String name) {
        return getSingleResult(TableRole.class,
                JPA.em().createQuery(
                        "select r from TableRole r where r.name = ?1"
                )
                        .setParameter(1, name)
        );
    }
}
