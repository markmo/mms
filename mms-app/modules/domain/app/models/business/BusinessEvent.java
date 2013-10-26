package models.domain.business;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;

import com.github.cleverage.elasticsearch.Indexable;

import models.domain.AuditedModel;

/**
 * User: markmo
 * Date: 1/07/13
 * Time: 6:07 PM
 */
@Entity
public class BusinessEvent extends AuditedModel implements Indexable {

    public enum SubjectType {
        CUSTOMER("Customer");

        private String name;

        SubjectType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum Target {
        AD("Ad"),
        PAGE("Page"),
        PRODUCT("Product");

        private String name;

        Target(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @Id
    @GeneratedValue
    @Column(name = "event_id")
    private Long id;

    @Column(name = "event_name")
    private String name;

    @Enumerated(EnumType.STRING)
    private SubjectType subjectType;

    @Enumerated(EnumType.STRING)
    private Target target;

    @Column(length = 8000)
    private String definition;

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

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @SuppressWarnings("unchecked")
    public Map toIndex() {
        HashMap map = new HashMap();
        map.put("id", id);
        map.put("name", name);
        map.put("definition", definition);
        if (subjectType != null) {
            map.put("subjectType", subjectType.getName());
        }
        if (target != null) {
            map.put("target", target.getName());
        }
        return map;
    }

    public Indexable fromIndex(Map map) {
        if (map == null) return this;
        name = (String)map.get("name");
        definition = (String)map.get("definition");
        subjectType = SubjectType.valueOf((String)map.get("subjectType"));
        target = Target.valueOf((String)map.get("target"));
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

        BusinessEvent that = (BusinessEvent) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
