package models;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 1/05/13
 * Time: 8:46 PM
 */
@Entity
@Table(name = "settings")
public class Setting {

    @Id
    @GeneratedValue
    public Long id;

    @Column(length = 8000)
    public String customSchema;
}
