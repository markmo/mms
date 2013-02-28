package models.file;

import models.file.FileUpload;
import play.db.jpa.JPA;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

/**
 * User: markmo
 * Date: 28/02/13
 * Time: 8:29 AM
 */
@Entity
@Table(name = "attachment")
public class Attachment {

    private Long entityId;
    private String entityType;

    @SuppressWarnings("unchecked")
    public static List<FileUpload> findByEntityTypeAndId(String entityType, Long id) {
        return JPA.em().createQuery(
                "select a.fileUpload from Attachment a where a.entityType = ?1 and a.entityId = ?2"
        )
                .setParameter(1, entityType)
                .setParameter(2, id)
                .getResultList();
    }

    @ManyToOne
    @JoinColumn(name = "upload_id")
    private FileUpload fileUpload;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public FileUpload getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }
}
