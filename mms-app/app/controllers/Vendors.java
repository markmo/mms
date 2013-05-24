package controllers;

import static play.data.Form.form;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.codehaus.jackson.JsonNode;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import mms.common.models.business.*;

/**
 * User: markmo
 * Date: 16/05/13
 * Time: 3:29 PM
 */
public class Vendors extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    public Result index() throws IOException {
        @SuppressWarnings("unchecked")
        List<Vendor> vendors = JPA.em().createQuery(
                "select v from Vendor v"
        )
                .getResultList();
        String json = mapper.writeValueAsString(vendors);
        return ok(json).as("application/json");
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        Form<Vendor> vendorForm = form(Vendor.class);
        Vendor vendor = vendorForm.bindFromRequest().get();
        if (vendorForm.hasErrors()) {
            return badRequest();
        } else {
            JPA.em().persist(vendor);
            return ok("{\"id\":\"" + vendor.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(Long id) {
        Form<Vendor> vendorForm = form(Vendor.class);
        JsonNode json = request().body().asJson();
        Vendor vendor = vendorForm.bind(json, "name").get();
        if (vendorForm.hasErrors()) {
            return badRequest();
        } else {
            vendor.setId(id);
            JPA.em().merge(vendor);
            return ok("{\"id\":\"" + vendor.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    public static Result delete() {
        String[] ids = request().body().asFormUrlEncoded().get("id[]");
        for (String id : ids) {
            Vendor vendor = JPA.em().find(Vendor.class, Long.parseLong(id));
            if (vendor != null) {
                JPA.em().remove(vendor);
            }
        }
        return ok();
    }
}
