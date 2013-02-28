package controllers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.codehaus.jackson.JsonNode;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.*;

import models.Table;

/**
 * User: markmo
 * Date: 3/11/12
 * Time: 10:24 AM
 */
public class Tables extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    public Result index() {
        @SuppressWarnings("unchecked")
        List<Table> tables = JPA.em().createQuery(
                "select t from Table t"
                )
                .getResultList();
        JsonNode json = Json.toJson(tables);
        return ok(json);
    }

    @Transactional(readOnly = true)
    public Result findTablesBySchemaId(Long schemaId) throws IOException {
        List<Table> tables = Table.findBySchemaId(schemaId);
        String json = mapper.writeValueAsString(tables);
        return ok(json).as("application/json");
    }
}
