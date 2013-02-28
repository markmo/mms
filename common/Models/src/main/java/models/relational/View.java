package models.relational;

/**
 * User: markmo
 * Date: 28/02/13
 * Time: 2:50 PM
 *
 * A view is a non-materialized set of rows, defined by the associated query.
 */
public class View extends NamedColumnSet {

    private String query;
}
