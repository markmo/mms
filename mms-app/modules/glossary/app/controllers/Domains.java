package controllers.glossary;

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

import mms.common.models.business.BusinessTerm;
import mms.common.models.business.Domain;

/**
 * User: markmo
 * Date: 29/04/13
 * Time: 4:04 PM
 */
public class Domains extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    public Result index() throws IOException {
        @SuppressWarnings("unchecked")
        List<Domain> domains = JPA.em().createQuery(
                "select d from Domain d"
        )
                .getResultList();
        String json = mapper.writeValueAsString(domains);
        return ok(json).as("application/json");
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        Form<Domain> domainForm = form(Domain.class);
        JsonNode json = request().body().asJson();
        Domain domain = domainForm.bind(json, "name").get();
        if (domainForm.hasErrors()) {
            return badRequest();
        } else {
            // Lookup Parent Domain
            JsonNode parentNode = json.get("parent");
            Domain parent = null;
            if (!parentNode.isNull()) {
                parent = JPA.em().find(Domain.class, parentNode.get("id").getLongValue());
            }
            domain.setParent(parent);
            JPA.em().persist(domain);
            return ok("{\"id\":\"" + domain.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(Long id) {
        Form<Domain> domainForm = form(Domain.class);
        JsonNode json = request().body().asJson();
        Domain domain = domainForm.bind(json, "name").get();
        if (domainForm.hasErrors()) {
            return badRequest();
        } else {
            domain.setId(id);
            // Lookup Parent Domain
            JsonNode parentNode = json.get("parent");
            Domain parent = null;
            if (!parentNode.isNull()) {
                parent = JPA.em().find(Domain.class, parentNode.get("id").getLongValue());
            }
            domain.setParent(parent);

            JPA.em().merge(domain);
            return ok("{\"id\":\"" + domain.getId() + "\"}").as("application/json");
        }
    }
}
