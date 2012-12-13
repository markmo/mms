package models;

import static utils.CollectionUtils.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;

/**
 * User: markmo
 * Date: 20/10/12
 * Time: 4:06 PM
 */
@Entity
@javax.persistence.Table(name = "ds_data_source")
@Audited
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class DataSource extends AuditedModel {

    @Id
    @GeneratedValue//(strategy = GenerationType.SEQUENCE, generator = "public.ds_data_source_data_source_id_seq_1")
    @javax.persistence.Column(name = "data_source_id")
    public long id;

    @javax.persistence.Column(name = "data_source_name")
    public String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "dataSource")
    @OrderBy("name")
    @JsonIgnore
    private Set<Schema> schemas;

    public Set<Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(Set<Schema> schemas) {
        for (Schema schema : safe(schemas)) {
            schema.dataSource = this;
        }
        this.schemas = schemas;
    }

    public void addSchema(Schema schema) {
        if (schemas == null) {
            schemas = new HashSet<Schema>();
        }
        schema.dataSource = this;
        schemas.add(schema);
    }

//    public static Finder<Long, DataSource> find = new Finder<Long, DataSource>(
//            Long.class, DataSource.class
//    );
}
