package controllers;

import static play.data.Form.form;
import static utils.QueryTool.*;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import org.codehaus.jackson.JsonNode;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import mms.common.models.business.UserGroup;
import models.Page;

/**
 * User: markmo
 * Date: 17/05/13
 * Time: 1:51 PM
 */
public class UserGroups extends Controller {

    @Transactional(readOnly = true)
    public static Result index(int pageIndex, int pageSize, String sortBy, String order) {
        if (sortBy == null || sortBy.isEmpty()) sortBy = "name";
        if (order == null || order.isEmpty()) order = "asc";
        Page<UserGroup> page = getPage(UserGroup.class, JPA.em(), pageIndex, pageSize, sortBy, order);
        long totalRowCount = page.getTotalRowCount();
        List<String> links = new ArrayList<>();
        links.add(String.format("<%s&total_entries=%d>; rel=\"first\"",
                routes.UserGroups.index(1, pageSize, sortBy, order), totalRowCount));
        links.add(String.format("<%s&total_entries=%d>; rel=\"last\"",
                routes.UserGroups.index(page.getLastPageIndex(), pageSize, sortBy, order), totalRowCount));
        if (page.hasPrev()) {
            links.add(String.format("<%s&total_entries=%d>; rel=\"prev\"",
                    routes.UserGroups.index(pageIndex - 1, pageSize, sortBy, order), totalRowCount));
        }
        if (page.getTotalRowCount() >= (pageIndex * pageSize)) {
            links.add(String.format("<%s&total_entries=%d>; rel=\"next\"",
                    routes.UserGroups.index(pageIndex + 1, pageSize, sortBy, order), totalRowCount));
        }
        String linksHeader = Joiner.on(',').join(links);
        response().setHeader("Link", linksHeader);
        return ok(page.toJSON()).as("application/json");
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
