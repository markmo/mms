package controllers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import models.DataSource;

/**
 * User: markmo
 * Date: 4/11/12
 * Time: 1:39 PM
 */
public class DataSources extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    public Result index() throws IOException {
        @SuppressWarnings("unchecked")
        List<DataSource> dataSources = JPA.em().createQuery(
                "select d from DataSource d"
                )
                .getResultList();
        String json = mapper.writeValueAsString(dataSources);
        return ok(json).as("application/json");
    }
}
