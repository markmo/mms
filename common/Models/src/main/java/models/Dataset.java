package models;

import static utils.JPA_Helper.getSingleResult;
import static utils.CollectionUtils.safe;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Table;
import java.util.*;

/**
 * User: markmo
 * Date: 26/02/13
 * Time: 6:32 PM
 *
 * A set of columns, representing either the result of a query, a view, or a physical table.
 *
 * Alias: ColumnSet
 */
@Entity
@Table(name = "dataset")
public class Dataset extends AuditedModel implements Indexable {

    @SuppressWarnings("unchecked")
    public static List<Dataset> findByNamespaceId(Long namespaceId) {
        return JPA.em().createQuery(
                "select s from Dataset s where s.namespace.id = ?1"
        )
                .setParameter(1, namespaceId)
                .getResultList();
    }

    public static Dataset findByName(String name, Namespace namespace) {
        return getSingleResult(Dataset.class,
                JPA.em().createQuery(
                        "select s from Dataset s where s.name = ?1 and s.namespace.id = ?2"
                )
                        .setParameter(1, name)
                        .setParameter(2, namespace.getId())
        );
    }

    @Id
    @GeneratedValue
    @Column(name = "dataset_id")
    private Long id;

    @Column(name = "dataset_name")
    @Constraints.Required
    private String name;

    @Column(length = 8000)
    private String description;

    private String url;

    @ManyToOne
    @JoinColumn(name = "security_class_id")
    private SecurityClassification securityClassification;

    @ManyToOne
    @JoinColumn(name = "namespace_id")
    private Namespace namespace;

    @ManyToMany
    @JoinTable(
            name = "mms_resource_datasets",
            joinColumns = @JoinColumn(name = "dataset_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id")
    )
    @JsonBackReference("resource_datasets")
    private Set<Resource> resources;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataset")
    @OrderBy("name")
    @JsonIgnore
    private List<AbstractColumn> columns;

    // Statistics
    private long rowCount;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SecurityClassification getSecurityClassification() {
        return securityClassification;
    }

    public void setSecurityClassification(SecurityClassification securityClassification) {
        this.securityClassification = securityClassification;
    }

    public Namespace getNamespace() {
        return namespace;
    }

    public void setNamespace(Namespace namespace) {
        this.namespace = namespace;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public void addResource(Resource resource) {
        if (resources == null) {
            resources = new HashSet<Resource>();
        }
        resources.add(resource);
    }

    public List<AbstractColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<AbstractColumn> columns) {
        for (AbstractColumn column : safe(columns)) {
            column.setDataset(this);
        }
        this.columns = columns;
    }

    public void addColumn(AbstractColumn column) {
        if (columns == null) {
            columns = new ArrayList<AbstractColumn>();
        }
        column.setDataset(this);
        columns.add(column);
    }

    public AbstractColumn getColumn(String name) {
        if (name == null || columns == null) {
            return null;
        }
        for (AbstractColumn column : columns) {
            if (column.getName().equals(name)) {
                return column;
            }
        }
        return null;
    }

    public long getRowCount() {
        return rowCount;
    }

    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }

    @Override
    public Map toIndex() {
        HashMap map = new HashMap();
        map.put("name", name);
        return map;
    }

    @Override
    public Indexable fromIndex(Map map) {
        if (map == null) {
            return this;
        }
        this.name = (String)map.get("name");
        return this;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dataset dataset = (Dataset) o;

        if (id != null ? !id.equals(dataset.id) : dataset.id != null) return false;
        if (name != null ? !name.equals(dataset.name) : dataset.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
