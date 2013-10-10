package controllers.glossary;

import java.util.*;

import org.codehaus.jackson.JsonNode;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import mms.common.models.business.*;

/**
 * User: markmo
 * Date: 16/05/13
 * Time: 9:04 AM
 */
public class Responsibilities extends Controller {

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update() {
        JsonNode json = request().body().asJson();
        Map<BusinessTerm, Set<BusinessTermStakeholderPerson>> responsibilityMap = getResponsibilityMap(json);
        for (BusinessTerm term : responsibilityMap.keySet()) {
            Set<BusinessTermStakeholderPerson> people = responsibilityMap.get(term);
            term.getPeople().clear();
            JPA.em().createQuery("delete from BusinessTermStakeholderPerson where pk.businessTerm = ?1")
                    .setParameter(1, term)
                    .executeUpdate();
            term.setPeople(people);
            JPA.em().persist(term);
        }
        return ok();
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result patchUpdate() {
        JsonNode json = request().body().asJson();
        Map<BusinessTerm, Set<BusinessTermStakeholderPerson>> responsibilityMap = getResponsibilityMap(json);
        for (BusinessTerm term : responsibilityMap.keySet()) {
            Set<BusinessTermStakeholderPerson> people = responsibilityMap.get(term);
            term.getPeople().addAll(people);
            JPA.em().persist(term);
        }
        return ok();
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result delete() {
        JsonNode json = request().body().asJson();
        Map<BusinessTerm, Set<BusinessTermStakeholderPerson>> responsibilityMap = getResponsibilityMap(json);
        for (BusinessTerm term : responsibilityMap.keySet()) {
            Set<BusinessTermStakeholderPerson> responsibilities = responsibilityMap.get(term);
            for (BusinessTermStakeholderPerson responsibility : responsibilities) {
                responsibility = JPA.em().merge(responsibility);
                term.getPeople().remove(responsibility);
                JPA.em().remove(responsibility);
            }
        }
        return ok();
    }

    private static Map<BusinessTerm, Set<BusinessTermStakeholderPerson>> getResponsibilityMap(JsonNode json) {
        Iterator<JsonNode> it = json.getElements();
        Map<BusinessTerm, Set<BusinessTermStakeholderPerson>> responsibilityMap = new HashMap<>();
        while (it.hasNext()) {
            JsonNode node = it.next();
            Long termId = node.get("termId").asLong();
            Long personId = node.get("personId").asLong();
            Long roleId = node.get("roleId").asLong();
            BusinessTerm term = JPA.em().find(BusinessTerm.class, termId);
            Person person = JPA.em().find(Person.class, personId);
            StakeholderRole role = JPA.em().find(StakeholderRole.class, roleId);
            BusinessTermStakeholderPersonPK pk = new BusinessTermStakeholderPersonPK();
            pk.setBusinessTerm(term);
            pk.setPerson(person);
            pk.setStakeholderRole(role);
            if (!responsibilityMap.containsKey(term)) {
                responsibilityMap.put(term, new HashSet<BusinessTermStakeholderPerson>());
            }
            BusinessTermStakeholderPerson responsibility = new BusinessTermStakeholderPerson();
            responsibility.setPk(pk);
            responsibilityMap.get(term).add(responsibility);
        }
        return responsibilityMap;
    }
}
