/*
 * Copyright 2012 Steve Chaloner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package models.account;

import static controllers.account.Application.getSingleResult;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.*;

import be.objectify.deadbolt.core.models.Role;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * @author Steve Chaloner (steve@objectify.be)
 * @author Mark Moloney modified for JPA
 */
@Entity
public class SecurityRole implements Role {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "role_id")
    public Long id;

    @Constraints.Required
    public String roleName;

    public String getName() {
        return roleName;
    }

    public static SecurityRole findByRoleName(String roleName) {
        return getSingleResult(SecurityRole.class,
                JPA.em().createQuery(
                        "select r from SecurityRole r where r.roleName = ?1"
                )
                        .setParameter(1, roleName)
        );
    }

    public static int findRowCount() {
        return ((Number)JPA.em().createQuery(
                "select count(r.id) from SecurityRole r"
        )
                .getFirstResult()).intValue();
    }

    public static Map<String, String> options() {
        @SuppressWarnings("unchecked")
        List<SecurityRole> roles = JPA.em().createQuery(
                "from SecurityRole order by roleName"
        )
                .getResultList();
        LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
        for (SecurityRole role : roles) {
            options.put(String.valueOf(role.id), role.getName());
        }
        return options;
    }
}
