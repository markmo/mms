package nz.co.datascience.mms.extractors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Joiner;
import mms.common.models.factory.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.sql.*;
import java.util.*;

import mms.common.models.*;
import mms.common.models.relational.*;

/**
 * User: markmo
 * Date: 25/09/12
 * Time: 9:04 AM
 */
public class DatabaseExtractor {

    private String username = "sa";
    private String password = "Password1";
    private String dbms = "jtds:sqlserver";
    private String serverName = "192.168.211.152";
    private String portNumber = "1433";
    private String dbName = "DM_ScheduALL";
    private String datasourceName;
    private String catalogName;
    private String catalogDescription;

    private List<Namespace> schemas;
    private Datasource datasource;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDbms(String dbms) {
        this.dbms = dbms;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCatalogDescription() {
        return catalogDescription;
    }

    public void setCatalogDescription(String catalogDescription) {
        this.catalogDescription = catalogDescription;
    }

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

    public Namespace lookupSchema(String schemaName) {
        for (Namespace schema : schemas) {
            if (schema.getName().equals(schemaName)) {
                return schema;
            }
        }
        return null;
    }

    public Datasource getDatasource() {
        return datasource;
    }

    public List<Namespace> extract(Connection conn) throws Exception {
        this.schemas = new ArrayList<Namespace>();
        DatabaseMetaData metadata = conn.getMetaData();
        System.out.println("Database Product Name: " + metadata.getDatabaseProductName() + ", Version: " + metadata.getDatabaseMajorVersion() + "." + metadata.getDatabaseMinorVersion());
        Datasource datasource = new Datasource();
        datasource.setName(this.datasourceName);
        Catalog catalog = new Catalog();
        catalog.setName(this.catalogName);
        datasource.addCatalog(catalog);
        ResultSet schemas = metadata.getSchemas();
        System.out.println(schemas.getMetaData().getColumnCount());
        for (int i = 1; i < schemas.getMetaData().getColumnCount() + 1; i++) {
            System.out.println(schemas.getMetaData().getColumnName(i));
        }
        while (schemas.next()) {
            String schemaName = schemas.getString("TABLE_SCHEM");
            if (!schemaName.matches("(pg_.*|information_schema)")) {
                Namespace schema = new Namespace();
                schema.setName(schemaName);
                catalog.addNamespace(schema);
                this.schemas.add(schema);
                System.out.println("\nSchema: " + schemaName);
//            String catalogName = schemas.getString("TABLE_CATALOG");
//            System.out.println("Catalog: " + catalogName);
            }
        }
//        Namespace schema = new Namespace();
//        schema.setName("mms");
//        Namespace pub = new Namespace();
//        pub.setName("public");
//        this.schemas.add(schema);
//        this.schemas.add(pub);

//        catalog.addNamespace(schema);
//        catalog.addNamespace(pub);
        this.datasource = datasource;

//        ResultSet tables = metadata.getTables(null, "DDS", null, new String[]{"TABLE", "VIEW"});
        ResultSet tables = metadata.getTables("mms", null, null, new String[]{"TABLE", "VIEW"});
        while (tables.next()) {
            String schemaName = tables.getString("TABLE_SCHEM");
            String tableName = tables.getString("TABLE_NAME");
            String tableType = tables.getString("TABLE_TYPE");
            System.out.println("\n\tTable: " +schemaName + "." + tableName + " (" + tableType + ")");
            Table table = new Table();
            table.setName(tableName);
            table.setTableType(TableTypeFactory.getTableType(tableType));
            lookupSchema(schemaName).addDataset(table);
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
                TableColumn column = new TableColumn();
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
                Column column = (Column)table.getColumn((columnName));
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

    public void getStats(Namespace schema) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
        dataSource.setUrl("jdbc:" + this.dbms + "://" + this.serverName + ":" + this.portNumber + "/" + this.dbName);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        for (final Dataset table : schema.getDatasets()) {
            StringBuilder sql = new StringBuilder("select COUNT(*)");
            for (AbstractColumn column : table.getColumns()) {
                String columnName = column.getName();
                sql.append(",MIN(").append(columnName)
                    .append("),MAX(").append(columnName)
                    .append(")");
                getStats(jdbcTemplate, schema, (Table)table, (Column)column);
            }
            sql.append(" from ").append(schema.getName()).append(".").append(table.getName());
            Map<String, Object> result = jdbcTemplate.queryForObject(sql.toString(),
                    new RowMapper<Map<String, Object>>() {
                        public Map<String, Object> mapRow(ResultSet resultSet, int i) throws SQLException {
                            Map<String, Object> result = new HashMap<String, Object>();
                            int j = 2;
                            result.put("count", resultSet.getInt(1));
                            for (AbstractColumn column : table.getColumns()) {
                                String columnName = column.getName();
                                result.put("min" + columnName, resultSet.getObject(j++));
                                result.put("max" + columnName, resultSet.getObject(j++));
                            }
                            return result;
                        }
                    });
            table.setRowCount((Integer)result.get("count"));
            for (AbstractColumn abstractColumn : table.getColumns()) {
                Column column = (Column)abstractColumn;
                String columnName = column.getName();
                column.setMinValue((String)result.get("min" + columnName));
                column.setMaxValue((String)result.get("max" + columnName));
            }
        }
    }

    public void getStats(JdbcTemplate jdbcTemplate, Namespace schema, Table table, Column column) {
        String distinctSql = "select distinct " + column.getName() + " from " + schema.getName() + "." + table.getName();
        int distinctCount = jdbcTemplate.queryForInt("select COUNT(*) from (" + distinctSql + ") A");
        column.setDistinctCount(distinctCount);
        if (distinctCount < 100) {
            List<Object> result = jdbcTemplate.query(distinctSql,
                    new RowMapper<Object>() {
                        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                            return resultSet.getObject(1);
                        }
                    });
            Joiner joiner = Joiner.on('|').skipNulls();
            if (!result.isEmpty()) {
                column.setDistinctValues(joiner.join(result));
                column.setHasNulls(result.contains(null));
            }
        } else {
            column.setDistinctValues("lots");
        }
    }

    public static void main(String[] args) throws Exception {
        Map conf = loadConfig();
        List<Map> datasources = (List<Map>)conf.get("datasource");
        for (Map source : datasources) {
            if ((Boolean)source.get("active")) {
                DatabaseExtractor extractor = new DatabaseExtractor();
                extractor.setUsername((String)source.get("username"));
                extractor.setPassword((String)source.get("password"));
                extractor.setDbms((String)source.get("dbms"));
                extractor.setServerName((String)source.get("server-name"));
                extractor.setPortNumber(source.get("port-number").toString());
                extractor.setDbName((String)source.get("dbname"));
                extractor.setDatasourceName((String)source.get("description"));
                extractor.setCatalogName((String)source.get("dbname"));
                extractor.setCatalogDescription((String)source.get("name"));
                Connection conn = extractor.getConnection();
                List<Namespace> schemas = extractor.extract(conn);
                Datasource datasource = extractor.getDatasource();
                Extraction extraction = new Extraction(new Sandbox("Test"), datasource);
                //extractor.getStats(datasource.getSchemas().get(0));
                //System.out.println(extractor.serialize(extractor.lookupSchema("public")));
                String json = extractor.serialize(extraction);
                System.out.println(json);
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://localhost:9000/import-datasource");
                httpPost.setEntity(new StringEntity(json));
                httpPost.setHeader("Accept", "text/json");
                httpPost.setHeader("Content-type", "text/json; charset=UTF-8");
                HttpResponse response = httpClient.execute(httpPost);
                System.out.println(response.getStatusLine().getStatusCode());
                conn.close();
            }
        }
    }

    public static Map loadConfig() throws IOException {
        InputStream input = DatabaseExtractor.class.getResourceAsStream("/datasources.yaml");
        Yaml yaml = new Yaml();
        return (Map)yaml.load(input);
    }

    static class Extraction {

        private Sandbox sandbox;
        private Object data;

        public Extraction(Sandbox sandbox, Object data) {
            this.sandbox = sandbox;
            this.data = data;
        }

        public Sandbox getSandbox() {
            return sandbox;
        }

        public void setSandbox(Sandbox sandbox) {
            this.sandbox = sandbox;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
