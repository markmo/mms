package models.domain;

import javax.persistence.*;
import javax.persistence.Column;

/**
 * User: markmo
 * Date: 18/05/13
 * Time: 5:27 PM
 */
@Entity
public class AnalysisModelVersionStatus {

    @Id
    @GeneratedValue
    @Column(name = "version_status_id")
    private Long id;

    @Column(name = "version_status_name")
    private String name;

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

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnalysisModelVersionStatus that = (AnalysisModelVersionStatus) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
