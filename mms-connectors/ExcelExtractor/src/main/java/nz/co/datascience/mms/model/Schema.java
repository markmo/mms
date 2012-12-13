package nz.co.datascience.mms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 1:09 PM
 *
 * A schema is a named collection of tables.
 */
public class Schema {

    private int id;
    private String name;

    private List<Table> tables;

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

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public void addTable(Table table) {
        if (tables == null) {
            tables = new ArrayList<Table>();
        }
        tables.add(table);
     }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schema schema = (Schema) o;

        if (id != schema.id) return false;
        if (name != null ? !name.equals(schema.name) : schema.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
