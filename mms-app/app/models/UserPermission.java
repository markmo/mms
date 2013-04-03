package models;

import static controllers.Application.getSingleResult;

import javax.persistence.*;

import be.objectify.deadbolt.core.models.Permission;
import play.db.jpa.JPA;

/**
 * Initial version based on work by Steve Chaloner (steve@objectify.be) for
 * Deadbolt2
 */
@Entity
public class UserPermission implements Permission {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "permission_id")
    public Long id;

    public String value;

    public String getValue() {
        return value;
    }

    public static UserPermission findByValue(String value) {
        return getSingleResult(UserPermission.class,
                JPA.em().createQuery(
                        "select p from UserPermission p where p.value = ?1"
                )
                        .setParameter(1, value)
        );
    }
}
