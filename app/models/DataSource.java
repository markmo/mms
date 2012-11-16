package models;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 20/10/12
 * Time: 4:06 PM
 */
@Entity
@javax.persistence.Table(name="ds_data_source")
public class DataSource extends AuditedModel {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public.ds_data_source_data_source_id_seq_1")
    @javax.persistence.Column(name="data_source_id")
    public Long id;

    @javax.persistence.Column(name="data_source_name")
    public String name;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "dataSource")
//    @OrderBy("name")
//    public List<Schema> schemas;

//    public static Finder<Long, DataSource> find = new Finder<Long, DataSource>(
//            Long.class, DataSource.class
//    );
}
