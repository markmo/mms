package controllers.account;

import static play.data.Form.form;

import be.objectify.deadbolt.java.actions.*;
import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;
import play.data.Form;
import play.data.format.Formats.NonEmpty;
import play.data.validation.Constraints.*;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.i18n.Messages;
import play.mvc.*;
import providers.account.MyUsernamePasswordAuthProvider;
import providers.account.MyUsernamePasswordAuthUser;

import models.account.User;
import views.html.account.*;

public class Account extends Controller {

    public static class Accept {

        @Required
        @NonEmpty
        public Boolean accept;

        public Boolean getAccept() {
            return accept;
        }

        public void setAccept(Boolean accept) {
            this.accept = accept;
        }

    }

    public static class PasswordChange {

        @MinLength(5)
        @Required
        public String password;

        @MinLength(5)
        @Required
        public String repeatPassword;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRepeatPassword() {
            return repeatPassword;
        }

        public void setRepeatPassword(String repeatPassword) {
            this.repeatPassword = repeatPassword;
        }

        public String validate() {
            if (password == null || !password.equals(repeatPassword)) {
                return Messages
                        .get("playauthenticate.change_password.error.passwords_not_same");
            }
            return null;
        }
    }

    private static final Form<Accept> ACCEPT_FORM = form(Accept.class);
    private static final Form<Account.PasswordChange> PASSWORD_CHANGE_FORM = form(Account.PasswordChange.class);
    private static final Form<User.UserDTO> USER_FORM = form(User.UserDTO.class);

    @Transactional(readOnly = true)
    @Restrict(@Group(Application.USER_ROLE))
    public static Result edit(int id) {
        User user = JPA.em().find(User.class, id);
        Form<User.UserDTO> userForm = USER_FORM.fill(user.getDTO());
        return ok(edit.render(id, userForm));
    }

    @Transactional
    @Restrict(@Group(Application.USER_ROLE))
    public Result update(int id) {
        Form<User.UserDTO> boundForm = USER_FORM.bindFromRequest();
        if (boundForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(edit.render(id, boundForm));
        }
        User user = User.update(boundForm.get());
        flash("success",
                String.format("Successfully updated user %s", user));

        return redirect(controllers.account.routes.Application.index());
    }

    @SubjectPresent
    public static Result link() {
        return ok(link.render());
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result verifyEmail() {
        final User user = Application.getLocalUser(session());
        if (user.emailValidated) {
            // E-Mail has been validated already
            flash(Application.FLASH_MESSAGE_KEY,
                    Messages.get("playauthenticate.verify_email.error.already_validated"));
        } else if (user.email != null && !user.email.trim().isEmpty()) {
            flash(Application.FLASH_MESSAGE_KEY, Messages.get(
                    "playauthenticate.verify_email.message.instructions_sent",
                    user.email));
            MyUsernamePasswordAuthProvider.getProvider()
                    .sendVerifyEmailMailingAfterSignup(user, ctx());
        } else {
            flash(Application.FLASH_MESSAGE_KEY, Messages.get(
                    "playauthenticate.verify_email.error.set_email_first",
                    user.email));
        }
        return redirect(controllers.account.routes.Application.profile());
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result changePassword() {
        final User u = Application.getLocalUser(session());

        if (!u.emailValidated) {
            return ok(unverified.render());
        } else {
            return ok(password_change.render(PASSWORD_CHANGE_FORM));
        }
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result doChangePassword() {
        final Form<Account.PasswordChange> filledForm =
                PASSWORD_CHANGE_FORM.bindFromRequest();
        if (filledForm.hasErrors()) {
            // User did not select whether to link or not link
            return badRequest(password_change.render(filledForm));
        } else {
            final User user = Application.getLocalUser(session());
            final String newPassword = filledForm.get().password;
            user.changePassword(new MyUsernamePasswordAuthUser(newPassword),
                    true);
            flash(Application.FLASH_MESSAGE_KEY,
                    Messages.get("playauthenticate.change_password.success"));
            return redirect(controllers.account.routes.Application.profile());
        }
    }

    @SubjectPresent
    public static Result askLink() {
        final AuthUser u = PlayAuthenticate.getLinkUser(session());
        if (u == null) {
            // account to link could not be found, silently redirect to login
            return redirect(controllers.account.routes.Application.index());
        }
        return ok(ask_link.render(ACCEPT_FORM, u));
    }

    @SubjectPresent
    public static Result doLink() {
        final AuthUser u = PlayAuthenticate.getLinkUser(session());
        if (u == null) {
            // account to link could not be found, silently redirect to login
            return redirect(controllers.account.routes.Application.index());
        }

        final Form<Accept> filledForm = ACCEPT_FORM.bindFromRequest();
        if (filledForm.hasErrors()) {
            // User did not select whether to link or not link
            return badRequest(ask_link.render(filledForm, u));
        } else {
            // User made a choice :)
            final boolean link = filledForm.get().accept;
            if (link) {
                flash(Application.FLASH_MESSAGE_KEY,
                        Messages.get("playauthenticate.accounts.link.success"));
            }
            return PlayAuthenticate.link(ctx(), link);
        }
    }

    @SubjectPresent
    public static Result askMerge() {
        // this is the currently logged in user
        final AuthUser aUser = PlayAuthenticate.getUser(session());

        // this is the user that was selected for a login
        final AuthUser bUser = PlayAuthenticate.getMergeUser(session());
        if (bUser == null) {
            // user to merge with could not be found, silently redirect to login
            return redirect(controllers.account.routes.Application.index());
        }

        // You could also get the local user object here via
        // User.findByAuthUserIdentity(newUser)
        return ok(ask_merge.render(ACCEPT_FORM, aUser, bUser));
    }

    @SubjectPresent
    public static Result doMerge() {
        // this is the currently logged in user
        final AuthUser aUser = PlayAuthenticate.getUser(session());

        // this is the user that was selected for a login
        final AuthUser bUser = PlayAuthenticate.getMergeUser(session());
        if (bUser == null) {
            // user to merge with could not be found, silently redirect to login
            return redirect(controllers.account.routes.Application.index());
        }

        final Form<Accept> filledForm = ACCEPT_FORM.bindFromRequest();
        if (filledForm.hasErrors()) {
            // User did not select whether to merge or not merge
            return badRequest(ask_merge.render(filledForm, aUser, bUser));
        } else {
            // User made a choice :)
            final boolean merge = filledForm.get().accept;
            if (merge) {
                flash(Application.FLASH_MESSAGE_KEY,
                        Messages.get("playauthenticate.accounts.merge.success"));
            }
            return PlayAuthenticate.merge(ctx(), merge);
        }
    }
}
