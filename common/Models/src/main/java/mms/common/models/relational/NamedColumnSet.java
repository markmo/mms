package mms.common.models.relational;

import javax.persistence.Entity;

import org.hibernate.envers.Audited;

/**
 * User: markmo
 * Date: 28/02/13
 * Time: 2:47 PM
 *
 * A catalogued set of columns, which may be Table or View.
 */
@Entity
@Audited
public abstract class NamedColumnSet extends ColumnSet {}
