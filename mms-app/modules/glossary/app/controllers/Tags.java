package controllers.glossary;

import java.util.List;

import org.codehaus.jackson.JsonNode;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.*;

import mms.common.models.business.Tag;

/**
 * User: markmo
 * Date: 26/04/13
 * Time: 1:33 PM
 */
public class Tags extends Controller {

    @Transactional(readOnly = true)
    @BodyParser.Of(BodyParser.Json.class)
    public static Result index() {
        @SuppressWarnings("unchecked")
        List<Tag> tags = JPA.em().createQuery(
                "select t from Tag t"
        )
                .getResultList();
        JsonNode json = Json.toJson(tags);
        return ok(json);
    }
}
