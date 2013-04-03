package mms.common.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.*;

/**
 * User: markmo
 * Date: 26/02/13
 * Time: 8:28 PM
 */
@Entity
public class Activity {

    enum ActivityType {
        POST,
        ENTITY_CHANGE
    }

    @Id
    @GeneratedValue
    @Column(name = "activity_id")
    private Long id;

    private Date datetime;

    private String description;

    private String message;

    @Column(name = "activity_type")
    private ActivityType type;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return message + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (id != null ? !id.equals(activity.id) : activity.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
