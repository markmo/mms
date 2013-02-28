package controllers;

import java.io.IOException;

import be.objectify.deadbolt.java.actions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import models.User;

/**
 * User: markmo
 * Date: 8/02/13
 * Time: 12:11 AM
 */
public class Users extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    @Restrict(@Group(Application.USER_ROLE))
    public Result profile(Long userId) throws IOException {
        final User user = JPA.em().find(models.User.class, userId);
        String json = mapper.writeValueAsString(user.getDTO());
        return ok(json);
    }

}
