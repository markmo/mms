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

import mms.common.models.business.Person;

/**
 * User: markmo
 * Date: 3/05/13
 * Time: 7:15 PM
 */
public class People extends Controller {

    @Transactional(readOnly = true)
    public static Result index() throws IOException {
        @SuppressWarnings("unchecked")
        List<Person> people = JPA.em().createQuery(
                "select p from Person p"
        )
                .getResultList();
        JsonNode json = Json.toJson(people);
        return ok(json).as("application/json");
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
}
