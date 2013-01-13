package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * User: markmo
 * Date: 29/12/12
 * Time: 1:17 PM
 */
@Entity
@Table(name = "ds_thread")
public class Thread {

    public Thread() {
        createdDate = new Date();
    }

    @Id
    @GeneratedValue
    @Column(name = "thread_id")
    public long id;

    public Date createdDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    public List<Post> posts;

}
