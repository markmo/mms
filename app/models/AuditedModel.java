package models;

import javax.persistence.*;
import java.util.Date;

/**
 * User: markmo
 * Date: 27/09/12
 * Time: 5:23 PM
 */
@MappedSuperclass
public class AuditedModel {

    @javax.persistence.Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;

    @javax.persistence.Column(name="updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date updatedAt;

    @javax.persistence.Column(name="created_by")
    public int createdBy;

    @javax.persistence.Column(name="updated_by")
    public int updatedBy;

    @PrePersist
    public void addAuditInfo() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.createdBy = 0;
        this.updatedBy = 0;
    }

    @PreUpdate
    public void updateAuditInfo() {
        this.updatedAt = new Date();
        this.updatedBy = 0;
    }
}
