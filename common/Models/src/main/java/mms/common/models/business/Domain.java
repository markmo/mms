package mms.common.models.business;

import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

import mms.common.models.AuditedModel;

/**
 * User: markmo
 * Date: 22/04/13
 * Time: 2:42 PM
 */
@Entity
public class Domain extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "domain_id")
    private Long id;

    @Column(name = "domain_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonManagedReference("domain_hierarchy")
    private Domain parent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    @JsonBackReference("domain_hierarchy")
    @JsonIgnore
    private List<Domain> children;

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

    public Domain getParent() {
        return parent;
    }

    public void setParent(Domain parent) {
        this.parent = parent;
    }

    public List<Domain> getChildren() {
        return children;
    }

    public void setChildren(List<Domain> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Domain domain = (Domain) o;

        if (id != null ? !id.equals(domain.id) : domain.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
