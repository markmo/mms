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

import models.Column;

/**
 * User: markmo
 * Date: 6/11/12
 * Time: 9:10 PM
 */
public class Columns extends Controller {

    @Inject
    static ObjectMapper mapper;

    @Transactional(readOnly = true)
    @BodyParser.Of(BodyParser.Json.class)
    public static Result index() {
//        List<Column> columns = Column.find.all();
        List<Column> columns = JPA.em().createQuery("from Column").getResultList();
        JsonNode json = Json.toJson(columns);
        return ok(json);
    }

    @Transactional(readOnly = true)
//    @BodyParser.Of(BodyParser.Json.class)
    public static Result findColumnsByTableId(Long tableId) throws IOException {
        List<Column> columns = Column.findByTableId(tableId);
//        JsonNode json = Json.toJson(columns);
        String json = mapper.writeValueAsString(columns);
        return ok(json).as("application/json");
    }
}
