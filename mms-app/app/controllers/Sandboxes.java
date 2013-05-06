package controllers;

import static play.data.Form.form;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.codehaus.jackson.JsonNode;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.*;

import mms.common.models.Sandbox;

/**
 * User: markmo
 * Date: 20/02/13
 * Time: 9:05 PM
 */
public class Sandboxes extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    public Result index() {
        @SuppressWarnings("unchecked")
        List<Sandbox> sandboxes = JPA.em().createQuery(
                "select s from Sandbox s"
        )
                .getResultList();
        JsonNode json = Json.toJson(sandboxes);
        return ok(json).as("application/json");
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result create() {
        Form<Sandbox> sandboxForm = form(Sandbox.class);
        Sandbox sandbox = sandboxForm.bindFromRequest().get();
        if (sandboxForm.hasErrors()) {
            return badRequest();
        } else {
            JPA.em().persist(sandbox);
            return ok("{\"id\":\"" + sandbox.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result update(Long id) {
        Form<Sandbox> sandboxForm = form(Sandbox.class);
        JsonNode json = request().body().asJson();
        Sandbox sandbox = sandboxForm.bind(json, "name", "description").get();
        if (sandboxForm.hasErrors()) {
            return badRequest();
        } else {
            sandbox.setId(id);
            JPA.em().merge(sandbox);
            return ok("{\"id\":\"" + sandbox.getId() + "\"}").as("application/json");
        }
    }
}
