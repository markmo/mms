package models.account;

import java.util.*;
import javax.persistence.*;

import be.objectify.deadbolt.core.models.*;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.code_factory.jpa.nestedset.NodeInfo;
import org.code_factory.jpa.nestedset.annotations.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import play.db.jpa.JPA;

import models.common.Page;

/**
 * User: markmo
 * Date: 2/06/13
 * Time: 4:43 PM
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SecuritySubject implements Subject, NodeInfo {

    @Id
    @GeneratedValue
    @Column(name = "subject_id")
    protected int id;

    protected String name;

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

    @LeftColumn
    private int lft;

    @RightColumn
    private int rgt;

    @LevelColumn
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

    public boolean isInRole(String roleName) {
        return Lists.transform(getRoles(), new Function<Role, String>() {
            public String apply(Role role) {
                return role.getName();
            }
        }).contains(roleName);
    }

    // scala templates don't like arrays
    // Field.indexes not working with an Array field - must be Iterable perhaps
    @Transient
    public List<Long> getRoleIds() {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }
        int n = roles.size();
        List<Long> roleIds = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            roleIds.add(roles.get(i).id);
        }
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        if (roleIds != null && roleIds.size() > 0) {
            List<SecurityRole> newRoles = new ArrayList<>();
            for (int i = 0, n = roleIds.size(); i < n; i++) {
                SecurityRole role = JPA.em().find(SecurityRole.class, roleIds.get(i));
                newRoles.add(role);
            }
            roles = newRoles;
        }
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

    public static Page<SecuritySubject> page(int pageIndex, int pageSize,
                                             String sortBy, String order,
                                             String filterColumn, String filterValue,
                                             Set<String[]> closedNodes,
                                             Long organizationId) {
        if (pageIndex < 1) pageIndex = 1;
        if (filterColumn == null || filterColumn.trim().isEmpty()) {
            filterColumn = "name";
        }
        Long totalRowCount = (Long) JPA.em()
                .createQuery("select count(s) from SecuritySubject s " +
                             "where lower(s." + filterColumn + ") like ?1")
                .setParameter(1, "%" + filterValue.toLowerCase() + "%")
                .getSingleResult();
        String q = "select s from SecuritySubject s " +
                "where lower(s." + filterColumn + ") like ?1";
        int n = closedNodes.size(), j = 2;
        for (int i = 0; i < n; i++) {
            q += String.format(" and (s.lft <= ?%s or s.lft >= ?%s)", j++, j++);
        }
        if (organizationId != null) {
            q += " and s.organization.id = ?" + j;
        }
        q += " order by s.rootId, s.lft, s.name";
//            " order by s." + sortBy + " " + order)
        Query query = JPA.em().createQuery(q);
        query.setParameter(1, "%" + filterValue.toLowerCase() + "%");
        j = 2;
        for (String[] bounds : closedNodes) {
            query.setParameter(j++, Integer.parseInt(bounds[1]));
            query.setParameter(j++, Integer.parseInt(bounds[2]));
        }
        if (organizationId != null) {
            query.setParameter(j, organizationId);
        }
        @SuppressWarnings("unchecked")
        List<SecuritySubject> list = query
                .setFirstResult((pageIndex - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        return new Page<>(list, totalRowCount, pageIndex, pageSize);
    }

    protected static boolean isSameGroup(SecurityGroup group1, SecurityGroup group2) {
        if (group1 == null) return (group2 == null);
        if (group2 == null) return false;
        return group1.equals(group2);
    }
}
