package models.domain.business;

import static utils.JPA_Helper.getSingleResult;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;

import com.github.cleverage.elasticsearch.Indexable;
import play.db.jpa.JPA;

import models.domain.AuditedModel;

/**
 * User: markmo
 * Date: 22/04/13
 * Time: 2:47 PM
 */
@Entity
public class Tag extends AuditedModel implements Indexable {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "tag_name")
    private String name;

    public static Tag findByName(String name) {
        return getSingleResult(Tag.class,
                JPA.em().createQuery(
                        "select t from Tag t where t.name = ?1"
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

    @SuppressWarnings("unchecked")
    public Map toIndex() {
        HashMap map = new HashMap();
        map.put("id", id);
        map.put("objectType", "Tag");
        map.put("name", name);
        return map;
    }

    public Indexable fromIndex(Map map) {
        if (map == null) return this;
        name = (String)map.get("name");
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

        Tag tag = (Tag) o;

        if (id != null ? !id.equals(tag.id) : tag.id != null) return false;
        if (name != null ? !name.equals(tag.name) : tag.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
