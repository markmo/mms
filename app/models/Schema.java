package models;

import com.avaje.ebean.Ebean;
import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.List;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 5:11 PM
 */
@Entity
@javax.persistence.Table(name="ds_schema")
public class Schema extends AuditedModel {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public.ds_schema_schema_id_seq_1")
    @javax.persistence.Column(name="schema_id")
    public Long id;

    @javax.persistence.Column(name="schema_name")
    public String name;

    @ManyToOne
    @JoinColumn(name="data_source_id")
    public DataSource dataSource;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "schema")
//    @OrderBy("name")
//    public List<Table> tables;

//    public static Finder<Long, Schema> find = new Finder<Long, Schema>(
//            Long.class, Schema.class
//    );

    public static List<Schema> findByDataSourceId(Long dataSourceId) {
//        return find.where().eq("dataSource.id", dataSourceId).findList();
        Query q = JPA.em().createQuery("from Schema s where s.dataSource.id = ?1");
        q.setParameter(1, dataSourceId);
        return q.getResultList();
    }
}
