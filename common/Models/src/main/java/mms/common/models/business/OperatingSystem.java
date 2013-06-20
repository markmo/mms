package mms.common.models.business;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 23/04/13
 * Time: 11:04 AM
 */
@Entity
public class OperatingSystem {

    @Id
    @GeneratedValue
    @Column(name = "os_id")
    private Long id;

    @Column(name = "os_name")
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

        OperatingSystem that = (OperatingSystem) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
