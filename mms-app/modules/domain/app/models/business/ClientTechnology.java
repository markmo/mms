package models.domain.business;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 23/04/13
 * Time: 10:54 AM
 */
@Entity
@Table(name = "client_tech")
public class ClientTechnology {

    @Id
    @GeneratedValue
    @Column(name = "client_tech_id")
    private Long id;

    @Column(name = "client_tech_name")
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

        ClientTechnology that = (ClientTechnology) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
