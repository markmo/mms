package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import be.objectify.deadbolt.actions.Restrict;
import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import play.Routes;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthProvider.MyLogin;
import providers.MyUsernamePasswordAuthProvider.MySignup;

import models.DataSource;
import models.Table;
import models.User;
import views.html.*;

public class Application extends Controller {

    public static final String FLASH_MESSAGE_KEY = "message";
    public static final String FLASH_ERROR_KEY = "error";
    public static final String USER_ROLE = "user";

    static Form<Table> tableForm = form(Table.class);

    public static Result index() {
//        return redirect(routes.Tables.index());
        return ok(
                views.html.tables.render()
        );
    }

    public static Result tables() {
        return ok(
                views.html.index.render(
                        JPA.em().createQuery("from Table").getResultList(),
//                        Table.find.all(),
                        tableForm)
        );
    }

    public static Result newTable() {
        return TODO;
    }

    public static Result deleteTable(Long id) {
        return TODO;
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result importSchema() throws Exception {
        Http.RequestBody body = request().body();
        JsonNode json = request().body().asJson();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DataSource dataSource = mapper.readValue(json, DataSource.class);
//        dataSource.save();
        JPA.em().persist(dataSource);
        return ok();
    }

    public static User getLocalUser(final Http.Session session) {
        final User localUser = User.findByAuthUserIdentity(
                PlayAuthenticate.getUser(session));
        return localUser;
    }

    @Restrict(Application.USER_ROLE)
    public static Result restricted() {
        final User localUser = getLocalUser(session());
        return ok(restricted.render(localUser));
    }

    @Restrict(Application.USER_ROLE)
    public static Result profile() {
        final User localUser = getLocalUser(session());
        return ok(profile.render(localUser));
    }

    public static Result login() {
        return ok(login.render(MyUsernamePasswordAuthProvider.LOGIN_FORM));
    }

    public static Result doLogin() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
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

    public static Result signup() {
        return ok(signup.render(MyUsernamePasswordAuthProvider.SIGNUP_FORM));
    }

    public static Result jsRoutes() {
        return ok(
                Routes.javascriptRouter("jsRoutes",
                        controllers.routes.javascript.Signup.forgotPassword()))
                .as("text/javascript");
    }

    public static Result doSignup() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
        final Form<MySignup> filledForm =
                MyUsernamePasswordAuthProvider.SIGNUP_FORM.bindFromRequest();
        if (filledForm.hasErrors()) {
            // User did not fill everything properly
            return badRequest(signup.render(filledForm));
        } else {
            // Everything was filled
            // do something with your part of the form before handling the user
            // signup
            return UsernamePasswordAuthProvider.handleSignup(ctx());
        }
    }

    public static String formatTimestamp(final long t) {
        return new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").format(new Date(t));
    }
}
