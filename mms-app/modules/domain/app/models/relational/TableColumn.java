package models.domain.relational;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.db.jpa.JPA;

import models.domain.Column;
import models.domain.ColumnRole;
import models.domain.DataType;
import models.domain.FilterType;

/**
 * User: markmo
 * Date: 26/02/13
 * Time: 6:49 PM
 */
@Entity
@DiscriminatorValue("TAB")
public class TableColumn extends Column {

    @javax.persistence.Column(name = "precizion")
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
    private Table table;

    public static TableColumn parseColumn(ObjectMapper mapper, JsonNode json, Table table) throws IOException {
        String name = json.path("name").asText();
        int columnIndex = json.path("columnIndex").intValue();
        DataType dataType = mapper.readValue(json.path("dataType").binaryValue(), DataType.class);
        DataType existingDataType = DataType.findByName(dataType.getName());
        SqlDataType sqlDataType = mapper.readValue(json.path("sqlDataType").binaryValue(), SqlDataType.class);
        SqlDataType existingSqlDataType = SqlDataType.findByName(sqlDataType.getName());
        ColumnRole columnRole = mapper.readValue(json.path("role").binaryValue(), ColumnRole.class);
        ColumnRole existingColumnRole = ColumnRole.findByName(columnRole.getName());
        int intAutoInc = json.path("autoinc").intValue();
        int intNullable = json.path("nullable").intValue();
        int precision = json.path("precision").intValue();
        int scale = json.path("scale").intValue();
        String defaultValue = json.path("defaultValue").asText();
        String minValue = json.path("minValue").asText();
        String maxValue = json.path("maxValue").asText();
        int distinctCount = json.path("distinctCount").intValue();
        String distinctValues = json.path("distinctValues").asText();
        Set<FilterType> filterTypes = null;
        JsonNode filterTypesJson = json.path("filterTypes");
        if (filterTypesJson.size() > 0) {
            filterTypes = new HashSet<FilterType>();
            Iterator<JsonNode> it = filterTypesJson.elements();
            while (it.hasNext()) {
                JsonNode filterTypeJson = it.next();
                String filterTypeName = filterTypeJson.path("name").asText();
                FilterType existingFilterType = FilterType.findByName(filterTypeName);
                if (existingFilterType == null) {
                    FilterType filterType = mapper.readValue(filterTypeJson.binaryValue(), FilterType.class);
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

    public models.domain.relational.Table getTable() {
        return table;
    }

    public void setTable(models.domain.relational.Table table) {
        this.table = table;
    }
}
