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

import mms.common.models.business.UserGroup;

/**
 * User: markmo
 * Date: 17/05/13
 * Time: 1:51 PM
 */
public class UserGroups extends Controller {

    @Transactional(readOnly = true)
    public static Result index() throws IOException {
        @SuppressWarnings("unchecked")
        List<UserGroup> groups = JPA.em().createQuery(
                "select g from UserGroup g"
        )
                .getResultList();
        JsonNode json = Json.toJson(groups);
        return ok(json).as("application/json");
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        Form<UserGroup> groupForm = form(UserGroup.class);
        UserGroup group = groupForm.bindFromRequest().get();
        if (groupForm.hasErrors()) {
            return badRequest();
        } else {
            JPA.em().persist(group);
            return ok("{\"id\":\"" + group.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(Long id) {
        Form<UserGroup> groupForm = form(UserGroup.class);
        JsonNode json = request().body().asJson();
        UserGroup group = groupForm.bind(json, "name").get();
        if (groupForm.hasErrors()) {
            return badRequest();
        } else {
            group.setId(id);
            JPA.em().merge(group);
            return ok("{\"id\":\"" + group.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    public static Result delete() {
        String[] ids = request().body().asFormUrlEncoded().get("id[]");
        for (String id : ids) {
            UserGroup group = JPA.em().find(UserGroup.class, Long.parseLong(id));
            if (group != null) {
                JPA.em().remove(group);
            }
        }
        return ok();
    }
}
