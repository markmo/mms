package controllers;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cleverage.elasticsearch.IndexQuery;
import com.github.cleverage.elasticsearch.IndexResults;
import com.google.inject.Inject;
import indexing.DatasetIndex;
import org.elasticsearch.index.query.QueryBuilders;
import play.db.jpa.Transactional;
import play.mvc.*;

/**
 * User: markmo
 * Date: 27/12/12
 * Time: 10:25 PM
 */
public class Search extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    public Result index(String terms) throws IOException {
        IndexQuery<DatasetIndex> queryDatasets = DatasetIndex.find.query();
        //queryDatasets.addFacet(FacetBuilders.termsFacet("columnF").field("columns.name").nested("columns"));
        queryDatasets.setBuilder(QueryBuilders.queryString(terms));
        IndexResults<DatasetIndex> results = DatasetIndex.find.search(queryDatasets);
        String json = mapper.writeValueAsString(results);
        return ok(json).as("application/json");
    }
}
