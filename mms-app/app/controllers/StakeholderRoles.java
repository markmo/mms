package controllers;

import static play.data.Form.form;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.*;

import mms.common.models.business.StakeholderRole;

/**
 * User: markmo
 * Date: 15/05/13
 * Time: 2:48 PM
 */
public class StakeholderRoles extends Controller {

    @Transactional(readOnly = true)
    public static Result index() throws IOException {
        @SuppressWarnings("unchecked")
        List<StakeholderRole> roles = JPA.em().createQuery(
                "select r from StakeholderRole r"
        )
                .getResultList();
        JsonNode json = Json.toJson(roles);
        return ok(json).as("application/json");
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        Form<StakeholderRole> roleForm = form(StakeholderRole.class);
        StakeholderRole role = roleForm.bindFromRequest().get();
        if (roleForm.hasErrors()) {
            return badRequest();
        } else {
            JPA.em().persist(role);
            return ok("{\"id\":\"" + role.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(Long id) {
        Form<StakeholderRole> roleForm = form(StakeholderRole.class);
        JsonNode json = request().body().asJson();
        StakeholderRole role = roleForm.bind(json, "name").get();
        if (roleForm.hasErrors()) {
            return badRequest();
        } else {
            role.setId(id);
            JPA.em().merge(role);
            return ok("{\"id\":\"" + role.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    public static Result delete() {
        String[] ids = request().body().asFormUrlEncoded().get("id[]");
        for (String id : ids) {
            StakeholderRole role = JPA.em().find(StakeholderRole.class, Long.parseLong(id));
            if (role != null) {
                JPA.em().remove(role);
            }
        }
        return ok();
    }
}
