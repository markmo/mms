package controllers.account;

import static play.data.Form.form;

import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import models.account.SecurityGroup;

/**
 * User: markmo
 * Date: 9/06/13
 * Time: 10:52 AM
 */
public class SecurityGroups extends Controller {

    final static Form<SecurityGroup.SecurityGroupDTO> groupForm = form(SecurityGroup.SecurityGroupDTO.class);

    @Transactional(readOnly = true)
    public static Result create() {
        return ok(views.html.securityGroupForm.render(true, 0, groupForm));
    }

    @Transactional(readOnly = true)
    public static Result edit(int id) {
        SecurityGroup group = JPA.em().find(SecurityGroup.class, id);
        if (group == null) {
            flash("error", "Security Group could not be found");
            return redirect(routes.SecuritySubjects.list(1, "name", "asc", "", ""));
        }
        Form<SecurityGroup.SecurityGroupDTO> filledForm = groupForm.fill(group.getDTO());
        return ok(views.html.securityGroupForm.render(false, id, filledForm));
    }

    @Transactional
    public Result update(int id) {
        Form<SecurityGroup.SecurityGroupDTO> filledForm = groupForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            flash("error", filledForm.errors().toString());
            return badRequest(views.html.securityGroupForm.render(false, id, filledForm));
        } else {
            SecurityGroup.SecurityGroupDTO partialGroup = filledForm.get();
            partialGroup.id = id;
            SecurityGroup group = SecurityGroup.update(partialGroup);
            flash("success", "Security Group '" + group.getName() + "' has been successfully saved");
            return redirect(routes.SecuritySubjects.list(1, "name", "asc", "", ""));
        }
    }
}
