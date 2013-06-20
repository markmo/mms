package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.*;

import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 1/06/13
 * Time: 8:14 PM
 */
@Entity
public class Organization {

    @Id
    @GeneratedValue
    @Column(name = "organization_id")
    public Long id;

    @Column(name = "organization_name")
    public String name;

    public static Page<Organization> page(int pageIndex, int pageSize, String sortBy, String order, String filter) {
        if (pageIndex < 1) pageIndex = 1;
        Long totalRowCount = (Long)JPA.em()
                .createQuery("select count(o) from Organization o where lower(o.name) like ?1")
                .setParameter(1, "%" + filter.toLowerCase() + "%")
                .getSingleResult();
        @SuppressWarnings("unchecked")
        List<Organization> list = JPA.em()
                .createQuery("select o from Organization o where lower(o.name) like ?1" +
                            " order by o." + sortBy + " " + order)
                .setParameter(1, "%" + filter.toLowerCase() + "%")
                .setFirstResult((pageIndex - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        return new Page<>(list, totalRowCount, pageIndex, pageSize);
    }

    public static Map<String, String> options() {
        @SuppressWarnings("unchecked")
        List<Organization> organizations = JPA.em().createQuery(
                "from Organization order by name"
        )
                .getResultList();
        LinkedHashMap<String, String> options = new LinkedHashMap<>();
        options.put("0", "-- Select an Organization");
        for (Organization organization : organizations) {
            options.put(String.valueOf(organization.id), organization.name);
        }
        return options;
    }
}
