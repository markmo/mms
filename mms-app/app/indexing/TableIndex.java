package indexing;

import com.github.cleverage.elasticsearch.Index;
import com.github.cleverage.elasticsearch.IndexUtils;
import com.github.cleverage.elasticsearch.Indexable;
import com.github.cleverage.elasticsearch.annotations.IndexMapping;
import com.github.cleverage.elasticsearch.annotations.IndexType;
import models.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: markmo
 * Date: 27/12/12
 * Time: 9:25 PM
 */
@IndexType(name = "table_index")
@IndexMapping(value = "{tables: {properties: {tables: {type: \"nested\"}}}}")
public class TableIndex extends Index {

    public List<Table> tables = new ArrayList<Table>();

    public static Finder<TableIndex> find = new Finder<TableIndex>(TableIndex.class);

    @Override
    public Map toIndex() {
        HashMap map = new HashMap();
        map.put("tables", IndexUtils.toIndex(tables));
        return map;
    }

    @Override
    public Indexable fromIndex(Map map) {
        if (map == null) {
            return this;
        }
        this.tables = IndexUtils.getIndexables(map, "tables", Table.class);
        return this;
    }

}
