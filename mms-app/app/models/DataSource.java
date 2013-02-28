package models;

import static controllers.Application.getSingleResult;
import static utils.CollectionUtils.safe;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 20/10/12
 * Time: 4:06 PM
 */
@Entity
@Table(name = "ds_data_source")
@DiscriminatorValue("DSC")
@Audited
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class DataSource extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "data_source_id")
    public long id;

    @Column(name = "data_source_name")
    public String name;

    public String shortDescription;

    @Column(length = 8000)
    public String longDescription;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataSource")
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

    public static DataSource findByName(String name) {
        return getSingleResult(DataSource.class,
                JPA.em().createQuery(
                        "select s from DataSource s where s.name = ?1"
                )
                        .setParameter(1, name)
        );
    }
}
