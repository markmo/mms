package mms.common.models;

import static utils.BeanUtils.copyProperties;
import static utils.CollectionUtils.safe;
import static utils.JPA_Helper.getSingleResult;

import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import com.github.cleverage.elasticsearch.IndexUtils;
import com.github.cleverage.elasticsearch.Indexable;
import org.hibernate.envers.Audited;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

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
@Audited
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public abstract class Dataset extends AuditedModel implements Indexable {

    @Id
    @GeneratedValue
    @javax.persistence.Column(name = "dataset_id")
    private Long id;

    @javax.persistence.Column(name = "dataset_name")
    @Constraints.Required
    private String name;

    @javax.persistence.Column(length = 8000)
    private String description;

    private String url;

    @ManyToOne
    @JoinColumn(name = "security_class_id")
    private SecurityClassification securityClassification;

    @ManyToOne
    @JoinColumn(name = "namespace_id")
    //@JsonBackReference("namespace_datasets")
    private Namespace namespace;

    @ManyToMany
    @JoinTable(
            name = "resource_datasets",
            joinColumns = @JoinColumn(name = "dataset_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id")
    )
    @JsonBackReference("resource_datasets")
    private Set<Resource> resources;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataset")
    @OrderBy("name")
    //@JsonManagedReference("dataset_columns")
    @JsonIgnore
    private List<AbstractColumn> columns;

    /*
    code
    // source details
    purpose
    scope
    coverage
    collectionMethod, e.g. Survey, Administrative Data, Census, Registry
    // data details
    conceptualFramework
    mainOutputs
    classifications
    otherConcepts
    accuracy - sources of error, e.g. processing error, coding error; If survey: sample size, % of population sampled, response rate, and sampling error; areas where careful interpretation is required
    geographicalAreas
    groups
    comments
    collectionFrequency
    frequencyComments
    collectionHistory
    dataAvailability
    dataAvailabilityComments
    // source and reference attribute
    organizationName - of unit responsible for the data
    custodianName - data owner
    referenceDocument
    // contact
    contactName
        position
        email
        phone
    responsibleOfficer
        position
        email
        phone
    // templates
    templateCode

    collectionMethods
    collectionHistory
    availableCrossTabulations
    internationalComparability
        country
        dataSources
        relatedWebSites
    nationalComparability
    comments
        Known quality issues
        Sample size
        Response rate
        Blank fields
        Social desirability bias
        Breaks in series
    glossary
    steward
    contact
    referenceDocuments
     */


    // Statistics
    private long rowCount;

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

    public static Dataset update(Dataset partialDataset) {
        if (partialDataset.id == null) {
            JPA.em().persist(partialDataset);
            return partialDataset;
        } else {
            Dataset dataset = JPA.em().find(partialDataset.getClass(), partialDataset.id);
            final String[] includedProperties = new String[]{
                    "name",
                    "description",
                    "url"
            };
            copyProperties(partialDataset, dataset, includedProperties);
            JPA.em().persist(dataset);
            return dataset;
        }
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

    @SuppressWarnings("unchecked")
    public Map toIndex() {
        HashMap map = new HashMap();
        map.put("id", id);
        map.put("objectType", "Dataset");
        map.put("dataType", "Complex");
        map.put("name", name);
        map.put("description", description);
        if (columns != null) {
            map.put("columns", IndexUtils.toIndex(columns));
        }
        return map;
    }

    public Indexable fromIndex(Map map) {
        if (map == null) {
            return this;
        }
        name = (String)map.get("name");
        description = (String)map.get("description");
        String columnType = (String)map.get("columnType");
        if ("column".equals(columnType)) {
            List<Column> columns1 = IndexUtils.getIndexables(map, "columns", Column.class);
            for (Column column : columns1) {
                addColumn(column);
            }
        }
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
