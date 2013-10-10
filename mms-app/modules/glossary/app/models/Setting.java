package models.glossary;

import javax.persistence.*;

import mms.common.models.AuditedModel;

/**
 * User: markmo
 * Date: 1/05/13
 * Time: 8:46 PM
 */
@Entity
@Table(name = "settings")
public class Setting extends AuditedModel {

    @Id
    @GeneratedValue
    public Long id;

    @Column(length = 8000)
    public String customSchema;
}
