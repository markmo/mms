package models;

import static utils.JPA_Helper.getSingleResult;
import static utils.CollectionUtils.safe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.envers.Audited;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.IOException;
import java.util.*;

/**
 * User: markmo
 * Date: 20/10/12
 * Time: 8:13 PM
 */
@Entity
@Table(name = "datasource")
@DiscriminatorValue("DSC")
@Audited
public class Datasource extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "datasource_id")
    private Long id;

    @Column(name = "datasource_name")
    @Constraints.Required
    private String name;

    @Column(length = 8000)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "datasource")
    @OrderBy("name")
    @JsonIgnore
    private Set<Catalog> catalogs;

    public static Datasource findByName(String name) {
        return getSingleResult(Datasource.class,
                JPA.em().createQuery(
                        "select s from Datasource s where s.name = ?1"
                )
                        .setParameter(1, name)
        );
    }

    public static Datasource parseDatasource(ObjectMapper mapper, JsonNode json) throws IOException {
        String name = json.path("name").getTextValue();
        Datasource existingDatasource = findByName(name);
        Datasource datasource = (existingDatasource == null ? new Datasource() : existingDatasource);
        datasource.name = name;
        JPA.em().persist(datasource);
        Set<Catalog> cats = null;
        JsonNode catsJson = json.path("catalogs");
        if (catsJson.size() > 0) {
            cats = new HashSet<Catalog>();
            Iterator<JsonNode> it = catsJson.getElements();
            while (it.hasNext()) {
                Catalog catalog = Catalog.parseCatalog(mapper, it.next());
                if (catalog != null) {
                    cats.add(catalog);
                }
            }
        }
        datasource.setCatalogs(cats);
        JPA.em().persist(datasource);
        return datasource;
    }

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

    public Set<Catalog> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(Set<Catalog> catalogs) {
        for (Catalog catalog : safe(catalogs)) {
            catalog.setDatasource(this);
        }
        this.catalogs = catalogs;
    }

    public void addCatalog(Catalog catalog) {
        if (catalogs == null) {
            catalogs = new HashSet<Catalog>();
        }
        catalog.setDatasource(this);
        catalogs.add(catalog);
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Datasource that = (Datasource) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
