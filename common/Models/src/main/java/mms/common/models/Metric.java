package mms.common.models;

import static utils.JPA_Helper.getSingleResult;

import javax.persistence.*;
import javax.persistence.Column;

import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 18/04/13
 * Time: 2:41 PM
 */
@Entity
public class Metric {

    @Id
    @GeneratedValue
    @Column(name = "metric_id")
    private Long id;

    @Column(name = "metric_name")
    private String name;

    @Column(length = 4000)
    private String description;

    public static Metric findByName(String name) {
        return getSingleResult(Metric.class,
                JPA.em().createQuery(
                        "select m from Metric m where m.name = ?1"
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Metric metric = (Metric) o;

        if (id != null ? !id.equals(metric.id) : metric.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
