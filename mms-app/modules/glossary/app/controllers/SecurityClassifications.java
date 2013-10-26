package controllers.glossary;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.*;

import models.domain.SecurityClassification;

/**
 * User: markmo
 * Date: 29/04/13
 * Time: 10:25 AM
 */
public class SecurityClassifications extends Controller {

    @Transactional(readOnly = true)
    @BodyParser.Of(BodyParser.Json.class)
    public static Result index() {
        @SuppressWarnings("unchecked")
        List<SecurityClassification> securityClassifications = JPA.em().createQuery(
                "select c from SecurityClassification c"
        )
                .getResultList();
        JsonNode json = Json.toJson(securityClassifications);
        return ok(json);
    }
}
