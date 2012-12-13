package nz.co.datascience.mms.model;

import java.util.Date;

/**
 * User: markmo
 * Date: 24/09/12
 * Time: 3:54 PM
 */
public class Cell {

    private int id;
    private int columnIndex;
    private int rowNum;
    private String cellRef;
    private boolean formula;
    private int indentLevel;

    private String stringValue;
    private Date dateValue;
    private double numericValue;
    private boolean booleanValue;

    private DataType dataType;
    private Column column;
    private Row row;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getCellRef() {
        return cellRef;
    }

    public void setCellRef(String cellRef) {
        this.cellRef = cellRef;
    }

    public boolean isFormula() {
        return formula;
    }

    public void setFormula(boolean formula) {
        this.formula = formula;
    }

    public int getIndentLevel() {
        return indentLevel;
    }

    public void setIndentLevel(int indentLevel) {
        this.indentLevel = indentLevel;
        if (column != null && indentLevel > 0) {
            column.setIndented(true);
        }
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
        this.stringValue = dateValue.toString();
    }

    public double getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(double numericValue) {
        this.numericValue = numericValue;
        this.stringValue = Double.toString(numericValue);
    }

    public boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
        this.stringValue = Boolean.toString(booleanValue);
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return cellRef + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return (id == cell.id);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
