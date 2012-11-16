package models;

import java.util.List;

import play.data.validation.Constraints.*;
import play.db.jpa.JPA;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 10:51 AM
 */
@Entity
@javax.persistence.Table(name="ds_table")
public class Table extends AuditedModel {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public.ds_table_table_id_seq")
    @javax.persistence.Column(name="table_id")
    public Long id;

    @javax.persistence.Column(name="table_name")
    @Required
    public String name;

    @ManyToOne
    @JoinColumn(name="schema_id")
    public Schema schema;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="table_type_id")
    public TableType tableType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="table_role_id")
    public TableRole role;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "table")
//    @OrderBy("name")
//    public List<Column> columns;

//    public static Finder<Long, Table> find = new Finder<Long, Table>(
//            Long.class, Table.class
//    );

    public static List<Table> findBySchemaId(Long schemaId) {
//        return find.where().eq("schema.id", schemaId).findList();
        Query q = JPA.em().createQuery("from Table t where t.schema.id = ?1");
        q.setParameter(1, schemaId);
        return q.getResultList();
    }
}
