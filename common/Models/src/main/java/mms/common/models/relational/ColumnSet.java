package mms.common.models.relational;

import javax.persistence.Entity;

import org.hibernate.envers.Audited;

import mms.common.models.Dataset;

/**
 * User: markmo
 * Date: 28/02/13
 * Time: 2:46 PM
 *
 * A set of columns, representing either the result of a query, a view, or a physical table.
 */
@Entity
@Audited
public abstract class ColumnSet extends Dataset {}
