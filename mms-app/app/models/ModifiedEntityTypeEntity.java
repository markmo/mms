package models;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 25/12/12
 * Time: 3:32 PM
 */
@Entity
public class ModifiedEntityTypeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    public CustomTrackingRevisionEntity revision;

    public String entityName;

    public String entityId;

    public String revisionType;

    public ModifiedEntityTypeEntity() {}

    public ModifiedEntityTypeEntity(CustomTrackingRevisionEntity revision, String entityName,
                                    String entityId, String revisionType)
    {
        this.revision = revision;
        this.entityName = entityName;
        this.entityId = entityId;
        this.revisionType = revisionType;
    }
}
