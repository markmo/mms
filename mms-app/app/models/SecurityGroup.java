package models;

import static controllers.Application.copyProperties;

import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import org.code_factory.jpa.nestedset.*;
import org.code_factory.jpa.nestedset.annotations.NodeClass;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 1/06/13
 * Time: 8:19 PM
 */
@Entity
@NodeClass(type = SecuritySubject.class)
public class SecurityGroup extends SecuritySubject {

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "parentGroup")
    @JsonIgnore
    public List<SecuritySubject> children;

    // The following has to be in the subclass since the NestedSetManager
    // only looks for annotations in the declared fields of the immediate
    // class.

    public static SecurityGroup update(SecurityGroup partialGroup) {
        NestedSetManager nsm = new JpaNestedSetManager(JPA.em());
        SecurityGroup parent = partialGroup.parentGroup;
        SecurityGroup parentGroup = null;
        if (parent != null && parent.getId() > 0) {
            parentGroup = JPA.em().find(SecurityGroup.class, parent.getId());
        }
        if (partialGroup.getId() == 0) {
            partialGroup.parentGroup = parentGroup;
            if (parentGroup == null) {
                partialGroup.setRootValue(1);
                nsm.createRoot(partialGroup);
            } else {
                Node<SecurityGroup> node = nsm.getNode(parentGroup);
                node.addChild(partialGroup);
            }
            JPA.em().persist(partialGroup);
            return partialGroup;
        } else {
            SecurityGroup group = JPA.em().find(SecurityGroup.class, partialGroup.getId());
            final String[] includedProperties = new String[]{"name"};
            copyProperties(partialGroup, group, Arrays.asList(includedProperties));
            if (!isSameGroup(group.parentGroup, parentGroup)) {
                group.parentGroup = parentGroup;
                Node<SecurityGroup> node = nsm.getNode(group);
                if (parentGroup == null) {
                    node.moveToRoot();
                } else {
                    Node<SecurityGroup> parentNode = nsm.getNode(parentGroup);
                    node.moveAsLastChildOf(parentNode);
                }
            }
            JPA.em().persist(group);
            return group;
        }
    }

    public static Map<String, String> options() {
        @SuppressWarnings("unchecked")
        List<SecurityGroup> groups = JPA.em().createQuery(
                "from SecurityGroup order by name"
        )
                .getResultList();
        LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
        options.put("0", "-- Select a Security Group --");
        for (SecurityGroup group : groups) {
            options.put(String.valueOf(group.getId()), group.getName());
        }
        return options;
    }
}
