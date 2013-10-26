package models.domain;

import static utils.JPA_Helper.getSingleResult;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.persistence.Column;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.envers.Audited;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 2/11/12
 * Time: 12:46 PM
 * <p/>
 * A Catalog is the unit of logon and identification. It also identifies
 * the scope of SQL statements: the tables contained in a catalog can be
 * used in a single SQL statement.
 */
@Entity
@Audited
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Catalog extends AuditedModel {

    public static Catalog findByName(String name) {
        return getSingleResult(Catalog.class,
                JPA.em().createQuery(
                        "select c from Catalog c where c.name = ?1"
                )
                        .setParameter(1, name)
        );
    }

    @SuppressWarnings("unchecked")
    public static List<Catalog> findByDatasourceId(Long datasourceId) {
        return JPA.em().createQuery(
                "select c from Catalog c where c.datasource.id = ?1"
        )
                .setParameter(1, datasourceId)
                .getResultList();
    }

    public static Catalog parseCatalog(ObjectMapper mapper, JsonNode json) throws IOException {
        String name = json.path("name").asText();
        Catalog existingCatalog = findByName(name);
        Catalog catalog = (existingCatalog == null ? new Catalog() : existingCatalog);
        catalog.name = name;
        JPA.em().persist(catalog);
        Set<Namespace> namespaces = null;
        JsonNode namespacesJson = json.path("namespaces");
        if (namespacesJson.size() > 0) {
            namespaces = new HashSet<Namespace>();
            Iterator<JsonNode> it = namespacesJson.elements();
            while (it.hasNext()) {
                Namespace namespace = Namespace.parseNamespace(mapper, it.next(), catalog);
                if (namespace != null) {
                    namespaces.add(namespace);
                }
            }
        }
        catalog.setNamespaces(namespaces);
        JPA.em().persist(catalog);
        return catalog;
    }

    @Id
    @GeneratedValue
    @Column(name = "catalog_id")
    private Long id;

    @Column(name = "catalog_name")
    private String name;

    @Column(length = 8000)
    private String description;

    private String defaultCharacterSetName;

    private String defaultCollationName;

    @ManyToOne
    @JoinColumn(name = "datasource_id")
    //@JsonBackReference("datasource_catalogs")
    private Datasource datasource;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catalog")
    @OrderBy("name")
    //@JsonManagedReference("catalog_namespaces")
    @JsonIgnore
    private Set<Namespace> namespaces;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The name of the default character set used for the values in the column.
     * This field applies only to columns whose datatype is a character string.
     *
     * @return defaultCharacterSetName
     */
    public String getDefaultCharacterSetName() {
        return defaultCharacterSetName;
    }

    public void setDefaultCharacterSetName(String defaultCharacterSetName) {
        this.defaultCharacterSetName = defaultCharacterSetName;
    }

    /**
     * The name of the default collation sequence used to sort the data values
     * in the column. This applies only to columns whose datatype is a form of
     * character string.
     *
     * @return defaultCollationName
     */
    public String getDefaultCollationName() {
        return defaultCollationName;
    }

    public void setDefaultCollationName(String defaultCollationName) {
        this.defaultCollationName = defaultCollationName;
    }

    public Datasource getDatasource() {
        return datasource;
    }

    public void setDatasource(Datasource datasource) {
        this.datasource = datasource;
    }

    public Set<Namespace> getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(Set<Namespace> namespaces) {
        this.namespaces = namespaces;
    }

    public void addNamespace(Namespace namespace) {
        if (namespaces == null) {
            namespaces = new HashSet<Namespace>();
        }
        namespaces.add(namespace);
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Catalog catalog = (Catalog) o;

        if (id != null ? !id.equals(catalog.id) : catalog.id != null) return false;
        if (name != null ? !name.equals(catalog.name) : catalog.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
