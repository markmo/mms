package mms.common.models.business;

import static utils.JPA_Helper.getSingleResult;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import play.db.jpa.JPA;

import mms.common.models.*;
import mms.common.models.Column;

/**
 * User: markmo
 * Date: 22/04/13
 * Time: 2:36 PM
 */
@Entity
public class BusinessTerm {

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
    @JoinColumn(name = "security_class_id")
    private SecurityClassification securityClassification;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "businessTerm")
    private Set<Column> representations;

    @ManyToOne
    @JoinColumn(name = "datasource_of_record_id")
    private Datasource datasourceOfRecord;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pk.businessTerm")
    private Set<BusinessTermStakeholder> stakeholders;

    @javax.persistence.Column(length = 8000)
    private String customMetadata;

    public static BusinessTerm findByName(String name) {
        return getSingleResult(BusinessTerm.class,
                JPA.em().createQuery(
                        "select t from BusinessTerm t where t.name = ?1"
                )
                        .setParameter(1, name)
        );
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

    public String getCustomMetadata() {
        return customMetadata;
    }

    public void setCustomMetadata(String customMetadata) {
        this.customMetadata = customMetadata;
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
