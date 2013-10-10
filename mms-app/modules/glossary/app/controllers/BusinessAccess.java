package controllers.glossary;

import java.util.*;

import org.codehaus.jackson.JsonNode;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import mms.common.models.business.*;

/**
 * User: markmo
 * Date: 22/05/13
 * Time: 2:38 PM
 */
public class BusinessAccess extends Controller {

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update() {
        JsonNode json = request().body().asJson();
        Map<BusinessTerm, Set<AccessPrivileges>> accessMap = getAccessMap(json);
        for (BusinessTerm term : accessMap.keySet()) {
            Set<AccessPrivileges> privileges = accessMap.get(term);
            term.getAccessPrivileges().clear();
            JPA.em().createQuery("delete from AccessPrivileges where pk.businessTerm = ?1")
                    .setParameter(1, term)
                    .executeUpdate();
            term.setAccessPrivileges(privileges);
            JPA.em().persist(term);
        }
        return ok();
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result patchUpdate() {
        JsonNode json = request().body().asJson();
        Map<BusinessTerm, Set<AccessPrivileges>> accessMap = getAccessMap(json);
        for (BusinessTerm term : accessMap.keySet()) {
            Set<AccessPrivileges> privileges = accessMap.get(term);
            term.getAccessPrivileges().addAll(privileges);
            JPA.em().persist(term);
        }
        return ok();
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result delete() {
        JsonNode json = request().body().asJson();
        Map<BusinessTerm, Set<AccessPrivileges>> accessMap = getAccessMap(json);
        for (BusinessTerm term : accessMap.keySet()) {
            Set<AccessPrivileges> privileges = accessMap.get(term);
            for (AccessPrivileges ap : privileges) {
                ap = JPA.em().merge(ap);
                term.getAccessPrivileges().remove(ap);
                JPA.em().remove(ap);
            }
        }
        return ok();
    }

    private static Map<BusinessTerm, Set<AccessPrivileges>> getAccessMap(JsonNode json) {
        Iterator<JsonNode> it = json.getElements();
        Map<BusinessTerm, Set<AccessPrivileges>> accessMap = new HashMap<>();
        while (it.hasNext()) {
            JsonNode node = it.next();
            Long termId = node.get("termId").asLong();
            Long groupId = node.get("groupId").asLong();
            String access = null;
            if (node.has("access"))
                access = node.get("access").asText();
            BusinessTerm term = JPA.em().find(BusinessTerm.class, termId);
            UserGroup group = JPA.em().find(UserGroup.class, groupId);
            AccessPrivilegesPK pk = new AccessPrivilegesPK();
            pk.setBusinessTerm(term);
            pk.setUserGroup(group);
            if (!accessMap.containsKey(term)) {
                accessMap.put(term, new HashSet<AccessPrivileges>());
            }
            AccessPrivileges privileges = new AccessPrivileges();
            privileges.setPk(pk);
            privileges.setAccess(access);
            accessMap.get(term).add(privileges);
        }
        return accessMap;
    }
}
