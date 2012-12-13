package nz.co.datascience.mms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User: markmo
 * Date: 2/11/12
 * Time: 12:46 PM
 * <p/>
 * A Catalog is the unit of logon and identification. It also identifies
 * the scope of SQL statements: the tables contained in a catalog can be
 * used in a single SQL statement.
 */
public class Catalog {

    private long id;
    private String name;
    private String defaultCharacterSetName;
    private String defaultCollationName;

    private List<Schema> schemas;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * The name of the default character set used for the values in the column.
     * This field applies only to columns whose datatype is a character string.
     *
     * @return defaultCharacterSetName
     */
    public String getDefaultCharacterSetName() {
        return defaultCharacterSetName;
    }

    public void setDefaultCharacterSetName(String defaultCharacterSetName) {
        this.defaultCharacterSetName = defaultCharacterSetName;
    }

    /**
     * The name of the default collation sequence used to sort the data values
     * in the column. This applies only to columns whose datatype is a form of
     * character string.
     *
     * @return defaultCollationName
     */
    public String getDefaultCollationName() {
        return defaultCollationName;
    }

    public void setDefaultCollationName(String defaultCollationName) {
        this.defaultCollationName = defaultCollationName;
    }

    public List<Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<Schema> schemas) {
        this.schemas = schemas;
    }

    public void addSchema(Schema schema) {
        if (schemas == null) {
            schemas = new ArrayList<Schema>();
        }
        schemas.add(schema);
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Catalog catalog = (Catalog) o;

        if (id != catalog.id) return false;
        if (name != null ? !name.equals(catalog.name) : catalog.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
