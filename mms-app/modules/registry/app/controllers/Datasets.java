package controllers.registry;

import static play.data.Form.form;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import indexing.DatasetIndex;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.F;
import play.mvc.*;

import models.domain.Dataset;
import models.domain.file.FileUpload;
import models.domain.file.FlatFile;
import models.domain.relational.Table;

/**
 * User: markmo
 * Date: 3/11/12
 * Time: 10:24 AM
 */
public class Datasets extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    public Result index() throws IOException {
        @SuppressWarnings("unchecked")
        List<Dataset> datasets = JPA.em().createQuery(
                "select s from Dataset s"
                )
                .getResultList();
        String json = mapper.writeValueAsString(datasets);
        return ok(json);
    }

    @Transactional
    public static Result create() {
        DynamicForm requestData = form().bindFromRequest();
        String type = requestData.get("type");
        Form datasetForm;
        if ("TAB".equals(type)) {
            datasetForm = form(Table.class);
        } else {
            datasetForm = form(FlatFile.class);
        }
        Dataset dataset = (Dataset)datasetForm.bindFromRequest().get();
        if (datasetForm.hasErrors()) {
            return badRequest();
        } else {
            JPA.em().persist(dataset);

            // done in file upload
//            DatasetIndex datasetIndex = new DatasetIndex();
//            datasetIndex.setDataset(dataset);
//            datasetIndex.index();
            return ok("{\"id\":\"" + dataset.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    public static Result update(Long id) {
        DynamicForm requestData = form().bindFromRequest();
        String type = requestData.get("type");
        Form datasetForm;
        if ("TAB".equals(type)) {
            datasetForm = form(Table.class);
        } else {
            datasetForm = form(FlatFile.class);
        }
        Dataset boundDataset = (Dataset)datasetForm.bindFromRequest().get();
        if (datasetForm.hasErrors()) {
            return badRequest();
        } else {
            Dataset dataset = Dataset.update(boundDataset);
            return ok("{\"id\":\"" + dataset.getId() + "\"}").as("application/json");
        }
    }

    @Transactional
    public static Result delete(Long id) {
        return TODO;
    }

    public static Result stats(final Long datasetId) throws Throwable {
        String result = JPA.withTransaction(new F.Function0<String>() {
            @Override
            public String apply() {
                List<FileUpload> uploads = FileUpload.findByEntityTypeAndId("FlatFile", datasetId);
                if (uploads.size() > 0) {// TODO should be == 1
                    return uploads.get(0).getAnalysisResults();
                }
                return "";
            }
        });
        return ok(result);
    }

    @Transactional(readOnly = true)
    public Result findDatasetsByNamespaceId(Long namespaceId) throws IOException {
        List<Dataset> datasets = Dataset.findByNamespaceId(namespaceId);
        String json = mapper.writeValueAsString(datasets);
        return ok(json).as("application/json");
    }
}
