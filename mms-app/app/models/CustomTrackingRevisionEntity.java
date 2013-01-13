package models;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import utils.CustomEntityTrackingRevisionListener;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * User: markmo
 * Date: 25/12/12
 * Time: 3:27 PM
 */
@Entity
@RevisionEntity(CustomEntityTrackingRevisionListener.class)
public class CustomTrackingRevisionEntity extends DefaultRevisionEntity {

    @OneToMany(mappedBy = "revision", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<ModifiedEntityTypeEntity> modifiedEntityTypes = new HashSet<ModifiedEntityTypeEntity>();

    public void addModifiedEntityType(String entityName, String entityId, String revisionType) {
        modifiedEntityTypes.add(new ModifiedEntityTypeEntity(this, entityName, entityId, revisionType));
    }

    public Set<ModifiedEntityTypeEntity> getModifiedEntityTypes() {
        return modifiedEntityTypes;
    }

    public int getNumberEntitiesChanged() {
        return modifiedEntityTypes.size();
    }
}
