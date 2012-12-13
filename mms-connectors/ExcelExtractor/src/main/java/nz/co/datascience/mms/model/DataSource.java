package nz.co.datascience.mms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User: markmo
 * Date: 20/10/12
 * Time: 8:13 PM
 */
public class DataSource {

    private int id;
    private String name;
    private List<Schema> schemas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        DataSource that = (DataSource) o;

        return (id == that.id);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
