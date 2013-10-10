package controllers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cleverage.elasticsearch.IndexQuery;
import com.github.cleverage.elasticsearch.IndexResults;
import com.github.cleverage.elasticsearch.IndexService;
import com.google.inject.Inject;
import indexing.*;
import org.elasticsearch.index.query.QueryBuilders;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import mms.common.models.Column;
import mms.common.models.Dataset;
import mms.common.models.business.BusinessTerm;
import mms.common.models.business.Tag;

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
//        IndexQuery<DatasetIndex> queryDatasets = DatasetIndex.find.query();
//        //queryDatasets.addFacet(FacetBuilders.termsFacet("columnF").field("columns.name").nested("columns"));
//        queryDatasets.setBuilder(QueryBuilders.queryString(terms));
//        IndexResults<DatasetIndex> results = DatasetIndex.find.search(queryDatasets);
//        String json = mapper.writeValueAsString(results);
//        return ok(json).as("application/json");
        return ok();
    }

    public static Result cleanIndex() {
        IndexService.cleanIndex();
        return ok("done");
    }

    @Transactional(readOnly = true)
    public static Result reindex() throws IOException {
        IndexService.cleanIndex();
//        List<Tag> tags = JPA.em().createQuery(
//                "select t from Tag t",
//                Tag.class
//        ).getResultList();
//        for (Tag tag : tags) {
//            TagIndex tagIndex = new TagIndex();
//            tagIndex.setTag(tag);
//            tagIndex.index();
//        }
//        List<Dataset> datasets = JPA.em().createQuery(
//                "select d from Dataset d",
//                Dataset.class
//        ).getResultList();
//        for (Dataset dataset : datasets) {
//            DatasetIndex datasetIndex = new DatasetIndex();
//            datasetIndex.setDataset(dataset);
//            datasetIndex.index();
//        }
//        List<Column> columns = JPA.em().createQuery(
//                "select c from Column c",
//                Column.class
//        ).getResultList();
//        for (Column column : columns) {
//            ColumnIndex columnIndex = new ColumnIndex();
//            columnIndex.setColumn(column);
//            columnIndex.index();
//        }
//        List<BusinessTerm> terms = JPA.em().createQuery(
//                "select t from BusinessTerm t",
//                BusinessTerm.class
//        ).getResultList();
//        for (BusinessTerm term : terms) {
//            BusinessTermIndex termIndex = new BusinessTermIndex();
//            termIndex.setBusinessTerm(term);
//            termIndex.index();
//        }
        return ok("done");
    }
}
