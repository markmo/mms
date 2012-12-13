package nz.co.datascience.mms.extractors.DatabaseExtractor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import nz.co.datascience.mms.model.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * User: markmo
 * Date: 25/09/12
 * Time: 9:04 AM
 */
public class DatabaseExtractor {

//    private String username = "markmo";
//    private String password = "boxcar99";
//    private String dbms = "postgresql";
//    private String serverName = "127.0.0.1";
//    private String portNumber = "5432";
//    private String dbName = "mms";
    private String username = "sa";
    private String password = "Password1";
    private String dbms = "jtds:sqlserver";
    private String serverName = "192.168.211.155";
    private String portNumber = "1433";
    private String dbName = "DM_ScheduALL";

    private List<Schema> schemas;
    private DataSource dataSource;

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Properties connProps = new Properties();
        connProps.put("user", this.username);
        connProps.put("password", this.password);
        Class.forName("org.postgresql.Driver");
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:" + this.dbms + "://" + this.serverName + ":" + this.portNumber + "/" + this.dbName, connProps);
        System.out.println("Connected to database");
        return conn;
    }

    public Schema lookupSchema(String schemaName) {
        for (Schema schema : schemas) {
            if (schema.getName().equals(schemaName)) {
                return schema;
            }
        }
        return null;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public List<Schema> extract(Connection conn) throws Exception {
        this.schemas = new ArrayList<Schema>();
        DatabaseMetaData metadata = conn.getMetaData();
        System.out.println("Database Product Name: " + metadata.getDatabaseProductName() + ", Version: " + metadata.getDatabaseMajorVersion() + "." + metadata.getDatabaseMinorVersion());
        ResultSet schemas = metadata.getSchemas();
//        System.out.println(schemas.getMetaData().getColumnCount());
//        for (int i = 1; i < schemas.getMetaData().getColumnCount() + 1; i++) {
//            System.out.println(schemas.getMetaData().getColumnName(i));
//        }
//        while (schemas.next()) {
//            String schemaName = schemas.getString("TABLE_SCHEM");
//            Schema schema = new Schema();
//            schema.setName(schemaName);
//            this.schemas.add(schema);
//            System.out.println("\nSchema: " + schemaName);
//            String catalogName = schemas.getString("TABLE_CATALOG");
//            System.out.println("Catalog: " + catalogName);
//        }
        Schema schema = new Schema();
        schema.setName("DDS");
        this.schemas.add(schema);

        DataSource dataSource = new DataSource();
        dataSource.setName("SQL Server 2008 R2");
        dataSource.addSchema(schema);
        this.dataSource = dataSource;

        ResultSet tables = metadata.getTables(null, "DDS", null, new String[]{"TABLE", "VIEW"});
//        ResultSet tables = metadata.getTables("public", null, null, new String[]{"TABLE", "VIEW"});
        while (tables.next()) {
            String schemaName = tables.getString("TABLE_SCHEM");
            String tableName = tables.getString("TABLE_NAME");
            String tableType = tables.getString("TABLE_TYPE");
            System.out.println("\n\tTable: " +schemaName + "." + tableName + " (" + tableType + ")");
            Table table = new Table();
            table.setName(tableName);
            table.setTableType(TableTypeFactory.getTableType(tableType));
            lookupSchema(schemaName).addTable(table);
            table.setRole(TableRoleFactory.getRole("ordinary"));

            ResultSet columns = metadata.getColumns(conn.getCatalog(), null, tableName, null);
//            for (int i = 1; i < columns.getMetaData().getColumnCount() + 1; i++) {
//                System.out.println(columns.getMetaData().getColumnName(i));
//            }
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String dataType = SqlTypes.getTypeName(columns.getInt("DATA_TYPE"));
                String columnType = columns.getString("TYPE_NAME");
                String autoinc = columnType.contains("identity") ? "YES" : "NO";//columns.getString("IS_AUTOINCREMENT");
                int precision = columns.getInt("COLUMN_SIZE");
                int scale = columns.getInt("DECIMAL_DIGITS");
                int nullable = columns.getInt("NULLABLE");
                String defaultValue = columns.getString("COLUMN_DEF");
                int ordinalPosition = columns.getInt("ORDINAL_POSITION");
                DatabaseColumn column = new DatabaseColumn();
                column.setName(columnName);
                column.setDataType(DataTypeFactory.getDataType(columnType));
                column.setSqlDataType(SqlDataTypeFactory.getSqlDataType(dataType));
                column.setColumnIndex(ordinalPosition);
                column.setAutoinc(autoinc.equals("YES"));
                column.setNullable(nullable == 1);
                column.setDefaultValue(defaultValue);
                column.setRole(ColumnRoleFactory.getRole("ordinary"));
                if (columnName.startsWith("DETL_")) {
                    column.addFilterType(FilterTypeFactory.getFilterType("etl"));
                }
                table.addColumn(column);
                System.out.println("\t\tColumn: " + columnName + " (" + dataType + ", " + columnType + ", autoinc=" + autoinc + ")");
                System.out.println("\t\t\tPrecision: " + precision + ", Scale: " + scale + ", Nullable: " + (nullable == 1 ? "YES" : "NO") + ", Default: " + defaultValue + ", Position: " + ordinalPosition);
            }

            // No result
            ResultSet identifiers = metadata.getBestRowIdentifier(conn.getCatalog(), "DDS", tableName, DatabaseMetaData.bestRowTemporary, false);
            while (identifiers.next()) {
                String columnName = identifiers.getString("COLUMN_NAME");
                System.out.println("id: " + columnName);
            }

            ResultSet primaryKeys = metadata.getPrimaryKeys(conn.getCatalog(), "DDS", tableName);
            while (primaryKeys.next()) {
                String columnName = primaryKeys.getString("COLUMN_NAME");
                System.out.println("PK: " + columnName);
                Column column = table.getColumn((columnName));
                if (column != null) {
                    column.setRole(ColumnRoleFactory.getRole("Primary Key"));
                }
            }
        }
        return this.schemas;
    }

    public String serialize(Object value) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(value);
    }

    public static void main(String[] args) throws Exception {
        DatabaseExtractor extractor = new DatabaseExtractor();
        Connection conn = extractor.getConnection();
        List<Schema> schemas = extractor.extract(conn);
        DataSource dataSource = extractor.getDataSource();
//        System.out.println(extractor.serialize(extractor.lookupSchema("public")));
        String json = extractor.serialize(dataSource);
        System.out.println(json);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://localhost:9000/import-schema");
        httpPost.setEntity(new StringEntity(json));
        httpPost.setHeader("Accept", "text/json");
        httpPost.setHeader("Content-type", "text/json; charset=UTF-8");
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode());
        conn.close();
    }
}
