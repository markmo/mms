package controllers.registry;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.codehaus.jackson.JsonNode;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.*;

import mms.common.models.Namespace;

/**
 * User: markmo
 * Date: 5/11/12
 * Time: 12:39 PM
 */
public class Namespaces extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    @BodyParser.Of(BodyParser.Json.class)
    public Result index() {
        @SuppressWarnings("unchecked")
        List<Namespace> namespaces = JPA.em().createQuery(
                "select n from Namespace n"
                )
                .getResultList();
        JsonNode json = Json.toJson(namespaces);
        return ok(json);
    }

    @Transactional(readOnly = true)
    public Result findNamespacesByCatalogId(Long catalogId)  throws IOException {
        List<Namespace> namespaces = Namespace.findByCatalogId(catalogId);
        String json = mapper.writeValueAsString(namespaces);
        return ok(json).as("application/json");
    }

    @Transactional(readOnly = true)
    public Result findNamespacesBySandboxId(Long sandboxId)  throws IOException {
        List<Namespace> namespaces = Namespace.findBySandboxId(sandboxId);
        String json = mapper.writeValueAsString(namespaces);
        return ok(json).as("application/json");
    }
}
