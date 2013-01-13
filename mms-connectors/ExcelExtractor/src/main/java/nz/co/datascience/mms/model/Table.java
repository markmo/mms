package nz.co.datascience.mms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User: markmo
 * Date: 24/09/12
 * Time: 3:47 PM
 *
 * A materialized NamedColumnSet.
 */
public class Table {

    public static enum TempScope {SESSION, APPLICATION}

    private int id;
    private String name;
    private boolean isSystem;
    private boolean isTemporary;
    private TempScope temporaryScope;
    private TableType tableType;
    private TableRole role;

    private List<Column> columns;

    // Statistics
    private int rowCount;

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

    /**
     * Indicates that the Table is a System Table (generally part of or view
     * on the system catalog).
     *
     * @return isSystem
     */
    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    /**
     * Indicates that the table content is temporary. SQL92 standards provide
     * two types of temporary tables (local Temporary and Global Temporary).
     *
     * @return isTemporary
     */
    public boolean isTemporary() {
        return isTemporary;
    }

    public void setTemporary(boolean temporary) {
        isTemporary = temporary;
    }

    /**
     * This attribute is meaningful only when the isTemporary flag is True.
     * The scope indicates when the data of this table are available.
     * “SESSION,” “APPLICATION” are examples of possible values. Look at
     * the Scope attribute for Global Temporary tables in the SQL standards
     * for more details.
     *
     * @return temporaryScope
     */
    public TempScope getTemporaryScope() {
        return temporaryScope;
    }

    public void setTemporaryScope(TempScope temporaryScope) {
        this.temporaryScope = temporaryScope;
    }

    public TableType getTableType() {
        return tableType;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    public TableRole getRole() {
        return role;
    }

    public void setRole(TableRole role) {
        this.role = role;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public void addColumn(Column column) {
        if (columns == null) {
            columns = new ArrayList<Column>();
        }
        columns.add(column);
    }

    public Column getColumn(String name) {
        if (name == null || columns == null) {
            return null;
        }
        for (Column column : columns) {
            if (column.getName().equals(name)) {
                return column;
            }
        }
        return null;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table = (Table) o;

        if (id != table.id) return false;
        if (name != null ? !name.equals(table.name) : table.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
