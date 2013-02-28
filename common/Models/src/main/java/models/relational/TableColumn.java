package models.relational;

import models.*;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.envers.Audited;
import play.db.jpa.JPA;

import javax.persistence.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * User: markmo
 * Date: 26/02/13
 * Time: 6:49 PM
 */
@Entity
@javax.persistence.Table(name = "table_column")
@Inheritance(strategy = InheritanceType.JOINED)
@Audited
public class TableColumn extends models.Column {

    private int precision;

    private int scale;

    private boolean isDimension;

    private String defaultValue;

    @javax.persistence.Column(name = "autoinc")
    private int intAutoinc;

    @javax.persistence.Column(name = "nullable")
    private int intNullable;

    private boolean isAutoinc;

    private boolean isNullable;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sql_datatype_id")
    private SqlDataType sqlDataType;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private models.relational.Table table;

    public static TableColumn parseColumn(ObjectMapper mapper, JsonNode json, models.relational.Table table) throws IOException {
        String name = json.path("name").getTextValue();
        int columnIndex = json.path("columnIndex").getIntValue();
        DataType dataType = mapper.readValue(json.path("dataType"), DataType.class);
        DataType existingDataType = DataType.findByName(dataType.getName());
        SqlDataType sqlDataType = mapper.readValue(json.path("sqlDataType"), SqlDataType.class);
        SqlDataType existingSqlDataType = SqlDataType.findByName(sqlDataType.getName());
        ColumnRole columnRole = mapper.readValue(json.path("role"), ColumnRole.class);
        ColumnRole existingColumnRole = ColumnRole.findByName(columnRole.getName());
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
        TableColumn existingColumn = (TableColumn)findByName(name, table);
        TableColumn column = (existingColumn == null ? new TableColumn() : existingColumn);
        column.setName(name);
        column.setColumnIndex(columnIndex);
        column.setDataType((existingDataType == null ? dataType : existingDataType));
        column.sqlDataType = (existingSqlDataType == null ? sqlDataType : existingSqlDataType);
        column.setRole((existingColumnRole == null ? columnRole : existingColumnRole));
        column.intAutoinc = intAutoInc;
        column.intNullable = intNullable;
        column.precision = precision;
        column.scale = scale;
        column.defaultValue = defaultValue;
        column.setMinValue(minValue);
        column.setMaxValue(maxValue);
        column.setDistinctCount(distinctCount);
        column.setDistinctValues(distinctValues);
        column.setFilterTypes(filterTypes);
        JPA.em().persist(column);
        return column;
    }

    /**
     * The total number of digits in the field.
     * Scale must be specified when precision is specified.
     *
     * @return precision
     */
    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    /**
     * The number of digits on the right of the decimal separator.
     * The scale attribute is valid only if the precision attribute
     * is specified.
     *
     * @return scale
     */
    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public boolean isDimension() {
        return isDimension;
    }

    public void setDimension(boolean dimension) {
        isDimension = dimension;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getIntAutoinc() {
        return intAutoinc;
    }

    public void setIntAutoinc(int intAutoinc) {
        this.intAutoinc = intAutoinc;
    }

    public int getIntNullable() {
        return intNullable;
    }

    public void setIntNullable(int intNullable) {
        this.intNullable = intNullable;
    }

    public boolean isAutoinc() {
        return isAutoinc;
    }

    public void setAutoinc(boolean autoinc) {
        isAutoinc = autoinc;
        intAutoinc = autoinc ? 1 : 0;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
        intNullable = nullable ? 1 : 0;
    }

    public SqlDataType getSqlDataType() {
        return sqlDataType;
    }

    public void setSqlDataType(SqlDataType sqlDataType) {
        this.sqlDataType = sqlDataType;
    }

    public models.relational.Table getTable() {
        return table;
    }

    public void setTable(models.relational.Table table) {
        this.table = table;
    }
}
