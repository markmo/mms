package models;

import javax.persistence.*;
import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.envers.Audited;
import play.data.validation.Constraints;

/**
 * User: markmo
 * Date: 25/12/12
 * Time: 6:57 PM
 */
@Entity
@javax.persistence.Table(name = "ds_sandbox")
@Audited
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Sandbox extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "sandbox_id")
    public long id;

    @Column(name = "sandbox_name")
    @Constraints.Required
    public String name;

}
