package indexing;

import java.util.Map;

import com.github.cleverage.elasticsearch.Index;
import com.github.cleverage.elasticsearch.Indexable;
import com.github.cleverage.elasticsearch.annotations.IndexMapping;
import com.github.cleverage.elasticsearch.annotations.IndexType;
import play.db.jpa.JPA;

import mms.common.models.Column;

/**
 * User: markmo
 * Date: 27/12/12
 * Time: 9:25 PM
 */
@IndexType(name = "column")
@IndexMapping(value =
        "{columns: {" +
            "properties: {" +
                "dataset: {type: \"nested\"}," +
                "id: {type: \"integer\"}," +
                "columnType: {type: \"string\"}," +
                "dataType: {type: \"string\"}," +
                "name: {type: \"string\"}" +
            "}}}")
public class ColumnIndex extends Index {

    private Column column;

    public static Finder<ColumnIndex> find = new Finder<>(ColumnIndex.class);

    public void setColumn(Column column) {
        this.column = column;
    }

    @Override
    public Map toIndex() {
        return column.toIndex();
    }

    @Override
    public Indexable fromIndex(Map map) {
        if (map == null) {
            return this;
        }
        Long columnId = ((Integer)map.get("id")).longValue();
        Column column = JPA.em().find(Column.class, columnId);
        this.column = column;
        column.fromIndex(map);
        return this;
    }

    @Override
    public String toString() {
        return "Column{name=\"" + column.getName() + "\"}";
    }

}
