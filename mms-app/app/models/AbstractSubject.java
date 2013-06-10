package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import be.objectify.deadbolt.core.models.*;
import org.code_factory.jpa.nestedset.NodeInfo;
import org.code_factory.jpa.nestedset.annotations.LeftColumn;
import org.code_factory.jpa.nestedset.annotations.LevelColumn;
import org.code_factory.jpa.nestedset.annotations.RightColumn;
import org.code_factory.jpa.nestedset.annotations.RootColumn;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 2/06/13
 * Time: 4:43 PM
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractSubject implements Subject, NodeInfo {

    @Id
    @GeneratedValue
    @Column(name = "subject_id")
    protected int id;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    public Organization organization;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_group_id")
    public SecurityGroup parentGroup;

    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "subject_roles",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public List<SecurityRole> roles;

    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "subject_permissions",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    public List<UserPermission> permissions;

    public abstract String getName();

//    @Column(updatable=false)
//    @LeftColumn
//    private int lft;
//
//    @RightColumn
//    @Column(updatable=false)
//    private int rgt;
//
//    @LevelColumn
//    @Column(updatable=false)
//    private int level;
//
//    @RootColumn
//    private int rootId;
//
//    @Override
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    @Override
    public String getIdentifier() {
        return Long.toString(id);
    }

    @Override
    public List<? extends Role> getRoles() {
        List<Role> roleList = new ArrayList<>();
        if (roles != null) {
            roleList.addAll(roles);
        }
        if (parentGroup != null) {
            roleList.addAll(parentGroup.getRoles());
        }
        return roleList;
    }

    @Override
    public List<? extends Permission> getPermissions() {
        List<Permission> permissionList = new ArrayList<>();
        if (permissions != null) {
            permissionList.addAll(permissions);
        }
        if (parentGroup != null) {
            permissionList.addAll(parentGroup.getPermissions());
        }
        return permissionList;
    }

//    @Override
//    public int getLeftValue() {
//        return this.lft;
//    }
//
//    @Override
//    public int getRightValue() {
//        return this.rgt;
//    }
//
//    @Override
//    public int getLevel() {
//        return this.level;
//    }
//
//    @Override
//    public void setLeftValue(int value) {
//        this.lft = value;
//    }
//
//    @Override
//    public void setRightValue(int value) {
//        this.rgt = value;
//    }
//
//    @Override
//    public void setLevel(int level) {
//        this.level = level;
//    }
//
//    @Override
//    public int getRootValue() {
//        return this.rootId;
//    }
//
//    @Override
//    public void setRootValue(int value) {
//        this.rootId = value;
//    }

    public static Page<AbstractSubject> page(int pageIndex, int pageSize, String sortBy, String order, String filter) {
        if (pageIndex < 1) pageIndex = 1;
        Long totalRowCount = (Long) JPA.em()
                .createQuery("select count(s) from AbstractSubject s where lower(s.name) like ?1")
                .setParameter(1, "%" + filter.toLowerCase() + "%")
                .getSingleResult();
        @SuppressWarnings("unchecked")
        List<AbstractSubject> list = JPA.em()
                .createQuery("select s from AbstractSubject s where lower(s.name) like ?1" +
                            " order by s.lft")
//                            " order by s." + sortBy + " " + order)
                .setParameter(1, "%" + filter.toLowerCase() + "%")
                .setFirstResult((pageIndex - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        return new Page<>(list, totalRowCount, pageIndex, pageSize);
    }
}
