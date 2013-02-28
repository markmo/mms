package controllers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import models.FilterType;

/**
 * User: markmo
 * Date: 5/11/12
 * Time: 12:39 PM
 */
public class FilterTypes extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    public Result index() throws IOException {
        @SuppressWarnings("unchecked")
        List<FilterType> filterTypes = JPA.em().createQuery(
                "select f from FilterType f"
                )
                .getResultList();
        String json = mapper.writeValueAsString(filterTypes);
        return ok(json).as("application/json");
    }
}
