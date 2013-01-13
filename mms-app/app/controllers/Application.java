package controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import be.objectify.deadbolt.actions.Restrict;
import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.google.inject.Inject;
import models.*;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import play.Routes;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthProvider.MyLogin;
import providers.MyUsernamePasswordAuthProvider.MySignup;

import views.html.*;

public class Application extends Controller {

    public static final String FLASH_MESSAGE_KEY = "message";
    public static final String FLASH_ERROR_KEY = "error";
    public static final String USER_ROLE = "user";

    static Form<Table> tableForm = form(Table.class);

    @Inject
    static ObjectMapper mapper;

    @Transactional(readOnly = true)
//    @Restrict(Application.USER_ROLE)
    public static Result index() {
//        return redirect(routes.Tables.index());
        if (session("theme") == null) {
            session("theme", "spacelab");
        }
        return ok(
                views.html.tables.render("dataSources")
        );
    }

    public static Result theme(String theme) {
        session("theme", theme);
        return redirect(routes.Application.index());
    }

    public static Result tables() {
        return ok(
                views.html.index.render(
                        JPA.em().createQuery("select t from Table t", Table.class).getResultList(),
                        //Table.find.all(),
                        tableForm)
        );
    }

    public static Result newTable() {
        return TODO;
    }

    public static Result deleteTable(Long id) {
        return TODO;
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result importSchema() throws Exception {
        Http.RequestBody body = request().body();
        JsonNode json = request().body().asJson();
        DataSource dataSource = parseDataSource(json);

//        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        DataSource dataSource = mapper.readValue(json, DataSource.class);
//        dataSource.save();
//        JPA.em().persist(dataSource);

        return ok();
    }

    private static DataSource parseDataSource(JsonNode json) throws IOException {
        String name = json.path("name").getTextValue();
        DataSource existingDataSource = DataSource.findByName(name);
        DataSource dataSource = (existingDataSource == null ? new DataSource() : existingDataSource);
        dataSource.name = name;
        JPA.em().persist(dataSource);
        Set<Schema> schemas = null;
        JsonNode schemasJson = json.path("schemas");
        if (schemasJson.size() > 0) {
            schemas = new HashSet<Schema>();
            Iterator<JsonNode> it = schemasJson.getElements();
            while (it.hasNext()) {
                Schema schema = parseSchema(it.next(), dataSource);
                if (schema != null) {
                    schemas.add(schema);
                }
            }
        }
        dataSource.setSchemas(schemas);
        JPA.em().persist(dataSource);
        return dataSource;
    }

    private static Schema parseSchema(JsonNode json, DataSource dataSource) throws IOException {
        String name = json.path("name").getTextValue();
        Schema existingSchema = Schema.findByName(name, dataSource);
        Schema schema = (existingSchema == null ? new Schema() : existingSchema);
        schema.name = name;
        schema.dataSource = dataSource;
        JPA.em().persist(schema);
        Set<Table> tables = null;
        JsonNode tablesJson = json.path("tables");
        if (tablesJson.size() > 0) {
            tables = new HashSet<Table>();
            Iterator<JsonNode> it = tablesJson.getElements();
            while (it.hasNext()) {
                Table table = parseTable(it.next(), schema);
                if (table != null) {
                    tables.add(table);
                }
            }
        }
        schema.setTables(tables);
        JPA.em().persist(schema);
        return schema;
    }

    private static Table parseTable(JsonNode json, Schema schema) throws IOException {
        String name = json.path("name").getTextValue();
        Table existingTable = Table.findByName(name, schema);
        Table table = (existingTable == null ? new Table() : existingTable);
        table.name = name;
        TableType tableType = mapper.readValue(json.path("tableType"), TableType.class);
        TableType existingTableType = TableType.findByName(tableType.name);
        table.tableType = (existingTableType == null ? tableType : existingTableType);
        TableRole tableRole = mapper.readValue(json.path("role"), TableRole.class);
        TableRole existingTableRole = TableRole.findByName(tableRole.name);
        table.role = (existingTableRole == null ? tableRole : existingTableRole);
        table.rowCount = json.path("rowCount").getIntValue();
        JPA.em().persist(table);
        Set<Column> columns = null;
        JsonNode columnsJson = json.path("columns");
        if (columnsJson.size() > 0) {
            columns = new HashSet<Column>();
            Iterator<JsonNode> it = columnsJson.getElements();
            while (it.hasNext()) {
                Column column = parseColumn(it.next(), table);
                if (column != null) {
                    column.table = null; // TODO
                    columns.add(column);
                }
            }
        }
        table.setColumns(columns);
        JPA.em().persist(table);
        return table;
    }

    private static Column parseColumn(JsonNode json, Table table) throws IOException {
        String name = json.path("name").getTextValue();
        int columnIndex = json.path("columnIndex").getIntValue();
        DataType dataType = mapper.readValue(json.path("dataType"), DataType.class);
        DataType existingDataType = DataType.findByName(dataType.name);
        SqlDataType sqlDataType = mapper.readValue(json.path("sqlDataType"), SqlDataType.class);
        SqlDataType existingSqlDataType = SqlDataType.findByName(sqlDataType.name);
        ColumnRole columnRole = mapper.readValue(json.path("role"), ColumnRole.class);
        ColumnRole existingColumnRole = ColumnRole.findByName(columnRole.name);
        int intAutoInc = json.path("autoinc").getIntValue();
        int intNullable = json.path("nullable").getIntValue();
        int precision = json.path("precision").getIntValue();
        int scale = json.path("scale").getIntValue();
        String defaultValue = json.path("defaultValue").getTextValue();
        String minValue = json.path("minValue").getTextValue();
        String maxValue = json.path("maxValue").getTextValue();
        int distinctCount = json.path("distinctCount").getIntValue();
        String distinctValues = json.path("distinctValues").getTextValue();
        Set<FilterType> filterTypes = null;
        JsonNode filterTypesJson = json.path("filterTypes");
        if (filterTypesJson.size() > 0) {
            filterTypes = new HashSet<FilterType>();
            Iterator<JsonNode> it = filterTypesJson.getElements();
            while (it.hasNext()) {
                JsonNode filterTypeJson = it.next();
                String filterTypeName = filterTypeJson.path("name").getTextValue();
                FilterType existingFilterType = FilterType.findByName(filterTypeName);
                if (existingFilterType == null) {
                    FilterType filterType = mapper.readValue(filterTypeJson, FilterType.class);
                    JPA.em().persist(filterType);
                    filterTypes.add(filterType);
                } else {
                    filterTypes.add(existingFilterType);
                }
            }

        }
        Column existingColumn = Column.findByName(name, table);
        Column column = (existingColumn == null ? new Column() : existingColumn);
        column.name = name;
        column.columnIndex = columnIndex;
        column.dataType = (existingDataType == null ? dataType : existingDataType);
        column.sqlDataType = (existingSqlDataType == null ? sqlDataType : existingSqlDataType);
        column.role = (existingColumnRole == null ? columnRole : existingColumnRole);
        column.intAutoinc = intAutoInc;
        column.intNullable = intNullable;
        column.precision = precision;
        column.scale = scale;
        column.defaultValue = defaultValue;
        column.minValue = minValue;
        column.maxValue = maxValue;
        column.distinctCount = distinctCount;
        column.distinctValues = distinctValues;
        column.setFilterTypes(filterTypes);
        JPA.em().persist(column);
        return column;
    }

    @Transactional(readOnly = true)
    public static Result revisions() throws IOException {
        List<CustomTrackingRevisionEntity> revisions = JPA.em().createQuery(
                "select r from CustomTrackingRevisionEntity r",
                CustomTrackingRevisionEntity.class)
                .getResultList();
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (CustomTrackingRevisionEntity revision : revisions) {
            Map<String, Object> entry = new HashMap<String, Object>();
            entry.put("revisionId", revision.getId());
            entry.put("revisionDate", revision.getRevisionDate());
            entry.put("numberEntitiesChanged", revision.getNumberEntitiesChanged());
            result.add(entry);
        }
        // TODO: move this to a global setting
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        mapper.setDateFormat(df);
        String json = mapper.writeValueAsString(result);
        return ok(json).as("application/json");
    }

    public static Result showRevisions() {
        return ok(views.html.tables.render("revisions"));
    }

    @Transactional(readOnly = true)
    public static Result getRevision(Integer revisionId) throws IOException {
        CustomTrackingRevisionEntity revision = JPA.em().createQuery(
                "select r from CustomTrackingRevisionEntity r where r.id = ?1",
                CustomTrackingRevisionEntity.class)
                .setParameter(1, revisionId)
                .getSingleResult();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("revisionId", revision.getId());
        result.put("revisionDate", revision.getRevisionDate());
        List<Map<String, Object>> modifiedEntities = new ArrayList<Map<String, Object>>();
        for (ModifiedEntityTypeEntity mod : revision.getModifiedEntityTypes()) {
            Map<String, Object> entry = new HashMap<String, Object>();
            entry.put("revisionType", mod.revisionType);
            entry.put("entityName", mod.entityName);
            entry.put("entityId", mod.entityId);
            modifiedEntities.add(entry);
        }
        result.put("modifiedEntities", modifiedEntities);

        // TODO: move this to a global setting
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        mapper.setDateFormat(df);
        String json = mapper.writeValueAsString(result);
        return ok(json).as("application/json");
    }

    public static User getLocalUser(final Http.Session session) {
        final User localUser = User.findByAuthUserIdentity(
                PlayAuthenticate.getUser(session));
        return localUser;
    }

    @Restrict(Application.USER_ROLE)
    public static Result restricted() {
        final User localUser = getLocalUser(session());
        return ok(restricted.render(localUser));
    }

    @Transactional(readOnly = true)
    @Restrict(Application.USER_ROLE)
    public static Result profile() {
        final User localUser = getLocalUser(session());
        return ok(profile.render(localUser));
    }

    @Transactional(readOnly = true)
    @Restrict(Application.USER_ROLE)
    public static Result profileAsJSON() throws IOException {
        final User localUser = getLocalUser(session());
        String json = mapper.writeValueAsString(localUser.getDTO());
        return ok(json).as("application/json");
    }

    public static Result login() {
        return ok(login.render(MyUsernamePasswordAuthProvider.LOGIN_FORM));
    }

    @Transactional
    public static Result doLogin() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
        final Form<MyLogin> filledForm =
                MyUsernamePasswordAuthProvider.LOGIN_FORM.bindFromRequest();
        if (filledForm.hasErrors()) {
            // User did not fill everything properly
            return badRequest(login.render(filledForm));
        } else {
            // Everything was filled
            return UsernamePasswordAuthProvider.handleLogin(ctx());
        }
    }

    public static Result signup() {
        return ok(signup.render(MyUsernamePasswordAuthProvider.SIGNUP_FORM));
    }

    public static Result jsRoutes() {
        return ok(
                Routes.javascriptRouter("jsRoutes",
                        controllers.routes.javascript.Signup.forgotPassword()))
                .as("text/javascript");
    }

    @Transactional
    public static Result doSignup() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
        final Form<MySignup> filledForm =
                MyUsernamePasswordAuthProvider.SIGNUP_FORM.bindFromRequest();
        if (filledForm.hasErrors()) {
            // User did not fill everything properly
            return badRequest(signup.render(filledForm));
        } else {
            // Everything was filled. Do something with your part of the form
            // before handling the user signup
            return UsernamePasswordAuthProvider.handleSignup(ctx());
        }
    }

    public static String formatTimestamp(final long t) {
        return new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").format(new Date(t));
    }
}
