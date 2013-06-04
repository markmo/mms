package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import be.objectify.deadbolt.core.models.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * User: markmo
 * Date: 2/06/13
 * Time: 4:43 PM
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractSubject implements Subject {

    @Id
    @GeneratedValue
    @Column(name = "subject_id")
    public Long id;

    @Column(name = "subject_name")
    public String name;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    public Organization organization;

    @ManyToOne
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
}
