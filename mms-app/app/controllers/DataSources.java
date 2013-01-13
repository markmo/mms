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

import models.DataSource;

/**
 * User: markmo
 * Date: 4/11/12
 * Time: 1:39 PM
 */
public class DataSources extends Controller {

    @Inject
    static ObjectMapper mapper;

    @Transactional(readOnly = true)
//    @BodyParser.Of(BodyParser.Json.class)
    public static Result index() throws IOException {
//        List<DataSource> dataSources = DataSource.find.all();
        List<DataSource> dataSources = JPA.em().createQuery(
                "select d from DataSource d",
                DataSource.class)
                .getResultList();
//        JsonNode json = Json.toJson(dataSources);
        String json = mapper.writeValueAsString(dataSources);
        return ok(json).as("application/json");
    }
}
