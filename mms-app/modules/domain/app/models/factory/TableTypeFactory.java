package models.domain.factory;

import java.util.ArrayList;
import java.util.List;

import models.domain.relational.TableType;

/**
 * User: markmo
 * Date: 27/09/12
 * Time: 7:32 PM
 */
public class TableTypeFactory {

    private static List<TableType> tableTypes;

    private static void initialize() {
        tableTypes = new ArrayList<TableType>();
        TableType tableType = new TableType();
        tableType.setName("Table");
        tableTypes.add(tableType);
        TableType viewType = new TableType();
        viewType.setName("View");
        tableTypes.add(viewType);
    }

    static {
        initialize();
    }

    public static TableType getTableType(String typeName) {
        if (typeName != null) {
            for (TableType tableType : tableTypes) {
                if (tableType.getName().toLowerCase().equals(typeName.toLowerCase())) {
                    return tableType;
                }
            }
        }
        return null;
    }
}
