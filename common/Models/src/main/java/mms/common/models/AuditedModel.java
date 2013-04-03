package mms.common.models;

import java.util.Date;
import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.*;

import mms.common.models.posts.DiscussionThread;
import mms.common.models.relational.Table;

/**
 * User: markmo
 * Date: 26/02/13
 * Time: 8:36 PM
 */
@MappedSuperclass
public abstract class AuditedModel {

    @javax.persistence.Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @javax.persistence.Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @javax.persistence.Column(name = "created_by")
    public Long createdBy;

    @javax.persistence.Column(name = "updated_by")
    public Long updatedBy;

    @ManyToAny(metaColumn = @javax.persistence.Column(name = "entity_type"))
    @AnyMetaDef(
            idType = "long", metaType = "string",
            metaValues = {
                    @MetaValue(targetEntity = Catalog.class, value = "CAT"),
                    @MetaValue(targetEntity = Namespace.class, value = "NSP"),
                    @MetaValue(targetEntity = Table.class, value = "TAB"),
                    @MetaValue(targetEntity = Column.class, value = "COL")
            }
    )
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinTable(
            name = "entity_threads",
            joinColumns = @JoinColumn(name = "entity_id"),
            inverseJoinColumns = @JoinColumn(name = "thread_id")
    )
    /*
        This is to prevent a foreign key constraint on entity_id in the join table.
        A constraint against the primary key of each subclass is created by default
        since AuditedModel has no table of its own. This won't work since the sub
        classes do not share keys.

        TODO:
        Rethink whether each subclass should have its own join table to enforce
        referential integrity.
     */
    @ForeignKey(name = "none")
    private Set<DiscussionThread> threads;

    @PrePersist
    public void addAuditInfo() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.createdBy = new Long(0);
        this.updatedBy = new Long(0);
    }

    @PreUpdate
    public void updateAuditInfo() {
        this.updatedAt = new Date();
        this.updatedBy = new Long(0);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<DiscussionThread> getThreads() {
        return threads;
    }

    public void setThreads(Set<DiscussionThread> threads) {
        this.threads = threads;
    }
}
