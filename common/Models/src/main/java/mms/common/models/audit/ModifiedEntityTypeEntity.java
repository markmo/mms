package mms.common.models.audit;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 25/12/12
 * Time: 3:32 PM
 */
@Entity
@Table(name = "entity_type_mod")
public class ModifiedEntityTypeEntity {

    @Id
    @GeneratedValue
    @Column(name = "mod_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "revision_id")
    public CustomTrackingRevisionEntity revision;

    public String entityType;

    public String entityId;

    public String revisionType;

    public ModifiedEntityTypeEntity() {}

    public ModifiedEntityTypeEntity(CustomTrackingRevisionEntity revision, String entityType,
                                    String entityId, String revisionType)
    {
        this.revision = revision;
        this.entityType = entityType;
        this.entityId = entityId;
        this.revisionType = revisionType;
    }
}
