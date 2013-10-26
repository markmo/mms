package models.domain.business;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 23/04/13
 * Time: 11:14 AM
 */
@Entity
@Table(name = "alias")
public class Alias {

    @Id
    @GeneratedValue
    @Column(name = "alias_id")
    private Long id;

    @Column(name = "alias_name")
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

        Alias that = (Alias) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
