package models.domain.relational;

import static utils.JPA_Helper.getSingleResult;

import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

import models.domain.AuditedModel;

/**
 * User: markmo
 * Date: 20/10/12
 * Time: 8:16 PM
 */
@Entity
@Audited
public class TableRole extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "table_role_id")
    private Long id;

    @Column(name = "table_role_name")
    @Constraints.Required
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "role")
    @OrderBy("name")
    @JsonIgnore
    private List<Table> tables;

    public static TableRole findByName(String name) {
        return getSingleResult(TableRole.class,
                JPA.em().createQuery(
                        "select r from TableRole r where r.name = ?1"
                )
                        .setParameter(1, name)
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableRole tableRole = (TableRole) o;

        if (id != null ? !id.equals(tableRole.id) : tableRole.id != null) return false;
        if (name != null ? !name.equals(tableRole.name) : tableRole.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
