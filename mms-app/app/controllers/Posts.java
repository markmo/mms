package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import models.*;
import models.Thread;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.util.*;

/**
 * User: markmo
 * Date: 4/01/13
 * Time: 9:51 AM
 */
public class Posts extends Controller {

    @Inject
    static ObjectMapper mapper;

    @Transactional(readOnly = true)
    public static Result posts(String entityType, Long entityId) throws IOException {
        List<Post> posts = new ArrayList<Post>();
        if (entityType.equals("datasource")) {
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
    public static Result create(String entityType, Long entityId) throws IOException {
        Form<PostInput> postInputForm = form(PostInput.class);
        PostInput postInput = postInputForm.bindFromRequest().get();
        if (postInput.entityType.equals("datasource")) {
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

    public static class PostInput {

        public String entityType;
        public Long entityId;
        public Long parentId;
        public Long userId;
        public String subject;
        public String message;
    }
}
