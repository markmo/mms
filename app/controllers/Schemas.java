package controllers;

import java.util.List;

import org.codehaus.jackson.JsonNode;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.*;

import models.Schema;

/**
 * User: markmo
 * Date: 5/11/12
 * Time: 12:39 PM
 */
public class Schemas extends Controller {

    @Transactional(readOnly = true)
    @BodyParser.Of(BodyParser.Json.class)
    public static Result index() {
//        List<Schema> schemas = Schema.find.all();
        List<Schema> schemas = JPA.em().createQuery("from Schema").getResultList();
        JsonNode json = Json.toJson(schemas);
        return ok(json);
    }

    @Transactional(readOnly = true)
    @BodyParser.Of(BodyParser.Json.class)
    public static Result findSchemasByDataSourceId(Long dataSourceId) {
        //List<Schema> schemas = Schema.find.where().eq("dataSource.id", dataSourceId).findList();
        List<Schema> schemas = Schema.findByDataSourceId(dataSourceId);
        JsonNode json = Json.toJson(schemas);
        return ok(json);
    }
}
