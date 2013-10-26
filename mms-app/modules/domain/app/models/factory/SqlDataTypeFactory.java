package models.domain.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.domain.relational.SqlDataType;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 2:10 PM
 */
public class SqlDataTypeFactory {

    private static List<SqlDataType> sqlDataTypes;

    private static void initialize() {
        sqlDataTypes = new ArrayList<SqlDataType>();
        Map<Integer, String> typeMap = SqlTypes.getTypeMap();
        for (Map.Entry<Integer, String> entry : typeMap.entrySet()) {
            SqlDataType sqlDataType = new SqlDataType();
            //sqlDataType.setId(entry.getKey());
            sqlDataType.setName(entry.getValue());
            sqlDataTypes.add(sqlDataType);
        }
    }

    static {
        initialize();
    }

    public static SqlDataType getSqlDataType(String typeName) {
        if (typeName == null) {
            return null;
        }
        for (SqlDataType sqlDataType : sqlDataTypes) {
            if (sqlDataType.getName().equals(typeName)) {
                return sqlDataType;
            }
        }
        return null;
    }
}
