package models;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.*;

import org.hibernate.envers.Audited;
import play.data.validation.Constraints;

/**
 * User: markmo
 * Date: 25/12/12
 * Time: 7:07 PM
 */
@Entity
@Table(name = "ds_company")
@Audited
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Company extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "company_id")
    public long id;

    @Column(name = "company_name")
    @Constraints.Required
    public String name;

}
