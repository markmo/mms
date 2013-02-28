package models;

import static utils.CollectionUtils.safe;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

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
@DiscriminatorValue("SCH")
@Audited
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Schema extends AuditedModel {

    @Id
    @GeneratedValue//(strategy = GenerationType.SEQUENCE, generator = "public.ds_schema_schema_id_seq_1")
    @javax.persistence.Column(name = "schema_id")
    public long id;

    @javax.persistence.Column(name = "schema_name")
    public String name;

    @ManyToOne
    @JoinColumn(name = "sandbox_id")
    public Sandbox sandbox;

    @ManyToOne
    @JoinColumn(name = "data_source_id")
    public DataSource dataSource;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schema")
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

    @SuppressWarnings("unchecked")
    public static List<Schema> findByDataSourceId(Long dataSourceId) {
        return JPA.em().createQuery(
                "select s from Schema s where s.dataSource.id = ?1"
        )
                .setParameter(1, dataSourceId)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public static List<Schema> findBySandboxId(Long sandboxId) {
        return JPA.em().createQuery(
                "select s from Schema s where s.sandbox.id = ?1"
        )
                .setParameter(1, sandboxId)
                .getResultList();
    }

    public static Schema findByName(String name, DataSource dataSource) {
        Schema schema = null;
        //try {
            @SuppressWarnings("unchecked")
            List<Schema> schemas = JPA.em().createQuery(
                    "select s from Schema s where s.name = ?1 and s.dataSource.id = ?2"
                    //,Schema.class)
                    )
                    .setParameter(1, name)
                    .setParameter(2, dataSource.id)
                    //.getSingleResult();

                    // getSingleResult results in IllegalArgumentException: Type
                    // specified for TypedQuery [models.Schema] is incompatible
                    // with query return type [interface java.util.Map]
                    // Could be a class loading issue?
                    // http://jacoblewallen.wordpress.com/2012/06/20/type-for-typedquery-incompatible-with-query-return-type/
                    // But the following works??
                    // TODO see if fixed in 2.1-final as was working with Play 2.0.4 and Scala 2.9.2
                    .setMaxResults(1)
                    .getResultList();

            if (schemas.size() > 0) {
                schema = schemas.get(0);
            }

        //} catch (NoResultException e) {
            // ignore
        //}
        return schema;
    }
}
