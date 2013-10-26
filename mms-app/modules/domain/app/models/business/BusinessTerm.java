package models.domain.business;

import static utils.JPA_Helper.getSingleResult;

import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import com.github.cleverage.elasticsearch.IndexUtils;
import com.github.cleverage.elasticsearch.Indexable;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.code_factory.jpa.nestedset.NodeInfo;
import org.code_factory.jpa.nestedset.annotations.*;
import play.db.jpa.JPA;

import models.domain.*;
import models.domain.Column;

/**
 * User: markmo
 * Date: 22/04/13
 * Time: 2:36 PM
 */
@Entity
public class BusinessTerm extends AuditedModel implements Indexable, NodeInfo {

    @Id
    @GeneratedValue
    @javax.persistence.Column(name = "business_term_id")
    private Long id;

    @javax.persistence.Column(name = "business_term_name")
    private String name;

    @javax.persistence.Column(length = 8000)
    private String definition;

    @javax.persistence.Column(length = 8000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonManagedReference("term_hierarchy")
    private BusinessTerm parent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    @JsonBackReference("term_hierarchy")
    @JsonIgnore
    private List<BusinessTerm> children;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @ManyToMany
    @JoinTable(
            name = "business_term_tags",
            joinColumns = @JoinColumn(name = "business_term_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "concept_type_id")
    private ConceptType conceptType;

    @ManyToOne
    @JoinColumn(name = "security_class_id")
    private SecurityClassification securityClassification;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "businessTerm")
    private Set<Column> representations;

    @ManyToOne
    @JoinColumn(name = "datasource_of_record_id")
    private Datasource datasourceOfRecord;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pk.businessTerm")
    private Set<BusinessTermStakeholder> stakeholders;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pk.businessTerm")
    private Set<BusinessTermStakeholderPerson> people;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pk.businessTerm")
    private Set<AccessPrivileges> accessPrivileges;

    @javax.persistence.Column(length = 8000)
    private String customMetadata;

    @LeftColumn
    private int lft;

    @RightColumn
    private int rgt;

    @LevelColumn
    private int level;

    @RootColumn
    private int rootId;

    public static BusinessTerm findByName(String name) {
        return getSingleResult(BusinessTerm.class,
                JPA.em().createQuery(
                        "select t from BusinessTerm t where t.name = ?1"
                )
                        .setParameter(1, name)
        );
    }

    public int getId() {
        return id == null ? null : id.intValue();
    }

    public Long getIdAsLong() {
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

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BusinessTerm getParent() {
        return parent;
    }

    public void setParent(BusinessTerm parent) {
        this.parent = parent;
    }

    public List<BusinessTerm> getChildren() {
        return children;
    }

    public void setChildren(List<BusinessTerm> children) {
        this.children = children;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public ConceptType getConceptType() {
        return conceptType;
    }

    public void setConceptType(ConceptType conceptType) {
        this.conceptType = conceptType;
    }

    public SecurityClassification getSecurityClassification() {
        return securityClassification;
    }

    public void setSecurityClassification(SecurityClassification securityClassification) {
        this.securityClassification = securityClassification;
    }

    public Set<Column> getRepresentations() {
        return representations;
    }

    public void setRepresentations(Set<Column> representations) {
        this.representations = representations;
    }

    public Datasource getDatasourceOfRecord() {
        return datasourceOfRecord;
    }

    public void setDatasourceOfRecord(Datasource datasourceOfRecord) {
        this.datasourceOfRecord = datasourceOfRecord;
    }

    public Set<BusinessTermStakeholder> getStakeholders() {
        return stakeholders;
    }

    public void setStakeholders(Set<BusinessTermStakeholder> stakeholders) {
        this.stakeholders = stakeholders;
    }

    public Set<BusinessTermStakeholderPerson> getPeople() {
        return people;
    }

    public void setPeople(Set<BusinessTermStakeholderPerson> people) {
        this.people = people;
    }

    public Set<AccessPrivileges> getAccessPrivileges() {
        return accessPrivileges;
    }

    public void setAccessPrivileges(Set<AccessPrivileges> accessPrivileges) {
        this.accessPrivileges = accessPrivileges;
    }

    public String getCustomMetadata() {
        return customMetadata;
    }

    public void setCustomMetadata(String customMetadata) {
        this.customMetadata = customMetadata;
    }

    @Override
    public int getLeftValue() {
        return this.lft;
    }

    @Override
    public int getRightValue() {
        return this.rgt;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLeftValue(int value) {
        this.lft = value;
    }

    @Override
    public void setRightValue(int value) {
        this.rgt = value;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getRootValue() {
        return this.rootId;
    }

    @Override
    public void setRootValue(int value) {
        this.rootId = value;
    }

    @SuppressWarnings("unchecked")
    public Map toIndex() {
        HashMap map = new HashMap();
        map.put("id", id);
        map.put("url", "terms/" + id);
        map.put("objectType", "Business Term");
        map.put("name", name);
        map.put("definition", definition);
        map.put("description", description);
        if (domain != null)
            map.put("domain", domain.getName());
        if (tags != null && !tags.isEmpty()) {
//            List<String> tagWords = Lists.transform(new ArrayList(tags), new Function<Tag, String>() {
//                public String apply(Tag tag) {return tag.getName();}
//            });
//            map.put("tags", Joiner.on(' ').join(tagWords));
            map.put("tags", IndexUtils.toIndex(new ArrayList(tags)));
        }
        if (conceptType != null)
            map.put("conceptType", conceptType.getName());
        if (securityClassification != null)
            map.put("securityClassification", securityClassification.getName());
        if (representations != null && !representations.isEmpty())
            map.put("columns", IndexUtils.toIndex(new ArrayList(representations)));
        if (datasourceOfRecord != null)
            map.put("datasource", datasourceOfRecord.getName());
        if (people != null && !people.isEmpty())
            map.put("stakeholders", IndexUtils.toIndex(new ArrayList(people)));
        return map;
    }

    public Indexable fromIndex(Map map) {
        if (map == null) return this;
        name = (String)map.get("name");
        definition = (String)map.get("definition");
        description = (String)map.get("description");
        List<Tag> tags1 = IndexUtils.getIndexables(map, "tags", Tag.class);
//        String strTags = (String)map.get("tags");
//        String[] tagWords = strTags.split(" ");
//        List<Tag> tags1 = Lists.transform(Arrays.asList(tagWords), new Function<String, Tag>() {
//            public Tag apply(String name) {return Tag.findByName(name);}
//        });
        setTags(new HashSet<>(tags1));
        List<Column> columns = IndexUtils.getIndexables(map, "columns", Column.class);
        setRepresentations(new HashSet<>(columns));
        List<BusinessTermStakeholderPerson> stakeholders =
                IndexUtils.getIndexables(map, "stakeholders", BusinessTermStakeholderPerson.class);
        setPeople(new HashSet<>(stakeholders));
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

        BusinessTerm that = (BusinessTerm) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
