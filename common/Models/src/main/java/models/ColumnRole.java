package models;

import static utils.JPA_Helper.getSingleResult;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import play.db.jpa.JPA;

import javax.persistence.*;
import javax.persistence.Table;

import java.util.List;

/**
 * User: markmo
 * Date: 20/10/12
 * Time: 8:18 PM
 */
@Entity
@Table(name = "column_role")
@Audited
public class ColumnRole {

    public static ColumnRole findByName(String name) {
        return getSingleResult(ColumnRole.class,
                JPA.em().createQuery(
                        "select r from ColumnRole r where r.name = ?1"
                )
                        .setParameter(1, name)
        );
    }

    @Id
    @GeneratedValue
    @javax.persistence.Column(name = "column_role_id")
    private Long id;

    @javax.persistence.Column(name = "column_role_name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "role")
    @OrderBy("name")
    @JsonIgnore
    private List<Column> columns;

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

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColumnRole that = (ColumnRole) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
