package controllers.account;

import static play.data.Form.form;

import java.io.IOException;

import be.objectify.deadbolt.java.actions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import providers.account.MyUsernamePasswordAuthProvider;

import models.account.User;

/**
 * User: markmo
 * Date: 8/02/13
 * Time: 12:11 AM
 */
public class Users extends Controller {

    final static Form<User.UserDTO> userForm = form(User.UserDTO.class);

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    @Restrict(@Group(Application.USER_ROLE))
    public Result profile(Long userId) throws IOException {
        final User user = JPA.em().find(User.class, userId);
        String json = mapper.writeValueAsString(user.getDTO());
        return ok(json);
    }

    @Transactional(readOnly = true)
    public static Result create() {
        return ok(views.html.userForm.render(true, 0, userForm));
    }

    @Transactional(readOnly = true)
    public static Result edit(int id) {
        User user = JPA.em().find(User.class, id);
        if (user == null) {
            flash("error", "User could not be found");
            return redirect(routes.SecuritySubjects.list(1, "name", "asc", "", ""));
        }
        Form<User.UserDTO> filledForm = userForm.fill(user.getDTO());
        return ok(views.html.userForm.render(false, id, filledForm));
    }

    @Transactional
    public Result update(int id) {
        Form<User.UserDTO> filledForm = userForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(views.html.userForm.render(false, id, filledForm));
        } else {
            User.UserDTO userDTO = filledForm.get();
            userDTO.id = id;
            User user = User.update(userDTO);
            flash("success", "User '" + user.getName() + "' has been successfully saved");
            return redirect(routes.SecuritySubjects.list(1, "name", "asc", "", ""));
        }
    }

    @Transactional
    public static Result resendVerificationEmail(int userId) {
        final User user = JPA.em().find(User.class, userId);
        if (user.emailValidated) {
            flash("info", "Email for " + user.getName() + " has already been validated");
        } else {
            MyUsernamePasswordAuthProvider.getProvider()
                    .sendVerifyEmailMailingAfterSignup(user, ctx());
            flash("success", "Verification email for " + user.getName() + " has been sent");
        }
        return redirect(routes.SecuritySubjects.list(0, "name", "asc", "", ""));
    }
}
