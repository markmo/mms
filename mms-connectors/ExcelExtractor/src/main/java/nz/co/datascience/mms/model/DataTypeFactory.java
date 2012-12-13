package nz.co.datascience.mms.model;

import static org.apache.poi.ss.usermodel.Cell.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User: markmo
 * Date: 24/09/12
 * Time: 4:20 PM
 */
public class DataTypeFactory {

    static {
        initialize();
    }

    private static List<DataType> dataTypes;

    private static void initialize() {
        dataTypes = new ArrayList<DataType>();
        Object[] types = new Object[]{
                CELL_TYPE_NUMERIC, "Numeric"
                ,CELL_TYPE_STRING, "String"
                ,CELL_TYPE_FORMULA, "Formula"
                ,CELL_TYPE_BLANK, "Blank"
                ,CELL_TYPE_BOOLEAN, "Boolean"
                ,CELL_TYPE_ERROR, "Error"
                ,6, "Date"
        };
        for (int i = 0; i < types.length; i += 2) {
            DataType dataType = new DataType();
            dataType.setId((Integer)types[i]);
            dataType.setName((String)types[i + 1]);
            dataTypes.add(dataType);
        }
    }

    public static DataType getDataType(String typeName) {
        if (typeName == null) {
            return null;
        }
        for (DataType dataType : dataTypes) {
            if (dataType.getName().equals(typeName)) {
                return dataType;
            }
        }
        DataType newType = new DataType();
        newType.setName(typeName);
        dataTypes.add(newType);
        return newType;
    }

    public static DataType getDataType(int type) {
        for (DataType dataType : dataTypes) {
            if (dataType.getId() == type) {
                return dataType;
            }
        }
        return null;
    }
}
