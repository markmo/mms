package controllers.common;

import static play.data.Form.form;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.codehaus.jackson.JsonNode;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import mms.common.models.Datasource;

/**
 * User: markmo
 * Date: 4/11/12
 * Time: 1:39 PM
 */
public class Datasources extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    public Result index() throws IOException {
        @SuppressWarnings("unchecked")
        List<Datasource> datasources = JPA.em().createQuery(
                "select d from Datasource d"
                )
                .getResultList();
        String json = mapper.writeValueAsString(datasources);
        return ok(json).as("application/json");
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        Form<Datasource> datasourceForm = form(Datasource.class);
        Datasource datasource = datasourceForm.bindFromRequest().get();
        if (datasourceForm.hasErrors()) {
            return badRequest();
        } else {
            JPA.em().persist(datasource);
            return ok("{\"id\":\"" + datasource.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(Long id) {
        Form<Datasource> datasourceForm = form(Datasource.class);
        JsonNode json = request().body().asJson();
        Datasource datasource = datasourceForm.bind(json, "name").get();
        if (datasourceForm.hasErrors()) {
            return badRequest();
        } else {
            datasource.setId(id);
            JPA.em().merge(datasource);
            return ok("{\"id\":\"" + datasource.getId() + "\"}").as("application/json");
        }
    }
}
