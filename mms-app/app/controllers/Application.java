package controllers;

import static play.data.Form.form;

import java.io.IOException;
import java.text.*;
import java.util.*;
import javax.persistence.Query;

import be.objectify.deadbolt.java.actions.*;
import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.user.AuthUser;
import com.google.inject.Inject;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthProvider.MyLogin;
import providers.MyUsernamePasswordAuthProvider.MySignup;

import mms.common.models.Catalog;
import mms.common.models.Datasource;
import mms.common.models.Namespace;
import mms.common.models.Sandbox;
import mms.common.models.audit.CustomTrackingRevisionEntity;
import mms.common.models.audit.ModifiedEntityTypeEntity;
import mms.common.models.relational.Table;
import models.User;
import views.html.*;

public class Application extends Controller {

    public static final String FLASH_MESSAGE_KEY = "message";
    public static final String FLASH_ERROR_KEY = "error";
    public static final String USER_ROLE = "user";

    static Form<Table> tableForm = form(Table.class);

    @Inject
    ObjectMapper mapper;

    @Transactional
    @Restrict(@Group(Application.USER_ROLE))
    public Result index() {
        if (session("theme") == null) {
            session("theme", "spacelab");
        }
        return ok(
                tables.render("datasources")
        );
    }

    public Result theme(String theme) {
        session("theme", theme);
        return redirect(routes.Application.index());
    }

    public static Result api() {
        return ok(apidocs.render());
    }

    public Result tables() {
        @SuppressWarnings("unchecked")
        List<Table> tables = JPA.em().createQuery(
                "select t from Table t"
        )
                .getResultList();
        return ok(
                index.render(tables, tableForm)
        );
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result importDatasource() throws Exception {
        Http.RequestBody body = request().body();
        JsonNode json = request().body().asJson();
        Sandbox sandbox = Sandbox.parseSandbox(json.path("sandbox"));
        Datasource datasource = Datasource.parseDatasource(mapper, json.path("data"));
        for (Catalog catalog : datasource.getCatalogs()) {
            for (Namespace namespace : catalog.getNamespaces()) {
                namespace.setSandbox(sandbox);
                JPA.em().persist(namespace);
            }
        }

//        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        Datasource datasource = mapper.readValue(json, Datasource.class);
//        datasource.save();
//        JPA.em().persist(datasource);

        return ok();
    }

    @Transactional(readOnly = true)
    public Result revisions() throws IOException {
        @SuppressWarnings("unchecked")
        List<CustomTrackingRevisionEntity> revisions = JPA.em().createQuery(
                "select r from CustomTrackingRevisionEntity r"
                )
                .getResultList();

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (CustomTrackingRevisionEntity revision : revisions) {
            Map<String, Object> entry = new HashMap<String, Object>();
            entry.put("revisionId", revision.getId());
            entry.put("revisionDate", revision.getRevisionDate());
            entry.put("numberEntitiesChanged", revision.getNumberEntitiesChanged());
            result.add(entry);
        }
        // TODO: move this to a global setting
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        mapper.setDateFormat(df);
        String json = mapper.writeValueAsString(result);
        return ok(json).as("application/json");
    }

    public Result showRevisions() {
        return ok(views.html.tables.render("revisions"));
    }

    public Result glossary() {
        return ok(views.html.tables.render("glossary"));
    }

    @Transactional(readOnly = true)
    public Result getRevision(Integer revisionId) throws IOException {
        CustomTrackingRevisionEntity revision = null;
        try {
            @SuppressWarnings("unchecked")
            List<CustomTrackingRevisionEntity> revisions = JPA.em().createQuery(
                    "select r from CustomTrackingRevisionEntity r where r.id = ?1"
                    )
                    .setParameter(1, revisionId)
                    .setMaxResults(1)
                    .getResultList();

            if (revisions.size() > 0) {
                revision = revisions.get(0);
            }

        } catch (Exception e) {
            // ignore
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("revisionId", revision.getId());
        result.put("revisionDate", revision.getRevisionDate());
        List<Map<String, Object>> modifiedEntities = new ArrayList<Map<String, Object>>();
        for (ModifiedEntityTypeEntity mod : revision.getModifiedEntityTypes()) {
            Map<String, Object> entry = new HashMap<String, Object>();
            entry.put("revisionType", mod.revisionType);
            entry.put("entityType", mod.entityType);
            entry.put("entityId", mod.entityId);
            modifiedEntities.add(entry);
        }
        result.put("modifiedEntities", modifiedEntities);

        // TODO: move this to a global setting
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        mapper.setDateFormat(df);
        String json = mapper.writeValueAsString(result);
        return ok(json).as("application/json");
    }

    public static User getLocalUser(final Http.Session session) {
        final AuthUser currentAuthUser = PlayAuthenticate.getUser(session);
        final User localUser = User.findByAuthUserIdentity(currentAuthUser);
        return localUser;
    }

    @Transactional(readOnly = true)
    @Restrict(@Group(Application.USER_ROLE))
    public Result clientSession() throws IOException {
        final User localUser = Application.getLocalUser(session());
        String json = mapper.writeValueAsString(localUser.getDTO());
        return ok(json);
    }

    @Restrict(@Group(Application.USER_ROLE))
    public Result restricted() {
        final User localUser = Application.getLocalUser(session());
        return ok(restricted.render(localUser));
    }

    @Transactional(readOnly = true)
    @Restrict(@Group(Application.USER_ROLE))
    public Result profile() {
        final User localUser = Application.getLocalUser(session());
        return ok(profile.render(localUser));
    }

    @Transactional(readOnly = true)
    @Restrict(@Group(Application.USER_ROLE))
    public Result profileAsJSON() throws IOException {
        final User localUser = Application.getLocalUser(session());
        String json = mapper.writeValueAsString(localUser.getDTO());
        return ok(json).as("application/json");
    }

    public Result login() {
        return ok(login.render(MyUsernamePasswordAuthProvider.LOGIN_FORM));
    }

    @Transactional
    public Result doLogin() {
        final Form<MyLogin> filledForm =
                MyUsernamePasswordAuthProvider.LOGIN_FORM.bindFromRequest();
        if (filledForm.hasErrors()) {
            // User did not fill everything properly
            return badRequest(login.render(filledForm));
        } else {
            // Everything was filled
            return UsernamePasswordAuthProvider.handleLogin(ctx());
        }
    }

    public Result signup() {
        return ok(signup.render(MyUsernamePasswordAuthProvider.SIGNUP_FORM));
    }

//    public static Result jsRoutes() {
//        return ok(
//                Routes.javascriptRouter("jsRoutes",
//                        controllers.routes.javascript.Signup.forgotPassword()))
//                .as("text/javascript");
//    }

    @Transactional
    public Result doSignup() {
        final Form<MySignup> filledForm =
                MyUsernamePasswordAuthProvider.SIGNUP_FORM.bindFromRequest();
        if (filledForm.hasErrors()) {
            // User did not fill everything properly
            return badRequest(signup.render(filledForm));
        } else {
            // Everything was filled. Do something with your part of the form
            // before handling the user signup
            return UsernamePasswordAuthProvider.handleSignup(ctx());
        }
    }

    public static String formatTimestamp(final long t) {
        return new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").format(new Date(t));
    }

    @SuppressWarnings("unchecked")
    public static <T> T getSingleResult(Class<T> clazz, Query q) {
        List<T> resultList = q.setMaxResults(1).getResultList();
        return resultList.size() > 0 ? resultList.get(0) : null;
    }

    public static void copyProperties(
            final Object source,
            final Object target,
            final Iterable<String> properties) {

        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper tgt = new BeanWrapperImpl(target);

        for (final String propertyName : properties) {
            tgt.setPropertyValue(
                    propertyName,
                    src.getPropertyValue(propertyName)
            );
        }
    }

}
