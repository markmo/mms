package models.audit;

import javax.persistence.*;
import javax.persistence.Table;

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
