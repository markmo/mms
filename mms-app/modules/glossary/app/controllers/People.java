package controllers.glossary;

import static play.data.Form.form;
import static utils.account.QueryTool.*;

import com.fasterxml.jackson.databind.JsonNode;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import models.domain.business.Person;
import models.common.Page;

/**
 * User: markmo
 * Date: 3/05/13
 * Time: 7:15 PM
 */
public class People extends Controller {

    @Transactional(readOnly = true)
    public static Result index(int pageIndex, int pageSize, String sortBy, String order) {
        if (sortBy == null || sortBy.isEmpty()) sortBy = "lastName";
        if (order == null || order.isEmpty()) order = "asc";
        Page<Person> page = getPage(Person.class, JPA.em(), pageIndex, pageSize, sortBy, order);
        return ok(page.toJSON()).as("application/json");
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        Form<Person> personForm = form(Person.class);
        Person person = personForm.bindFromRequest().get();
        if (personForm.hasErrors()) {
            return badRequest();
        } else {
            JPA.em().persist(person);
            return ok("{\"id\":\"" + person.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(Long id) {
        Form<Person> personForm = form(Person.class);
        JsonNode json = request().body().asJson();
        Person person = personForm.bind(json, "firstName", "lastName", "title", "email", "phone").get();
        if (personForm.hasErrors()) {
            return badRequest();
        } else {
            person.setId(id);
            JPA.em().merge(person);
            return ok("{\"id\":\"" + person.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    public static Result delete() {
        String[] ids = request().body().asFormUrlEncoded().get("id[]");
        for (String id : ids) {
            Person person = JPA.em().find(Person.class, Long.parseLong(id));
            if (person != null) {
                JPA.em().remove(person);
            }
        }
        return ok();
    }
}
