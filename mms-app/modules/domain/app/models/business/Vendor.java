package models.domain.business;

import javax.persistence.*;

import models.domain.AuditedModel;

/**
 * User: markmo
 * Date: 22/04/13
 * Time: 3:33 PM
 */
@Entity
public class Vendor extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "vendor_id")
    private Long id;

    @Column(name = "vendor_name")
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

        Vendor vendor = (Vendor) o;

        if (id != null ? !id.equals(vendor.id) : vendor.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
