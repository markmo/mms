package models;

import static utils.CollectionUtils.*;

import javax.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.*;
import com.github.cleverage.elasticsearch.Indexable;
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
@DiscriminatorValue("TAB")
@Audited

// can't be interpreted by Backbone
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")

public class Table extends AuditedModel implements Indexable {

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

    public int rowCount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "table")
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

    @Override
    public Map toIndex() {
        HashMap map = new HashMap();
        map.put("name", name);
        return map;
    }

    @Override
    public Indexable fromIndex(Map map) {
        if (map == null) {
            return this;
        }
        this.name = (String)map.get("name");
        return this;
    }

//    public static Finder<Long, Table> find = new Finder<Long, Table>(
//            Long.class, Table.class
//    );

    public static List<Table> findBySchemaId(Long schemaId) {
//        return find.where().eq("schema.id", schemaId).findList();
        return JPA.em()
                .createQuery("select t from Table t where t.schema.id = ?1",
                        Table.class)
                .setParameter(1, schemaId)
                .getResultList();
    }

    public static Table findByName(String name, Schema schema) {
        Table table = null;
        try {
            table = JPA.em().createQuery("select t from Table t where t.name = ?1 and t.schema = ?2",
                    Table.class)
                    .setParameter(1, name)
                    .setParameter(2, schema)
                    .getSingleResult();
        } catch (NoResultException e) {
            // ignore
        }
        return table;
    }
}
