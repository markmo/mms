package models;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 1/06/13
 * Time: 8:14 PM
 */
@Entity
public class Organization {

    @Id
    @GeneratedValue
    @Column(name = "organization_id")
    public Long id;

    @Column(name = "organization_name")
    public String name;
}
