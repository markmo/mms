package controllers.glossary;

import static play.data.Form.form;

import java.io.IOException;
import java.text.*;
import java.util.*;
import javax.persistence.Query;

import be.objectify.deadbolt.java.actions.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.user.AuthUser;
import com.google.inject.Inject;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import providers.account.MyUsernamePasswordAuthProvider;
import providers.account.MyUsernamePasswordAuthProvider.MyLogin;
import providers.account.MyUsernamePasswordAuthProvider.MySignup;

import models.domain.Catalog;
import models.domain.Datasource;
import models.domain.Namespace;
import models.domain.Sandbox;
import models.account.Organization;
import models.account.User;
import models.domain.audit.CustomTrackingRevisionEntity;
import models.domain.audit.ModifiedEntityTypeEntity;
import models.domain.relational.Table;
import views.html.*;

public class Application extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional
    @Restrict(@Group(controllers.account.Application.USER_ROLE))
    public Result index() {
        if (session("theme") == null) {
            session("theme", "spacelab");
        }
        return ok(
                tables.render("datasources")
        );
    }

    public Result theme(String theme) {
        session("theme", theme);
        return redirect(routes.Application.index());
    }

    @Transactional
    public Result glossary() {
        return ok(views.html.tables.render("glossary"));
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
