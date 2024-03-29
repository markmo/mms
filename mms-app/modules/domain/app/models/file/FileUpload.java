package models.domain.file;

import java.io.File;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 13/02/13
 * Time: 11:08 PM
 */
@Entity
public class FileUpload {

    enum HashType {MD5}

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @Column(name = "file_name")
    @Constraints.Required
    private String name;

    private int size;

    private HashType hashType;

    private String hash;

    private String url;

    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    @JsonProperty("delete_type")
    private String deleteType = "DELETE";

    private Long entityId;
    private String entityType;

    @Transient
    private File file;

    @Lob
    private String analysisResults;


    @SuppressWarnings("unchecked")
    public static List<FileUpload> findByEntityTypeAndId(String entityType, Long id) {
        return JPA.em().createQuery(
                "select f from FileUpload f where f.entityType = ?1 and f.entityId = ?2"
        )
                .setParameter(1, entityType)
                .setParameter(2, id)
                .getResultList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public HashType getHashType() {
        return hashType;
    }

    public void setHashType(HashType hashType) {
        this.hashType = hashType;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDeleteType() {
        return deleteType;
    }

    public void setDeleteType(String deleteType) {
        this.deleteType = deleteType;
    }

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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getAnalysisResults() {
        return analysisResults;
    }

    public void setAnalysisResults(String analysisResults) {
        this.analysisResults = analysisResults;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileUpload that = (FileUpload) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
