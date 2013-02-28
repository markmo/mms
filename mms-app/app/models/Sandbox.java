package models;

import static controllers.Application.getSingleResult;

import javax.persistence.Column;
import javax.persistence.*;

import org.hibernate.envers.Audited;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

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

    @Column(length = 8000)
    public String description;

    public static Sandbox findByName(String name) {
        return getSingleResult(Sandbox.class,
                JPA.em().createQuery(
                        "select s from Sandbox s where s.name = ?1"
                )
                        .setParameter(1, name)
        );
    }

}
