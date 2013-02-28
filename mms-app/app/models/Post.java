package models;

import play.data.validation.Constraints;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.*;

/**
 * User: markmo
 * Date: 29/12/12
 * Time: 1:19 PM
 */
@Entity
@Table(name = "ds_post")
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    public long id;

    public String subject;

    @Constraints.Required
    public String message;

    public Date datetime;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    public Post parent;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    public Thread thread;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

}
