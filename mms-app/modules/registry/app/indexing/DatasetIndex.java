package indexing;

import java.util.Map;

import com.github.cleverage.elasticsearch.Index;
import com.github.cleverage.elasticsearch.Indexable;
import com.github.cleverage.elasticsearch.annotations.IndexMapping;
import com.github.cleverage.elasticsearch.annotations.IndexType;
import play.db.jpa.JPA;

import mms.common.models.Dataset;

/**
 * User: markmo
 * Date: 27/12/12
 * Time: 9:25 PM
 */
@IndexType(name = "dataset")
@IndexMapping(value =
        "{columns: {" +
            "properties: {" +
                "columns: {type: \"nested\"}," +
                "id: {type: \"integer\"}," +
                "name: {type: \"string\"}," +
                "description: {type: \"string\"}," +
                "objectType: {type: \"string\", index: \"not_analyzed\"}," +
                "dataType: {type: \"string\", index: \"not_analyzed\"}" +
            "}}}")
public class DatasetIndex extends Index {

    private Dataset dataset;

    public static Finder<DatasetIndex> find = new Finder<>(DatasetIndex.class);

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    @Override
    public Map toIndex() {
        return dataset.toIndex();
    }

    @Override
    public Indexable fromIndex(Map map) {
        if (map == null) {
            return this;
        }
        Long datasetId = ((Integer)map.get("id")).longValue();
        Dataset dataset = JPA.em().find(Dataset.class, datasetId);
        this.dataset = dataset;
        dataset.fromIndex(map);
        return this;
    }

    @Override
    public String toString() {
        return "Dataset{name=\"" + dataset.getName() + "\"}";
    }
}
