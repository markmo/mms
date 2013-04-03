package controllers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import mms.common.models.Catalog;

/**
 * User: markmo
 * Date: 4/11/12
 * Time: 1:39 PM
 */
@Api(value = "/catalogs", listingPath = "/api-docs.{format}/catalogs", description = "Catalog operations")
public class Catalogs extends Controller {

    @Inject
    ObjectMapper mapper;

    @ApiOperation(value = "List catalogs", httpMethod = "GET")
    @Transactional(readOnly = true)
    public Result index() throws IOException {
        @SuppressWarnings("unchecked")
        List<Catalog> catalogs = JPA.em().createQuery(
                "select c from Catalog c"
                )
                .getResultList();
        String json = mapper.writeValueAsString(catalogs);
        return ok(json).as("application/json");
    }

    @Transactional(readOnly = true)
    public Result findCatalogsByDatasourceId(Long datasourceId)  throws IOException {
        List<Catalog> catalogs = Catalog.findByDatasourceId(datasourceId);
        String json = mapper.writeValueAsString(catalogs);
        return ok(json).as("application/json");
    }
}
