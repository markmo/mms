package controllers;

import java.util.List;

import org.codehaus.jackson.JsonNode;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.*;

import models.DataSource;

/**
 * User: markmo
 * Date: 4/11/12
 * Time: 1:39 PM
 */
public class DataSources extends Controller {

    @Transactional(readOnly = true)
    @BodyParser.Of(BodyParser.Json.class)
    public static Result index() {
//        List<DataSource> dataSources = DataSource.find.all();
        List<DataSource> dataSources = JPA.em().createQuery("from DataSource").getResultList();
        JsonNode json = Json.toJson(dataSources);
        return ok(json);
    }
}
