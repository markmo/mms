package controllers;

import static play.data.Form.form;
import static utils.QueryTool.*;

import org.codehaus.jackson.JsonNode;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import mms.common.models.business.StakeholderRole;
import models.Page;

/**
 * User: markmo
 * Date: 15/05/13
 * Time: 2:48 PM
 */
public class StakeholderRoles extends Controller {

    @Transactional(readOnly = true)
    public static Result index(int pageIndex, int pageSize, String sortBy, String order) {
        if (sortBy == null || sortBy.isEmpty()) sortBy = "name";
        if (order == null || order.isEmpty()) order = "asc";
        Page<StakeholderRole> page = getPage(StakeholderRole.class, JPA.em(), pageIndex, pageSize, sortBy, order);
        return ok(page.toJSON()).as("application/json");
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
