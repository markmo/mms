package nz.co.datascience.mms.model;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 1:43 PM
 */
public class DatabaseColumn extends Column {

    private SqlDataType sqlDataType;
    private boolean isAutoinc;
    private boolean isNullable;
    private int precision;
    private int scale;
    private String defaultValue;

    public SqlDataType getSqlDataType() {
        return sqlDataType;
    }

    public void setSqlDataType(SqlDataType sqlDataType) {
        this.sqlDataType = sqlDataType;
    }

    public boolean isAutoinc() {
        return isAutoinc;
    }

    public void setAutoinc(boolean autoinc) {
        isAutoinc = autoinc;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
