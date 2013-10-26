package models.domain;

import static utils.CollectionUtils.safe;

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
import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 1:09 PM
 *
 * A named collection of datasets.
 *
 * Alias: Schema (Relational), Group (File)
 */
@Entity
@Audited
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Namespace extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "namespace_id")
    private Long id;

    @Column(name = "namespace_name")
    @Constraints.Required
    private String name;

    @ManyToOne
    @JoinColumn(name = "sandbox_id")
    private Sandbox sandbox;

    @ManyToOne
    @JoinColumn(name = "catalog_id")
    //@JsonBackReference("catalog_namespaces")
    private Catalog catalog;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "namespace")
    @OrderBy("name")
    //@JsonManagedReference("namespace_datasets")
    @JsonIgnore
    private Set<Dataset> datasets;

    @SuppressWarnings("unchecked")
    public static List<Namespace> findByCatalogId(Long catalogId) {
        return JPA.em().createQuery(
                "select n from Namespace n where n.catalog.id = ?1"
        )
                .setParameter(1, catalogId)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public static List<Namespace> findBySandboxId(Long sandboxId) {
        return JPA.em().createQuery(
                "select n from Namespace n where n.sandbox.id = ?1"
        )
                .setParameter(1, sandboxId)
                .getResultList();
    }

    public static Namespace findByName(String name, Catalog catalog) {
        Namespace namespace = null;
        //try {
        @SuppressWarnings("unchecked")
        List<Namespace> namespaces = JPA.em().createQuery(
                "select n from Namespace n where n.name = ?1 and n.catalog.id = ?2"
                //,Namespace.class)
        )
                .setParameter(1, name)
                .setParameter(2, catalog.getId())
                //.getSingleResult();

                // getSingleResult results in IllegalArgumentException: Type
                // specified for TypedQuery [models.domain.Namespace] is incompatible
                // with query return type [interface java.util.Map]
                // Could be a class loading issue?
                // http://jacoblewallen.wordpress.com/2012/06/20/type-for-typedquery-incompatible-with-query-return-type/
                // But the following works??
                // TODO see if fixed in 2.1-final as was working with Play 2.0.4 and Scala 2.9.2
                .setMaxResults(1)
                .getResultList();

        if (namespaces.size() > 0) {
            namespace = namespaces.get(0);
        }

        //} catch (NoResultException e) {
        // ignore
        //}
        return namespace;
    }

    public static Namespace parseNamespace(ObjectMapper mapper, JsonNode json, Catalog catalog) throws IOException {
        String name = json.path("name").asText();
        Namespace existingNamespace = findByName(name, catalog);
        Namespace namespace = (existingNamespace == null ? new Namespace() : existingNamespace);
        namespace.name = name;
        namespace.catalog = catalog;
        JPA.em().persist(namespace);
        Set<Dataset> tables = null;
        JsonNode tablesJson = json.path("datasets");
        if (tablesJson.size() > 0) {
            tables = new HashSet<Dataset>();
            Iterator<JsonNode> it = tablesJson.elements();
            while (it.hasNext()) {
                models.domain.relational.Table table = models.domain.relational.Table.parseTable(mapper, it.next(), namespace);
                if (table != null) {
                    tables.add(table);
                }
            }
        }
        namespace.setDatasets(tables);
        JPA.em().persist(namespace);
        return namespace;
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

    public Sandbox getSandbox() {
        return sandbox;
    }

    public void setSandbox(Sandbox sandbox) {
        this.sandbox = sandbox;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Set<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(Set<Dataset> datasets) {
        for (Dataset dataset : safe(datasets)) {
            dataset.setNamespace(this);
        }
        this.datasets = datasets;
    }

    public void addDataset(Dataset dataset) {
        if (datasets == null) {
            datasets = new HashSet<Dataset>();
        }
        dataset.setNamespace(this);
        datasets.add(dataset);
     }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Namespace namespace = (Namespace) o;

        if (id != null ? !id.equals(namespace.id) : namespace.id != null) return false;
        if (name != null ? !name.equals(namespace.name) : namespace.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
