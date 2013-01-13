package models;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Column;
import java.util.Date;
import java.util.Set;

/**
 * User: markmo
 * Date: 27/09/12
 * Time: 5:23 PM
 */
@MappedSuperclass
public class AuditedModel {// extends Model {

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date updatedAt;

    @Column(name = "created_by")
    public int createdBy;

    @Column(name = "updated_by")
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

    @ManyToAny(metaColumn = @Column(name = "entity_type"))
    @AnyMetaDef(
            idType = "long", metaType = "string",
            metaValues = {
                    @MetaValue(targetEntity = DataSource.class, value = "DSC"),
                    @MetaValue(targetEntity = Schema.class, value = "SCH"),
                    @MetaValue(targetEntity = Table.class, value = "TAB"),
                    @MetaValue(targetEntity = models.Column.class, value = "COL")
            }
    )
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinTable(
            name = "ds_entity_threads",
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
    public Set<Thread> threads;

}
