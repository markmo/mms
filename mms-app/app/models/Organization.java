package models;

import static controllers.Application.getSingleResult;

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

    @Column(name = "organization_code")
    public String code;

    @Column(name = "organization_name")
    public String name;

    public static Organization findByCode(String code) {
        return getSingleResult(Organization.class,
                JPA.em().createQuery(
                        "select o from Organization o where o.code = ?1"
                )
                        .setParameter(1, code)
        );
    }

    public static Organization findByCodeOrName(String code, String name) {
        return getSingleResult(Organization.class,
                JPA.em().createQuery(
                        "select o from Organization o where o.code = ?1 or o.name = ?2"
                )
                        .setParameter(1, code)
                        .setParameter(2, name)
        );
    }

    public static Page<Organization> page(int pageIndex, int pageSize,
                                          String sortBy, String order,
                                          String filterColumn, String filterValue) {
        if (pageIndex < 1) pageIndex = 1;
        if (filterColumn == null || filterColumn.trim().isEmpty()) {
            filterColumn = "name";
        }
        Long totalRowCount = (Long)JPA.em()
                .createQuery("select count(o) from Organization o " +
                             "where lower(o." + filterColumn + ") like ?1")
                .setParameter(1, "%" + filterValue.toLowerCase() + "%")
                .getSingleResult();
        @SuppressWarnings("unchecked")
        List<Organization> list = JPA.em()
                .createQuery("select o from Organization o " +
                             "where lower(o." + filterColumn + ") like ?1 " +
                             "order by o." + sortBy + " " + order)
                .setParameter(1, "%" + filterValue.toLowerCase() + "%")
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
