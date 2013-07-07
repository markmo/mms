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

    public static SecurityGroup update(SecurityGroupDTO groupDTO) {
        NestedSetManager nsm = new JpaNestedSetManager(JPA.em());
        SecurityGroupDTO parent = groupDTO.parentGroup;
        SecurityGroup parentGroup = null;
        if (parent != null && parent.id > 0) {
            parentGroup = JPA.em().find(SecurityGroup.class, parent.id);
        }
        final String[] includedProperties = new String[]{"name", "organization", "roleIds"};
        if (groupDTO.id == 0) {
            SecurityGroup group = new SecurityGroup();
            group.parentGroup = parentGroup;
            copyProperties(groupDTO, group, Arrays.asList(includedProperties));
            if (parentGroup == null) {
                nsm.createRoot(group);
            } else {
                Node<SecurityGroup> node = nsm.getNode(parentGroup);
                node.addChild(group);
            }
            JPA.em().persist(group);
            return group;
        } else {
            SecurityGroup group = JPA.em().find(SecurityGroup.class, groupDTO.id);
            copyProperties(groupDTO, group, Arrays.asList(includedProperties));
            if (groupDTO.roleIds == null || groupDTO.roleIds.isEmpty()) {
                if (group.roles != null) {
                    group.roles.clear();
                }
            }
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

    public SecurityGroupDTO getDTO() {
        SecurityGroupDTO dto = new SecurityGroupDTO();
        dto.id = id;
        dto.name = name;
        dto.organization = organization;
        if (parentGroup != null) {
            dto.parentGroup = parentGroup.getDTO();
        }
        dto.roleIds = getRoleIds();

        return dto;
    }

    public static class SecurityGroupDTO {

        public int id;
        public String name;
        public Organization organization;
        public SecurityGroupDTO parentGroup;
        public List<Long> roleIds;
    }
}
