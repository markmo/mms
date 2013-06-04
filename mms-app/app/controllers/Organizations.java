package controllers;

import static play.data.Form.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import be.objectify.deadbolt.java.actions.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import models.Organization;

/**
 * User: markmo
 * Date: 1/06/13
 * Time: 8:28 PM
 */
//@Restrict(@Group(Application.USER_ROLE))
public class Organizations extends Controller {

    final static Form<Organization> organizationForm = form(Organization.class);

    @Transactional(readOnly = true)
    public static Result index() throws IOException {
        @SuppressWarnings("unchecked")
        List<Organization> organizations = JPA.em().createQuery(
                "select o from Organization o"
        )
                .getResultList();
        return ok(views.html.organizations.render(organizations));
    }

    public static Result create() {
        return ok(views.html.organizationForm.render(true, organizationForm));
    }

    @Transactional(readOnly = true)
    public static Result edit(Long id) {
        Organization organization = JPA.em().find(Organization.class, id);
        if (organization == null) {
            flash("error", "Organization could not be found");
            return redirect(routes.Organizations.index());
        }
        Form<Organization> filledForm = organizationForm.fill(organization);
        return ok(views.html.organizationForm.render(false, filledForm));
    }

    @Transactional
    public static Result update() {
        Form<Organization> filledForm = organizationForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(views.html.organizationForm.render(false, filledForm));
        } else {
            Organization organization = filledForm.get();
            if (organization.id == null) {
                JPA.em().persist(organization);
            } else {
                JPA.em().merge(organization);
            }
            flash("success", "Organization '" + organization.name + "' has been successfully saved");
            return redirect(routes.Organizations.index());
        }
    }

    @Transactional
    public static Result delete() {
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        String[] deletions = params.get("deletions[]");
        for (int i = 0; i < deletions.length; i++) {
            Long id = Long.parseLong(deletions[i]);
            Organization organization = JPA.em().find(Organization.class, id);
            if (organization != null) {
                JPA.em().remove(organization);
            }
        }
        if (deletions.length == 1) {
            flash("success", "The selected organization has been deleted");
        } else {
            flash("success", "The selected organizations have been deleted");
        }
        return redirect(routes.Organizations.index());
    }
}
