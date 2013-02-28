package models;

import java.io.File;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Table;
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
@Table(name = "ds_file")
public class FileInput {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    public long id;

    @JsonIgnore
    public String entityType;

    @JsonIgnore
    public long entityId;

    @Column(name = "file_name")
    @Constraints.Required
    public String name;

    public int size;

    public String url;

    @JsonProperty("thumbnail_url")
    public String thumbnailUrl;

    @JsonProperty("delete_type")
    public String deleteType = "DELETE";

    @Transient
    public File file;

    @SuppressWarnings("unchecked")
    public static List<FileInput> findByEntityTypeAndId(String entityType, Long id) {
        return JPA.em().createQuery(
                "select f from FileInput f where f.entityType = ?1 and f.entityId = ?2"
        )
                .setParameter(1, entityType)
                .setParameter(2, id)
                .getResultList();
    }

}
