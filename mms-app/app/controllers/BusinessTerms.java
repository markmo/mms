package controllers;

import static play.data.Form.form;
import static utils.QueryTool.*;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import indexing.BusinessTermIndex;
import org.codehaus.jackson.JsonNode;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import mms.common.models.SecurityClassification;
import mms.common.models.business.*;

/**
 * User: markmo
 * Date: 26/04/13
 * Time: 8:28 AM
 */
public class BusinessTerms extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    public Result index() throws IOException {
//        @SuppressWarnings("unchecked")
//        List<BusinessTerm> businessTerms = JPA.em().createQuery(
//                "select t from BusinessTerm t"
//        )
//                .getResultList();
        List<BusinessTerm> businessTerms = getResultList(BusinessTerm.class, JPA.em(), session());
        String json = mapper.writeValueAsString(businessTerms);
        return ok(json).as("application/json");
    }

    @Transactional(readOnly = true)
    public Result associations(Long subjectId) throws IOException {
        @SuppressWarnings("unchecked")
        List<BusinessTermAssociation> associations =
                BusinessTermAssociation.findBySubjectId(subjectId);
        String json = mapper.writeValueAsString(associations);
        return ok(json).as("application/json");
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        Form<BusinessTerm> termForm = form(BusinessTerm.class);
        JsonNode json = request().body().asJson();
        BusinessTerm term = termForm.bind(json, "name",
                "definition", "description", "domain", "parent",
                "securityClassification", "tags", "representations",
                "customMetadata").get();
        if (termForm.hasErrors()) {
            return badRequest();
        } else {
            // Lookup Domain
            JsonNode domainNode = json.get("domain");
            Domain domain = null;
            if (!domainNode.isNull()) {
                domain = JPA.em().find(Domain.class, domainNode.get("id").getLongValue());
            }
            term.setDomain(domain);

            // Lookup Parent Term
            JsonNode parentNode = json.get("parent");
            BusinessTerm parent = null;
            if (!parentNode.isNull()) {
                parent = JPA.em().find(BusinessTerm.class, parentNode.get("id").getLongValue());
            }
            term.setParent(parent);

            // Lookup Security Classification
            JsonNode scNode = json.get("securityClassification");
            SecurityClassification sc = null;
            if (!scNode.isNull()) {
                sc = JPA.em().find(SecurityClassification.class, scNode.get("id").getLongValue());
            }
            term.setSecurityClassification(sc);

            // Convert CustomMetadata property to String
            JsonNode cmNode = json.get("customMetadata");
            String customMetadata = null;
            if (!cmNode.isNull()) {
                customMetadata = cmNode.toString();
            }
            term.setCustomMetadata(customMetadata);

            JPA.em().persist(term);

            // Persist tags. Must follow persistence of term due to many-to-many.
            Iterator<JsonNode> it = json.get("tags").getElements();
            Set<Tag> tags = new HashSet<Tag>();
            while (it.hasNext()) {
                Form<Tag> tagForm = form(Tag.class);
                JsonNode tagNode = it.next();
                Tag tag = tagForm.bind(tagNode, "id", "name").get();
                if (tagForm.hasErrors()) {
                    return badRequest();
                } else {
                    if (tag.getId() == null) {
                        JPA.em().persist(tag);
                    } else {
//                        JPA.em().merge(tag);
                    }
                    tags.add(tag);
                }
            }
            term.setTags(tags);

            // Parse definition
            List<BusinessTermAssociation> assocs = extractBusinessTermAssociations(term);
            for (BusinessTermAssociation assoc : assocs) {
                assoc.setSource(SourceType.DEFINITION);
                JPA.em().persist(assoc);
            }
            JPA.em().persist(term);

            BusinessTermIndex termIndex = new BusinessTermIndex();
            termIndex.setBusinessTerm(term);
            termIndex.index();

            return ok("{\"id\":\"" + term.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(Long id) {
        Form<BusinessTerm> termForm = form(BusinessTerm.class);
        JsonNode json = request().body().asJson();
        BusinessTerm term = termForm.bind(json, "name",
                "definition", "description", "domain", "parent",
                "securityClassification", "tags", "representations",
                "customMetadata").get();
        if (termForm.hasErrors()) {
            return badRequest();
        } else {
            term.setId(id);
            // Lookup Domain
            JsonNode domainNode = json.get("domain");
            Domain domain = null;
            if (!domainNode.isNull()) {
                domain = JPA.em().find(Domain.class, domainNode.get("id").getLongValue());
            }
            term.setDomain(domain);

            // Lookup Parent Term
            JsonNode parentNode = json.get("parent");
            BusinessTerm parent = null;
            if (!parentNode.isNull()) {
                parent = JPA.em().find(BusinessTerm.class, parentNode.get("id").getLongValue());
            }
            term.setParent(parent);

            // Lookup Security Classification
            JsonNode scNode = json.get("securityClassification");
            SecurityClassification sc = null;
            if (!scNode.isNull()) {
                sc = JPA.em().find(SecurityClassification.class, scNode.get("id").getLongValue());
            }
            term.setSecurityClassification(sc);

            // Convert CustomMetadata property to String
            JsonNode cmNode = json.get("customMetadata");
            String customMetadata = null;
            if (!cmNode.isNull()) {
                customMetadata = cmNode.toString();
            }
            term.setCustomMetadata(customMetadata);

            term = JPA.em().merge(term);

            // Persist tags. Must follow persistence of term due to many-to-many.
            Iterator<JsonNode> it = json.get("tags").getElements();
            Set<Tag> tags = new HashSet<Tag>();
            while (it.hasNext()) {
                Form<Tag> tagForm = form(Tag.class);
                JsonNode tagNode = it.next();
                Tag tag = tagForm.bind(tagNode, "id", "name").get();
                if (tagForm.hasErrors()) {
                    return badRequest();
                } else {
                    if (tag.getId() == null) {
                        JPA.em().persist(tag);
                    } else {
                        JPA.em().merge(tag);
                    }
                    tags.add(tag);
                }
            }
            term.setTags(tags);

            // Parse definition
            List<BusinessTermAssociation> assocs = extractBusinessTermAssociations(term);
            List<BusinessTermAssociation> existing =
                    BusinessTermAssociation.findBySubjectIdFromSource(id, SourceType.DEFINITION);
            List<Long> changelist = new ArrayList<>();

            // Add new or changed associations
            for (BusinessTermAssociation assoc : assocs) {
                List<BusinessTermAssociation> matching =
                        BusinessTermAssociation.findBySubjectIdAndObjectIdFromSource(
                                assoc.getSubject().getId(),
                                assoc.getObject().getId(),
                                SourceType.DEFINITION);
                if (matching.isEmpty()) {
                    assoc.setSource(SourceType.DEFINITION);
                    JPA.em().persist(assoc);
                } else {
                    BusinessTermAssociation a = matching.get(0);
                    changelist.add(a.getId());
                    if (!a.getPredicate().equals(assoc.getPredicate())) {
                        a.setPredicate(assoc.getPredicate());
                        JPA.em().persist(a);
                    }
                }
            }
            // Remove deleted associations
            for (BusinessTermAssociation assoc : existing) {
                if (!changelist.contains(assoc.getId())) {
                    JPA.em().remove(assoc);
                }
            }
            JPA.em().persist(term);

            return ok("{\"id\":\"" + term.getId() + "\"}").as("application/json");
        }
    }

    private static List<BusinessTermAssociation> findRemaining(
            List<BusinessTermAssociation> existing,
            List<BusinessTermAssociation> replacing)
    {
        if (existing == null) return null;
        if (existing.isEmpty()) return existing;
        if (replacing == null || replacing.isEmpty()) return existing;
        List<BusinessTermAssociation> remaining = new ArrayList<>(existing);
        for (BusinessTermAssociation assoc : replacing) {
            int n = remaining.size();
            if (n == 0) break;
            for (int i = 0; i < n; i++) {
                BusinessTermAssociation a = remaining.get(i);
                if (a.getSubject().equals(assoc.getSubject()) &&
                    a.getObject().equals(assoc.getObject()))
                {
                    remaining.remove(i);
                    break;
                }
            }
        }
        return remaining;
    }

    private static List<BusinessTermAssociation> extractBusinessTermAssociations(BusinessTerm term) {
        List<BusinessTermAssociation> assocs = new ArrayList<>();
        String definition = term.getDefinition();
        if (definition.endsWith(".")) {
            definition = definition.substring(0, definition.length() - 1);
        }
        String[] sentences = definition.split("\\.\\s+");
        for (String sentence : sentences) {
            Pattern p1 = Pattern.compile("#\\w+|#[\"}][^\"}]+[\"}]", Pattern.DOTALL);
            Pattern p2 = Pattern.compile("^#[\"}]?([^\"}]+)[\"}]?$");
            Matcher m1 = p1.matcher(sentence);
            while (m1.find()) {
                String tag = m1.group();
                Matcher m2 = p2.matcher(tag);
                if (m2.find()) {
                    String objname = m2.group(1);
                    BusinessTerm object = BusinessTerm.findByName(objname);
                    if (object != null) {
                        BusinessTermAssociation assoc = new BusinessTermAssociation();
                        assoc.setSubject(term);
                        assoc.setObject(object);
                        assoc.setPredicate(sentence);
                        assocs.add(assoc);
                    }
                }
            }
        }
        return assocs;
    }

    private static Set<BusinessTermAssociation> convertToSet(List<BusinessTermAssociation> list) {
        if (list == null) return null;
        if (list.isEmpty()) return new HashSet<>();
        Map<Pair<Long>, BusinessTermAssociation> assocMap = new HashMap<>();
        for (BusinessTermAssociation assoc : list) {
            Pair<Long> key = new Pair<>(assoc.getSubject().getId(), assoc.getObject().getId());
            if (assocMap.containsKey(key)) {
                BusinessTermAssociation existing = assocMap.get(key);
                String newPredicate = existing.getPredicate() + " " + assoc.getPredicate();
                existing.setPredicate(newPredicate);
            } else {
                assocMap.put(key, assoc);
            }
        }
        return new HashSet<>(assocMap.values());
    }

    static class Pair<T> {

        T lft;

        T rgt;

        Pair(T lft, T rgt) {
            this.lft = lft;
            this.rgt = rgt;
        }

        @Override
        public String toString() {
            return String.format("Pair<%s,%s>", lft, rgt);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (!lft.equals(pair.lft)) return false;
            if (!rgt.equals(pair.rgt)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = lft.hashCode();
            result = 31 * result + rgt.hashCode();
            return result;
        }
    }
}
