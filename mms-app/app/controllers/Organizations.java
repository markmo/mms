package controllers;

import static play.data.Form.form;

import java.util.Map;

import be.objectify.deadbolt.java.actions.*;
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
public class Organizations extends Controller {

    final static Form<Organization> organizationForm = form(Organization.class);

//    @Transactional(readOnly = true)
//    public static Result index() throws IOException {
//        @SuppressWarnings("unchecked")
//        List<Organization> organizations = JPA.em().createQuery(
//                "select o from Organization o"
//        )
//                .getResultList();
//        return ok(views.html.organizations.render(organizations));
//    }

    /**
     * Display the paginated list of organizations.
     *
     * @param pageIndex Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on organization names
     */
    @Transactional(readOnly = true)
    @Restrict(@Group(Application.SUPERADMIN_ROLE))
    public static Result list(int pageIndex, String sortBy, String order,
                              String filterColumn, String filterValue) {
        return ok(
                views.html.organizations.render(
                        Organization.page(pageIndex, 10, sortBy, order, filterColumn, filterValue),
                        sortBy, order, filterColumn, filterValue
                )
        );
    }

    @Transactional(readOnly = true)
    public static Result findByCode(String code) {
        Organization organization = Organization.findByCode(code);
        String json;
        if (organization != null) {
            json = String.format("{\"name\":\"%s\"}", organization.name);
            return ok(json).as("application/json");
        } else {
            json = String.format("{\"error\":\"%s\"}", "Organization could not be found");
            return notFound(json).as("application/json");
        }
    }

    public static Result create() {
        return ok(views.html.organizationForm.render(true, organizationForm));
    }

    @Transactional(readOnly = true)
    @Restrict({@Group(Application.ADMIN_ROLE), @Group(Application.SUPERADMIN_ROLE)})
    public static Result edit(Long id) {
        Organization organization = JPA.em().find(Organization.class, id);
        if (organization == null) {
            flash("error", "Organization could not be found");
            return redirect(routes.Organizations.list(0, "name", "asc", "", ""));
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
                // Check that the code or name hasn't already been used
                Organization organization1 = Organization.findByCodeOrName(organization.code, organization.name);
                if (organization1 != null) {
                    return badRequest("That organization name or code has already been used.");
                }
                JPA.em().persist(organization);
            } else {
                JPA.em().merge(organization);
            }
            flash("success", "Organization '" + organization.name + "' has been successfully saved");
            return redirect(routes.Organizations.list(0, "name", "asc", "", ""));
        }
    }

    @Transactional
    @Restrict({@Group(Application.ADMIN_ROLE), @Group(Application.SUPERADMIN_ROLE)})
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
        return redirect(routes.Organizations.list(0, "name", "asc", "", ""));
    }
}
