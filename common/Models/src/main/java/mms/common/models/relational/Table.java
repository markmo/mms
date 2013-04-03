package mms.common.models.relational;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.*;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.envers.Audited;
import play.db.jpa.JPA;

import mms.common.models.AbstractColumn;
import mms.common.models.Namespace;

/**
 * User: markmo
 * Date: 24/09/12
 * Time: 3:47 PM
 *
 * A materialized NamedColumnSet.
 */
@Entity
@DiscriminatorValue("TAB")
@Audited
public class Table extends NamedColumnSet {

    public static enum TempScope {SESSION, APPLICATION}

    private boolean isSystem;
    private boolean isTemporary;
    private TempScope temporaryScope;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "table_type_id")
    private TableType tableType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "table_role_id")
    private TableRole role;

    public static Table parseTable(ObjectMapper mapper, JsonNode json, Namespace namespace) throws IOException {
        String name = json.path("name").getTextValue();
        Table existingTable = (Table)findByName(name, namespace);
        Table table = (existingTable == null ? new Table() : existingTable);
        table.setName(name);
        TableType tableType = mapper.readValue(json.path("tableType"), TableType.class);
        TableType existingTableType = TableType.findByName(tableType.getName());
        table.tableType = (existingTableType == null ? tableType : existingTableType);
        TableRole tableRole = mapper.readValue(json.path("role"), TableRole.class);
        TableRole existingTableRole = TableRole.findByName(tableRole.getName());
        table.role = (existingTableRole == null ? tableRole : existingTableRole);
        table.setRowCount(json.path("rowCount").getIntValue());
        JPA.em().persist(table);
        List<AbstractColumn> columns = null;
        JsonNode columnsJson = json.path("columns");
        if (columnsJson.size() > 0) {
            columns = new ArrayList<AbstractColumn>();
            Iterator<JsonNode> it = columnsJson.getElements();
            while (it.hasNext()) {
                TableColumn column = TableColumn.parseColumn(mapper, it.next(), table);
                if (column != null) {
                    column.setTable(table);
                    columns.add(column);
                }
            }
        }
        table.setColumns(columns);
        JPA.em().persist(table);
        return table;
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
}
