package controllers.account;

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
import models.account.Organization;
import models.account.User;
import views.html.*;

public class Application extends Controller {

    public static final String FLASH_MESSAGE_KEY = "message";
    public static final String FLASH_ERROR_KEY = "error";
    public static final String USER_ROLE = "user";
    public static final String ADMIN_ROLE = "admin";
    public static final String SUPERADMIN_ROLE = "superadmin";

    @Inject
    ObjectMapper mapper;

    public Result index() {
        return ok();
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
        final Form<Organization> organizationForm = form(Organization.class);
        return ok(signup.render(MyUsernamePasswordAuthProvider.SIGNUP_FORM, organizationForm));
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
        final Form<Organization> organizationForm = form(Organization.class);
        if (filledForm.hasErrors()) {
            // User did not fill everything properly
            return badRequest(signup.render(filledForm, organizationForm));
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
