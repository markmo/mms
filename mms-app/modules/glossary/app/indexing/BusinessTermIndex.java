package indexing;

import java.util.Map;

import com.github.cleverage.elasticsearch.Index;
import com.github.cleverage.elasticsearch.Indexable;
import com.github.cleverage.elasticsearch.annotations.IndexMapping;
import com.github.cleverage.elasticsearch.annotations.IndexType;
import play.db.jpa.JPA;

import models.domain.business.BusinessTerm;

/**
 * User: markmo
 * Date: 27/05/13
 * Time: 6:13 PM
 */
@IndexType(name = "business_term")
@IndexMapping(value =
        "{columns: {" +
            "properties: {" +
                "id: {type: \"integer\"}," +
                "objectType: {type: \"string\", index: \"not_analyzed\"}," +
                "name: {type: \"string\"}," +
                "definition: {type: \"string\"}," +
                "description: {type: \"string\"}," +
                "domain: {type: \"string\", index: \"not_analyzed\"}," +
                "tags: {type: \"nested\", properties: {" +
                    "id: {type: \"integer\"}," +
                    "name: {type: \"string\", index: \"not_analyzed\"}" +
                "}}," +
                "conceptType: {type: \"string\", index: \"not_analyzed\"}," +
                "securityClassification: {type: \"string\", index: \"not_analyzed\"}," +
                "columns: {type: \"nested\", properties: {" +
                    "id: {type: \"integer\"}," +
                    "name: {type: \"string\", index: \"not_analyzed\"}" +
                "}}," +
                "datasource: {type: \"string\", index: \"not_analyzed\"}," +
                "stakeholders: {type: \"nested\", properties: {" +
                    "id: {type: \"integer\"}," +
                    "name: {type: \"string\", index: \"not_analyzed\"}" +
                "}}" +
            "}}}")
public class BusinessTermIndex extends Index {

    private BusinessTerm businessTerm;

    public static Finder<BusinessTermIndex> find = new Finder<BusinessTermIndex>(BusinessTermIndex.class);

    public void setBusinessTerm(BusinessTerm businessTerm) {
        this.businessTerm = businessTerm;
    }

    @Override
    public Map toIndex() {
        return businessTerm.toIndex();
    }

    @Override
    public Indexable fromIndex(Map map) {
        if (map == null) return this;
        Long termId = ((Integer)map.get("id")).longValue();
        BusinessTerm term = JPA.em().find(BusinessTerm.class, termId);
        this.businessTerm = term;
        term.fromIndex(map);
        return this;
    }

    @Override
    public String toString() {
        return "BusinessTerm{name=\"" + businessTerm.getName() + "\"}";
    }
}
