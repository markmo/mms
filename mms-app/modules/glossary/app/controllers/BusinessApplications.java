package controllers.glossary;

import static play.data.Form.form;
import static utils.QueryTool.*;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.codehaus.jackson.JsonNode;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import mms.common.models.business.Application;
import models.common.Page;

/**
 * User: markmo
 * Date: 16/05/13
 * Time: 2:44 PM
 */
public class BusinessApplications extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    public Result index(int pageIndex, int pageSize, String sortBy, String order) throws IOException {
        if (sortBy == null || sortBy.isEmpty()) sortBy = "name";
        if (order == null || order.isEmpty()) order = "asc";
        Page<Application> page = getPage(Application.class, JPA.em(), pageIndex, pageSize, sortBy, order);
        return ok(page.toJSON(mapper)).as("application/json");
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        Form<Application> applicationForm = form(Application.class);
        Application application = applicationForm.bindFromRequest().get();
        if (applicationForm.hasErrors()) {
            return badRequest();
        } else {
            JPA.em().persist(application);
            return ok("{\"id\":\"" + application.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(Long id) {
        Form<Application> applicationForm = form(Application.class);
        JsonNode json = request().body().asJson();
        Application application = applicationForm.bind(json, "name").get();
        if (applicationForm.hasErrors()) {
            return badRequest();
        } else {
            application.setId(id);
            JPA.em().merge(application);
            return ok("{\"id\":\"" + application.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    public static Result delete(Long id) {
        Application application = JPA.em().find(Application.class, id);
        if (application != null) {
            JPA.em().remove(application);
        }
        return ok();
    }
}
