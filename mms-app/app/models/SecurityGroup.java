package models;

import java.util.List;
import javax.persistence.*;

/**
 * User: markmo
 * Date: 1/06/13
 * Time: 8:19 PM
 */
@Entity
public class SecurityGroup extends AbstractSubject {

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "parentGroup")
    public List<AbstractSubject> children;
}
