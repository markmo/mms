package models;

import static controllers.Application.copyProperties;

import java.util.*;
import javax.persistence.*;

import org.code_factory.jpa.nestedset.JpaNestedSetManager;
import org.code_factory.jpa.nestedset.NestedSetManager;
import org.code_factory.jpa.nestedset.Node;

import org.code_factory.jpa.nestedset.annotations.*;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 1/06/13
 * Time: 8:19 PM
 */
@Entity
public class SecurityGroup extends AbstractSubject {

    private String name;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "parentGroup")
    public List<AbstractSubject> children;

    @Column(updatable=false)
    @LeftColumn
    private int lft;

    @RightColumn
    @Column(updatable=false)
    private int rgt;

    @LevelColumn
    @Column(updatable=false)
    private int level;

    @RootColumn
    private int rootId;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getLeftValue() {
        return this.lft;
    }

    @Override
    public int getRightValue() {
        return this.rgt;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLeftValue(int value) {
        this.lft = value;
    }

    @Override
    public void setRightValue(int value) {
        this.rgt = value;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getRootValue() {
        return this.rootId;
    }

    @Override
    public void setRootValue(int value) {
        this.rootId = value;
    }

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
            group.parentGroup = parentGroup;
            if (parentGroup == null) {
                nsm.createRoot(group);
            } else {
                Node<SecurityGroup> node = nsm.getNode(parentGroup);
                node.addChild(group);
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
