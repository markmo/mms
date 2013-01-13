package models;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 24/11/12
 * Time: 6:54 PM
 */
@Entity
@Table(name = "ds_filter_type")
@Audited
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class FilterType {

    @Id
    @GeneratedValue//(strategy = GenerationType.SEQUENCE, generator = "public.ds_filter_type_filter_type_id_seq")
    @Column(name = "filter_type_id")
    public int id;

    @Column(name = "filter_type_name")
    @Constraints.Required
    public String name;

    public static FilterType findByName(String name) {
        FilterType filterType = null;
        try {
            filterType = JPA.em().createQuery("select t from FilterType t where t.name = ?1",
                    FilterType.class)
                    .setParameter(1, name)
                    .getSingleResult();
        } catch (NoResultException e) {
            // ignore
        }
        return filterType;
    }
}