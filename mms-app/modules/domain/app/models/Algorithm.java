package models.domain;

import javax.persistence.*;
import javax.persistence.Column;

/**
 * User: markmo
 * Date: 18/05/13
 * Time: 5:16 PM
 */
@Entity
public class Algorithm {

    @Id
    @GeneratedValue
    @Column(name = "algorithm_id")
    private Long id;

    @Column(name = "algorithm_name")
    private String name;

    @Column(length = 8000)
    private String description;

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

        Algorithm algorithm = (Algorithm) o;

        if (id != null ? !id.equals(algorithm.id) : algorithm.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
