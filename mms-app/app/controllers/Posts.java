package controllers;

import static play.data.Form.form;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import models.*;
import models.Thread;

import org.codehaus.jackson.JsonNode;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import service.FileService;

/**
 * User: markmo
 * Date: 4/01/13
 * Time: 9:51 AM
 */
public class Posts extends Controller {

    @Inject
    ObjectMapper mapper;

    @Inject
    FileService fileService;

    @Transactional(readOnly = true)
    public Result posts(String entityType, Long entityId) throws IOException {
        List<Post> posts = new ArrayList<Post>();
        if (entityType.equals("dataSource")) {
            DataSource dataSource = JPA.em().find(DataSource.class, entityId);
            if (dataSource.threads != null && !dataSource.threads.isEmpty()) {
                for (models.Thread thread : dataSource.threads) {
                    posts.addAll(thread.posts);
                }
            }
        }
        String json = mapper.writeValueAsString(posts);
        return ok(json).as("application/json");
    }

    @Transactional
    public Result create(String entityType, Long entityId) throws IOException {
        Form<PostInput> postInputForm = form(PostInput.class);
        PostInput postInput = postInputForm.bindFromRequest().get();
        if (postInput.entityType.equals("dataSource")) {
            DataSource dataSource = JPA.em().find(DataSource.class, postInput.entityId);
            Thread thread = null;
            if (dataSource.threads == null || dataSource.threads.isEmpty()) {
                thread = new Thread();
                dataSource.threads = new HashSet<Thread>(Collections.singletonList(thread));
            } else {
                thread = dataSource.threads.iterator().next();
            }

            // TODO: replace with authenticated user
            User user = JPA.em().find(User.class, postInput.userId);

            Post parent = null;
            if (postInput.parentId != null) {
                parent = JPA.em().find(Post.class, postInput.parentId);
            }

            Post post = new Post();
            post.datetime = new Date();
            post.subject = postInput.subject;
            post.message = postInput.message;
            post.parent = parent;
            post.thread = thread;
            post.user = user;
            if (thread.posts == null) {
                thread.posts = new ArrayList<Post>();
            }
            thread.posts.add(post);
            JPA.em().persist(thread);
            JPA.em().flush();
            JPA.em().persist(dataSource);
        }
        return ok();
    }

    public Result listAll() {
        fileService.listAll();
        return ok();
    }

    @Transactional(readOnly = true)
    public Result list(String category, Long id) {
        List<FileInput> files = FileInput.findByEntityTypeAndId(category, id);
        ObjectNode root = mapper.createObjectNode();
        ArrayNode filesArray = mapper.createArrayNode();
        for (FileInput file : files) {
            filesArray.add(mapper.valueToTree(file));
        }
        root.set("files", filesArray);
        return ok(root.toString());
    }

    @Transactional
    public Result uploadMetadata() throws IOException {
        Form<FileInput> fileForm = form(FileInput.class);
        if (fileForm.hasErrors()) {
            return badRequest();
        } else {
            FileInput file = fileForm.bindFromRequest().get();
            JPA.em().persist(file);
            ObjectNode root = mapper.createObjectNode();
            ArrayNode filesArray = mapper.createArrayNode();
            filesArray.add(mapper.valueToTree(file));
            root.set("files", filesArray);
            return ok(root.toString());
        }
    }

    @Transactional
    public Result uploadFile(Long id) {
        File file = request().body().asRaw().asFile();
        FileInput fileInput = JPA.em().find(FileInput.class, id);
        fileInput.file = file;
        System.out.println("uploaded file: " + fileInput.name);
        FileContext context = new FileContext(fileInput.entityType, fileInput.entityId);
        fileService.store(context, fileInput);
        ObjectNode root = mapper.createObjectNode();
        ArrayNode filesArray = mapper.createArrayNode();
        ObjectNode fileNode = mapper.valueToTree(fileInput);
        fileNode.put("delete_url", "/upload/" + fileInput.id);
        filesArray.add(fileNode);
        root.set("files", filesArray);
        return ok(root.toString());
    }

    @Transactional
    public Result deleteFile(Long id) {
        JPA.em().createQuery("delete from FileInput where id = ?1").setParameter(1, id);
        return ok();
    }

    public WebSocket<JsonNode> chat(final String username) {
        return new WebSocket<JsonNode>() {
            @Override
            public void onReady(In<JsonNode> in, Out<JsonNode> out) {
                try {
                    ChatRoom.join(username, in, out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static class PostInput {

        public String entityType;
        public Long entityId;
        public Long parentId;
        public Long userId;
        public String subject;
        public String message;
    }
}
