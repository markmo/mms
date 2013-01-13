package models;

import javax.persistence.*;
import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.envers.Audited;
import play.data.validation.Constraints;

/**
 * User: markmo
 * Date: 25/12/12
 * Time: 7:07 PM
 */
@Entity
@javax.persistence.Table(name = "ds_company")
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
