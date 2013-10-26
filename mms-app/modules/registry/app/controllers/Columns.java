package controllers.registry;

import java.io.IOException;
import java.text.*;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.query.AuditEntity;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import models.domain.Column;

/**
 * User: markmo
 * Date: 6/11/12
 * Time: 9:10 PM
 */
public class Columns extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    public Result index() throws IOException {
        @SuppressWarnings("unchecked")
        List<Column> columns = JPA.em().createQuery(
                "select c from Column c"
                )
                .getResultList();
        String json = mapper.writeValueAsString(columns);
        return ok(json).as("application/json");
    }

    @Transactional(readOnly = true)
    public Result revisions(Long id) throws IOException {
        List revisions = AuditReaderFactory.get(JPA.em()).createQuery()
                .forRevisionsOfEntity(Column.class, false, true)
                .add(AuditEntity.id().eq(id))
                .getResultList();
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Object rev : revisions) {
            Object[] revision = (Object[])rev;
            DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity)revision[1];
            Map<String, Object> entry = new HashMap<String, Object>();
            entry.put("revisionId", revisionEntity.getId());
            entry.put("revisionDate", revisionEntity.getRevisionDate());
            result.add(entry);
        }
        // TODO: move this to a global setting
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        mapper.setDateFormat(df);
        String json = mapper.writeValueAsString(result);
        return ok(json).as("application/json");
    }

    @Transactional(readOnly = true)
    public Result getRevision(Long id, Integer revisionId) throws IOException {
        AuditReader reader = AuditReaderFactory.get(JPA.em());
        Object[] revision = (Object[])reader.createQuery()
                .forRevisionsOfEntity(Column.class, false, true)
                .add(AuditEntity.id().eq(id))
                .add(AuditEntity.revisionNumber().eq(revisionId))
                .getSingleResult();
        DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity)revision[1];
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("revisionId", revisionEntity.getId());
        result.put("revisionDate", revisionEntity.getRevisionDate());
        result.put("column", revision[0]);

        // I need the revision info as well
//        Column column = reader.findRevision(Column.class, revisionId);

        // more verbose alternative to above
//        Column column = (Column)reader.createQuery()
//                .forEntitiesAtRevision(Column.class, revisionId)
//                .add(AuditEntity.id().eq(id))
//                .getSingleResult();

        String json = mapper.writeValueAsString(result);
        return ok(json).as("application/json");
    }

    @Transactional(readOnly = true)
    public Result findColumnsByDatasetId(Long tableId) throws IOException {
        List<Column> columns = Column.findByDatasetId(tableId);
        String json = mapper.writeValueAsString(columns);
        return ok(json).as("application/json");
    }
}
