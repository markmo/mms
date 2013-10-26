package indexing.glossary;

import java.util.Map;

import com.github.cleverage.elasticsearch.Index;
import com.github.cleverage.elasticsearch.Indexable;
import com.github.cleverage.elasticsearch.annotations.IndexMapping;
import com.github.cleverage.elasticsearch.annotations.IndexType;
import play.db.jpa.JPA;

import models.domain.business.Tag;

/**
 * User: markmo
 * Date: 28/05/13
 * Time: 4:16 PM
 */
@IndexType(name = "tag")
@IndexMapping(value =
        "{columns: {" +
            "properties: {" +
                "objectType: {type: \"string\", index: \"not_analyzed\"}," +
                "id: {type: \"integer\"}," +
                "name: {type: \"string\", index: \"not_analyzed\"}" +
            "}}}")
public class TagIndex extends Index {

    private Tag tag;

    public static Finder<TagIndex> find = new Finder<>(TagIndex.class);

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public Map toIndex() {
        return tag.toIndex();
    }

    @Override
    public Indexable fromIndex(Map map) {
        if (map == null) return this;
        Long tagId = ((Integer)map.get("id")).longValue();
        Tag tag = JPA.em().find(Tag.class, tagId);
        this.tag = tag;
        tag.fromIndex(map);
        return this;
    }

    @Override
    public String toString() {
        return "Tag{name=\"" + tag.getName() + "\"}";
    }
}
