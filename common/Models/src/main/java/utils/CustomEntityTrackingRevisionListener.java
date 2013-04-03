package utils;

import java.io.Serializable;

import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionType;

import mms.common.models.audit.CustomTrackingRevisionEntity;

/**
 * User: markmo
 * Date: 25/12/12
 * Time: 3:24 PM
 */
public class CustomEntityTrackingRevisionListener implements EntityTrackingRevisionListener {

    public void entityChanged(Class entityClass, String entityName,
                              Serializable entityId, RevisionType revisionType,
                              Object revisionEntity)
    {
        ((CustomTrackingRevisionEntity)revisionEntity)
                .addModifiedEntityType(entityName, String.valueOf(entityId), revisionType.name());
    }

    public void newRevision(Object revisionEntity) {}
}
