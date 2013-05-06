package controllers;

import static play.data.Form.form;
import static utils.JPA_Helper.getSingleResult;

import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import models.Setting;

/**
 * User: markmo
 * Date: 2/05/13
 * Time: 9:28 AM
 */
public class Settings extends Controller {

    @Transactional(readOnly = true)
    public static Result show() {
        @SuppressWarnings("unchecked")
        Setting setting = getSingleResult(Setting.class,
                JPA.em().createQuery(
                        "select s from Setting s"
                )
        );
        return ok(setting.customSchema).as("application/json");
    }

    @Transactional(readOnly = true)
    public static Result edit() {
        @SuppressWarnings("unchecked")
        Setting setting = getSingleResult(Setting.class,
                JPA.em().createQuery(
                        "select s from Setting s"
                )
        );
        Form<Setting> settingForm = form(Setting.class).fill(setting);
        return ok(views.html.settings.render(setting.id, settingForm));
    }

    @Transactional
    public static Result update(Long id) {
        Form<Setting> settingForm = form(Setting.class).bindFromRequest();
        if (settingForm.hasErrors()) {
            return badRequest(views.html.settings.render(id, settingForm));
        } else {
            Setting setting = settingForm.get();
            setting.id = id;
            JPA.em().merge(setting);
        }
        flash("success", "Settings have been updated");
        return redirect(routes.Application.index());
    }
}
