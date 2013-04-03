package mms.common.models.posts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

/**
 * User: markmo
 * Date: 29/12/12
 * Time: 1:17 PM
 */
@Entity
@Table(name = "discussion_thread")
public class DiscussionThread {

    public DiscussionThread() {
        createdDate = new Date();
    }

    @Id
    @GeneratedValue
    @Column(name = "thread_id")
    private Long id;

    private Date createdDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "discussion_thread_posts",
            joinColumns = @JoinColumn(name = "thread_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    @JsonIgnore
    private List<Post> posts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        if (posts == null) {
            posts = new ArrayList<Post>();
        }
        posts.add(post);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscussionThread that = (DiscussionThread) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
