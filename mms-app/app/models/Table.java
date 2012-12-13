package models;

import static utils.CollectionUtils.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;
import play.data.validation.Constraints.*;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 10:51 AM
 */
@Entity
@javax.persistence.Table(name = "ds_table")
@Audited
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Table extends AuditedModel {

    @Id
    @GeneratedValue//(strategy = GenerationType.SEQUENCE, generator = "public.ds_table_table_id_seq")
    @javax.persistence.Column(name = "table_id")
    public long id;

    @javax.persistence.Column(name = "table_name")
    @Required
    public String name;

    @ManyToOne
    @JoinColumn(name = "schema_id")
    public Schema schema;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "table_type_id")
    public TableType tableType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "table_role_id")
    public TableRole role;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "table")
    @OrderBy("name")
    @JsonIgnore
    private Set<Column> columns;

    public Set<Column> getColumns() {
        return columns;
    }

    public void setColumns(Set<Column> columns) {
        for (Column column : safe(columns)) {
            column.table = this;
        }
        this.columns = columns;
    }

    public void addColumn(Column column) {
        if (columns == null) {
            columns = new HashSet<Column>();
        }
        column.table = this;
        columns.add(column);
    }

//    public static Finder<Long, Table> find = new Finder<Long, Table>(
//            Long.class, Table.class
//    );

    public static List<Table> findBySchemaId(Long schemaId) {
//        return find.where().eq("schema.id", schemaId).findList();
        return JPA.em()
                .createQuery("from Table t where t.schema.id = ?1",
                        Table.class)
                .setParameter(1, schemaId)
                .getResultList();
    }
}
