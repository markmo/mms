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

    public static Result create() {
        return ok(views.html.settings.render(new Long(0), form(Setting.class)));
    }

    @Transactional(readOnly = true)
    public static Result edit(Long id) {
        @SuppressWarnings("unchecked")
        Setting setting = getSingleResult(Setting.class,
                JPA.em().createQuery(
                        "select s from Setting s where s.id = ?1"
                )
                        .setParameter(1, id)
        );
        Form<Setting> settingForm = form(Setting.class).fill(setting);
        return ok(views.html.settings.render(id, settingForm));
    }

    @Transactional
    public static Result update(Long id) {
        Form<Setting> settingForm = form(Setting.class).bindFromRequest();
        if (settingForm.hasErrors()) {
            return badRequest(views.html.settings.render(id, settingForm));
        } else {
            Setting setting = settingForm.get();
            setting.id = id;
            if (id == 0) {
                setting.id = null;
                JPA.em().persist(setting);
            } else {
                JPA.em().merge(setting);
            }
        }
        flash("success", "Settings have been updated");
        return redirect(routes.Application.index());
    }
}
