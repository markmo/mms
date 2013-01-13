package controllers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cleverage.elasticsearch.IndexQuery;
import com.github.cleverage.elasticsearch.IndexResults;
import com.github.cleverage.elasticsearch.IndexService;
import com.google.inject.Inject;
import indexing.TableIndex;
import models.Table;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.facet.FacetBuilders;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

/**
 * User: markmo
 * Date: 27/12/12
 * Time: 10:25 PM
 */
public class Search extends Controller {

    @Inject
    static ObjectMapper mapper;

    @Transactional(readOnly = true)
    public static Result index(String terms) throws IOException {
        List<Table> tables = JPA.em().createQuery("select t from Table t", Table.class).getResultList();
        TableIndex tableIndex = new TableIndex();
        tableIndex.tables = tables;
        IndexService.cleanIndex();
        tableIndex.index();
        IndexQuery<TableIndex> queryTables = TableIndex.find.query();
        queryTables.addFacet(FacetBuilders.termsFacet("tablesF").field("tables.name").nested("tables"));
        //queryTables.setBuilder(QueryBuilders.queryString(terms));
        IndexResults<TableIndex> results = TableIndex.find.search(queryTables);
        String json = mapper.writeValueAsString(results);
        return ok(json).as("application/json");
    }
}
