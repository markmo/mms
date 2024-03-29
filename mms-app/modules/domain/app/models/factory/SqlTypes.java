package models.domain.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * User: markmo
 * Date: 25/09/12
 * Time: 2:34 PM
 */
public class SqlTypes {

    private static Object[] types = {
            2003,   "ARRAY",
            -5,     "BIGINT",
            -2,     "BINARY",
            -7,     "BIT",
            2004,   "BLOB",
            16,     "BOOLEAN",
            1,      "CHAR",
            2005,   "CLOB",
            70,     "DATALINK",
            91,     "DATE",
            3,      "DECIMAL",
            2001,   "DISTINCT",
            8,      "DOUBLE",
            6,      "FLOAT",
            4,      "INTEGER",
            2000,   "JAVA_OBJECT",
            -16,    "LONGNVARCHAR",
            -4,     "LONGVARBINARY",
            -1,     "LONGVARCHAR",
            -15,    "NCHAR",
            2011,   "NCLOB",
            0,      "NULL",
            2,      "NUMERIC",
            -9,     "NVARCHAR",
            1111,   "OTHER",
            7,      "REAL",
            2006,   "REF",
            -8,     "ROWID",
            5,      "SMALLINT",
            2009,   "SQLXML",
            2002,   "STRUCT",
            92,     "TIME",
            93,     "TIMESTAMP",
            -6,     "TINYINT",
            -3,     "VARBINARY",
            12,     "VARCHAR"
    };
    private static Map<Integer, String> typeMap = new HashMap<Integer, String>();

    static {
        for (int i = 0; i < types.length; i += 2) {
            typeMap.put((Integer)types[i], (String)types[i + 1]);
        }
    }

    public static String getTypeName(int type) {
        return typeMap.get(type);
    }

    public static Map<Integer, String> getTypeMap() {
        return typeMap;
    }
}
