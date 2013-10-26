package controllers.social;

import static play.data.Form.form;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import models.account.User;
import models.domain.Catalog;
import models.domain.Datasource;
import models.domain.business.BusinessTerm;
import models.domain.posts.DiscussionThread;
import models.domain.posts.Post;
import models.social.ChatRoom;

/**
 * User: markmo
 * Date: 4/01/13
 * Time: 9:51 AM
 */
public class Posts extends Controller {

    @Inject
    ObjectMapper mapper;

    @Transactional(readOnly = true)
    public Result posts(String entityType, Long entityId) throws IOException {
        List<Post> posts = new ArrayList<Post>();
        if (entityType.equals("datasource")) {
            Datasource datasource = JPA.em().find(Datasource.class, entityId);
            if (datasource.getThreads() != null && !datasource.getThreads().isEmpty()) {
                for (DiscussionThread thread : datasource.getThreads()) {
                    posts.addAll(thread.getPosts());
                }
            }
        } else if (entityType.equals("catalog")) {
            Catalog catalog = JPA.em().find(Catalog.class, entityId);
            if (catalog.getThreads() != null && !catalog.getThreads().isEmpty()) {
                for (DiscussionThread thread : catalog.getThreads()) {
                    posts.addAll(thread.getPosts());
                }
            }
        } else if (entityType.equals("term")) {
            BusinessTerm term = JPA.em().find(BusinessTerm.class, entityId);
            if (term.getThreads() != null && !term.getThreads().isEmpty()) {
                for (DiscussionThread thread : term.getThreads()) {
                    posts.addAll(thread.getPosts());
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
        Datasource datasource = null;
        Catalog catalog = null;
        BusinessTerm term = null;
        DiscussionThread thread = null;
        if (postInput.entityType.equals("datasource")) {
            datasource = JPA.em().find(Datasource.class, postInput.entityId);
            if (datasource.getThreads() == null || datasource.getThreads().isEmpty()) {
                thread = new DiscussionThread();
                datasource.setThreads(new HashSet<DiscussionThread>(Collections.singletonList(thread)));
            } else {
                thread = datasource.getThreads().iterator().next();
            }
        } else if (postInput.entityType.equals("catalog")) {
            catalog = JPA.em().find(Catalog.class, postInput.entityId);
            if (catalog.getThreads() == null || catalog.getThreads().isEmpty()) {
                thread = new DiscussionThread();
                catalog.setThreads(new HashSet<DiscussionThread>(Collections.singletonList(thread)));
            } else {
                thread = catalog.getThreads().iterator().next();
            }
        } else if (postInput.entityType.equals("term")) {
            term = JPA.em().find(BusinessTerm.class, postInput.entityId);
            if (term.getThreads() == null || term.getThreads().isEmpty()) {
                thread = new DiscussionThread();
                term.setThreads(new HashSet<DiscussionThread>(Collections.singletonList(thread)));
            } else {
                thread = term.getThreads().iterator().next();
            }
        }
        if (thread != null) {

            // TODO: replace with authenticated user
            User user = JPA.em().find(User.class, postInput.userId);

            Post parent = null;
            if (postInput.parentId != null) {
                parent = JPA.em().find(Post.class, postInput.parentId);
            }

            Post post = new Post();
            post.setDatetime(new Date());
            post.setSubject(postInput.subject);
            post.setMessage(postInput.message);
            post.setParent(parent);
            post.setThread(thread);
            post.setUserId(user.getId());
            if (thread.getPosts() == null) {
                thread.setPosts(new ArrayList<Post>());
            }
            thread.addPost(post);
            JPA.em().persist(thread);
            JPA.em().flush();
            if (postInput.entityType.equals("datasource")) {
                JPA.em().persist(datasource);
            } else if (postInput.entityType.equals("catalog")) {
                JPA.em().persist(catalog);
            } else if (postInput.entityType.equals("term")) {
                JPA.em().persist(term);
            }
        }
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
        public int userId;
        public String subject;
        public String message;
    }
}
