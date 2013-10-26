package models.domain.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;

import models.domain.DataType;

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
                Cell.CELL_TYPE_NUMERIC, "Numeric"
                ,Cell.CELL_TYPE_STRING, "String"
                ,Cell.CELL_TYPE_FORMULA, "Formula"
                ,Cell.CELL_TYPE_BLANK, "Blank"
                ,Cell.CELL_TYPE_BOOLEAN, "Boolean"
                ,Cell.CELL_TYPE_ERROR, "Error"
                ,6, "Date"
        };
        for (int i = 0; i < types.length; i += 2) {
            DataType dataType = new DataType();
            dataType.setId(new Long((Integer)types[i]));
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
