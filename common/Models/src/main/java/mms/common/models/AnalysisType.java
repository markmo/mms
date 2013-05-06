package mms.common.models;

import static utils.JPA_Helper.getSingleResult;

import javax.persistence.*;
import javax.persistence.Column;

import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 18/04/13
 * Time: 2:21 PM
 */
@Entity
public class AnalysisType {

    @Id
    @GeneratedValue
    @Column(name = "analysis_type_id")
    private Long id;

    @Column(name = "analysis_type_name")
    private String name;

    public static AnalysisType findByName(String name) {
        return getSingleResult(AnalysisType.class,
                JPA.em().createQuery(
                        "select a from AnalysisType a where a.name = ?1"
                )
                        .setParameter(1, name)
        );
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

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnalysisType that = (AnalysisType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
