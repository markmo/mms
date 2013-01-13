package models;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

/**
 * User: markmo
 * Date: 12/01/13
 * Time: 4:53 PM
 */
@Entity
@Table(name = "ds_activity")
public class Activity {

    enum ActivityType {
        POST,
        ENTITY_CHANGE
    }

    @Id
    @GeneratedValue
    public Long id;

    public Date datetime;

    public String description;

    public String message;

    public ActivityType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}
