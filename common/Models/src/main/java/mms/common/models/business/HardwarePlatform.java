package mms.common.models.business;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 23/04/13
 * Time: 10:51 AM
 */
@Entity
public class HardwarePlatform {

    @Id
    @GeneratedValue
    @Column(name = "hardware_platform_id")
    private Long id;

    @Column(name = "hardware_platform_name")
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

        HardwarePlatform that = (HardwarePlatform) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
