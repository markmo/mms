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
    static ObjectMapper mapper;

    @Transactional(readOnly = true)
    @BodyParser.Of(BodyParser.Json.class)
    public static Result index() {
//        List <Table> tables = Table.find.all();
        List<Table> tables = JPA.em().createQuery("from Table").getResultList();
        JsonNode json = Json.toJson(tables);
        return ok(json);
    }

    @Transactional(readOnly = true)
//    @BodyParser.Of(BodyParser.Json.class)
    public static Result findTablesBySchemaId(Long schemaId) throws IOException {
        List<Table> tables = Table.findBySchemaId(schemaId);
//        JsonNode json = Json.toJson(tables);
        String json = mapper.writeValueAsString(tables);
        return ok(json).as("application/json");
    }
}
