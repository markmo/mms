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
package models;

import javax.persistence.*;

import be.objectify.deadbolt.models.Role;
import play.db.ebean.Model;
import play.db.jpa.JPA;

/**
 * @author Steve Chaloner (steve@objectify.be)
 * @author Mark Moloney modified for JPA
 */
@Entity
public class SecurityRole implements Role {// extends Model implements Role {

    private static final long serialVersionUID = 1L;

    @Id
//    @SequenceGenerator(name="id_seq", sequenceName="public.security_role_id_seq_1")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    public String roleName;

//    public static final Finder<Long, SecurityRole> find = new Finder<Long, SecurityRole>(
//            Long.class, SecurityRole.class);

    public String getRoleName() {
        return roleName;
    }

    public static SecurityRole findByRoleName(String roleName) {
//        return find.where().eq("roleName", roleName).findUnique();
        return JPA.em()
                .createQuery("from SecurityRole r where r.roleName = ?1",
                        SecurityRole.class)
                .setParameter(1, roleName)
                .getSingleResult();
    }

    public static int findRowCount() {
        return JPA.em()
                .createQuery("select count(*) from SecurityRole")
                .getFirstResult();
    }
}
