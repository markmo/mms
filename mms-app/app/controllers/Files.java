package controllers;

import static play.data.Form.form;
import static play.libs.F.Function;
import static play.libs.F.Promise;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import indexing.ColumnIndex;
import indexing.DatasetIndex;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Akka;
import play.libs.F;
import play.mvc.*;

import mms.common.models.AbstractColumn;
import mms.common.models.Column;
import mms.common.models.file.FileContext;
import mms.common.models.file.FileUpload;
import mms.common.models.file.FlatFile;
import service.FileRepoService;
import service.FileService;

/**
 * User: markmo
 * Date: 6/03/13
 * Time: 9:36 AM
 */
public class Files extends Controller {

    @Inject
    ObjectMapper mapper;

    @Inject
    FileRepoService repo;

    @Inject
    FileService fileService;

    @Transactional
    public Result uploadMetadata() throws IOException {
        Form<FileUpload> fileForm = form(FileUpload.class);
        if (fileForm.hasErrors()) {
            return badRequest();
        } else {
            FileUpload file = fileForm.bindFromRequest().get();
            JPA.em().persist(file);
            ObjectNode root = mapper.createObjectNode();
            ArrayNode filesArray = mapper.createArrayNode();
            filesArray.add(mapper.valueToTree(file));
            root.set("files", filesArray);
            return ok(root.toString());
        }
    }

    public Result uploadFile(final Long id) throws Throwable {
        final File file = request().body().asRaw().asFile();
        final FileUpload upload = JPA.withTransaction(new F.Function0<FileUpload>() {
            @Override
            public FileUpload apply() {
                return JPA.em().find(FileUpload.class, id);
            }
        });
        final FlatFile flatFile = JPA.withTransaction(new F.Function0<FlatFile>() {
            @Override
            public FlatFile apply() {
                return JPA.em().find(FlatFile.class, upload.getEntityId());
            }
        });
        upload.setFile(file);
        System.out.println("uploaded file: " + upload.getName());
        FileContext context = new FileContext(upload.getEntityType(), upload.getEntityId());
        repo.store(context, upload);

        Promise<String> analyzeFile = Akka.future(
            new Callable<String>() {
                public String call() {
                    try {
                        return JPA.withTransaction(new F.Function0<String>() {
                            @Override
                            public String apply() {
                                return fileService.extractMetadata(upload.getName(), file, flatFile);
                            }
                        });
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                        return "";
                    }
                }
            }
        );
        return async(
            analyzeFile.map(
                new Function<String, Result>() {
                    public Result apply(String html) {
                        upload.setAnalysisResults(html);
                        JPA.withTransaction(new F.Callback0() {
                            @Override
                            public void invoke() {
                                JPA.em().merge(upload);

                                FlatFile ff = flatFile;

                                if (!JPA.em().contains(ff)) {
                                    ff = JPA.em().find(FlatFile.class, ff.getId());
                                }
                                for (AbstractColumn column : ff.getColumns()) {
                                    ColumnIndex columnIndex = new ColumnIndex();
                                    columnIndex.setColumn((Column)column);
                                    columnIndex.index();
                                }
                                DatasetIndex datasetIndex = new DatasetIndex();
                                datasetIndex.setDataset(ff);
                                datasetIndex.index();
                            }
                        });

                        ObjectNode root = mapper.createObjectNode();
                        ArrayNode filesArray = mapper.createArrayNode();
                        ObjectNode fileNode = mapper.valueToTree(upload);
                        fileNode.put("delete_url", "/upload/" + upload.getId());
                        filesArray.add(fileNode);
                        root.set("files", filesArray);
                        return ok(root.toString());
                    }
                }
            )
        );
    }

    public Result deleteAll() {
        repo.deleteAll();
        return ok();
    }

    @Transactional
    public Result deleteFile(Long id) {
        JPA.em().createQuery("delete from FileUpload where id = ?1").setParameter(1, id);
        return ok();
    }

    public Result index(String path) {
        List<Map<String, Object>> files = repo.index(path);
        ArrayNode filesArray = mapper.createArrayNode();
        for (Map file : files) {
            filesArray.add(mapper.valueToTree(file));
        }
        return ok(filesArray.toString());
    }

    public Result listAll() {
        repo.listAll();
        repo.getBinary("test3.csv");
        return ok();
    }

    @Transactional(readOnly = true)
    public Result list(String category, Long id) {
        List<FileUpload> files = FileUpload.findByEntityTypeAndId(category, id);
        ObjectNode root = mapper.createObjectNode();
        ArrayNode filesArray = mapper.createArrayNode();
        for (FileUpload file : files) {
            filesArray.add(mapper.valueToTree(file));
        }
        root.set("files", filesArray);
        return ok(root.toString());
    }
}
