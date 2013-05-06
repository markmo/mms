package controllers;

import static play.data.Form.form;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
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
        @SuppressWarnings("unchecked")
        List<BusinessTerm> businessTerms = JPA.em().createQuery(
                "select t from BusinessTerm t"
        )
                .getResultList();
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
                        JPA.em().merge(tag);
                    }
                    tags.add(tag);
                }
            }
            term.setTags(tags);

            // Parse definition
            String definition = term.getDefinition();
            String[] sentences = definition.split("\\.\\s+");
            for (String sentence : sentences) {
                Pattern pattern = Pattern.compile("#\\w+");
                Matcher matcher = pattern.matcher(sentence);
                if (matcher.find()) {
                    String obj = matcher.group().substring(1);
                    BusinessTerm object = BusinessTerm.findByName(obj);
                    if (object != null) {
                        BusinessTermAssociation assoc = new BusinessTermAssociation();
                        assoc.setSubject(term);
                        assoc.setObject(object);
                        assoc.setPredicate(sentence);

                        JPA.em().persist(assoc);
                    }
                }
            }

            JPA.em().persist(term);

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
            String definition = term.getDefinition();
            String[] sentences = definition.split("\\.\\s+");
            for (String sentence : sentences) {
                Pattern pattern = Pattern.compile("#\\w+");
                Matcher matcher = pattern.matcher(sentence);
                if (matcher.find()) {
                    String obj = matcher.group().substring(1);
                    BusinessTerm object = BusinessTerm.findByName(obj);
                    if (object != null) {
                        BusinessTermAssociation assoc = new BusinessTermAssociation();
                        assoc.setSubject(term);
                        assoc.setObject(object);
                        assoc.setPredicate(sentence);

                        JPA.em().persist(assoc);
                    }
                }
            }

            JPA.em().persist(term);

            return ok("{\"id\":\"" + term.getId() + "\"}").as("application/json");
        }
    }
}
