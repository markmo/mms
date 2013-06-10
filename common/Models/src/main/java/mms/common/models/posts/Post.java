package mms.common.models.posts;

import java.util.Date;
import javax.persistence.*;

import play.data.validation.Constraints;

/**
 * User: markmo
 * Date: 29/12/12
 * Time: 1:19 PM
 */
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String subject;

    @Constraints.Required
    private String message;

    private Date datetime;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Post parent;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    private DiscussionThread thread;

    private int userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Post getParent() {
        return parent;
    }

    public void setParent(Post parent) {
        this.parent = parent;
    }

    public DiscussionThread getThread() {
        return thread;
    }

    public void setThread(DiscussionThread thread) {
        this.thread = thread;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (id != null ? !id.equals(post.id) : post.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
