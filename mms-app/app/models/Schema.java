package models;

import static utils.CollectionUtils.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 5:11 PM
 */
@Entity
@javax.persistence.Table(name = "ds_schema")
@Audited
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Schema extends AuditedModel {

    @Id
    @GeneratedValue//(strategy = GenerationType.SEQUENCE, generator = "public.ds_schema_schema_id_seq_1")
    @javax.persistence.Column(name = "schema_id")
    public long id;

    @javax.persistence.Column(name = "schema_name")
    public String name;

    @ManyToOne
    @JoinColumn(name = "data_source_id")
    public DataSource dataSource;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "schema")
    @OrderBy("name")
    @JsonIgnore
    private Set<Table> tables;

    public Set<Table> getTables() {
        return tables;
    }

    public void setTables(Set<Table> tables) {
        for (Table table : safe(tables)) {
            table.schema = this;
        }
        this.tables = tables;
    }

    public void addTable(Table table) {
        if (tables == null) {
            tables = new HashSet<Table>();
        }
        table.schema = this;
        tables.add(table);
    }

//    public static Finder<Long, Schema> find = new Finder<Long, Schema>(
//            Long.class, Schema.class
//    );

    public static List<Schema> findByDataSourceId(Long dataSourceId) {
//        return find.where().eq("dataSource.id", dataSourceId).findList();
        return JPA.em()
                .createQuery(
                        "from Schema s where s.dataSource.id = ?1",
                        Schema.class)
                .setParameter(1, dataSourceId)
                .getResultList();
    }
}
