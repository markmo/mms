package nz.co.datascience.mms.model;

import java.util.*;

/**
 * User: markmo
 * Date: 24/09/12
 * Time: 3:51 PM
 *
 * A column in a result set, a view, a table, or an SQLStructuredType.
 */
public class Column implements Iterable<Cell> {

    public static enum NullableType {
        COLUMN_NO_NULLS,
        COLUMN_NULLABLE,
        COLUMN_NULLABLE_UNKNOWN
    }

    private int id;
    private String name;
    private int columnIndex;
    private int length;
    private int precision;
    private int scale;
    private NullableType nullable;
    private boolean isUnique;
    private boolean isDimension;
    private boolean isHidden;
    private boolean hasHeaderRow;
    private boolean isIndented;
    private String characterSetName;
    private String collationName;
    private DataType dataType;
    private ColumnRole role;

    private SortedSet<Cell> cells;
    private Set<FilterType> filterTypes;

    // Statistics
    private Object minValue;
    private Object maxValue;
    private int distinctCount;
    private String distinctValues;
    private boolean hasNulls;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Ordinal position.
     *
     * @return columnIndex
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    /**
     * The length of fixed length character or byte strings.
     * Maximum length if length is variable.
     *
     * @return length
     */
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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

    /**
     * Indicates if null values are valid in this column.
     *
     * @return nullable
     */
    public NullableType isNullableType() {
        return nullable;
    }

    public void setNullableType(NullableType nullable) {
        this.nullable = nullable;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean unique) {
        isUnique = unique;
    }

    public boolean isDimension() {
        return isDimension;
    }

    public void setDimension(boolean dimension) {
        isDimension = dimension;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public boolean hasHeaderRow() {
        return hasHeaderRow;
    }

    public void setHasHeaderRow(boolean hasHeaderRow) {
        this.hasHeaderRow = hasHeaderRow;
    }

    public boolean isIndented() {
        return isIndented;
    }

    public void setIndented(boolean indented) {
        isIndented = indented;
    }

    /**
     * The name of the character set used for the values in the column. This
     * field applies only to columns whose datatype is a character string.
     *
     * @return characterSetName
     */
    public String getCharacterSetName() {
        return characterSetName;
    }

    public void setCharacterSetName(String characterSetName) {
        this.characterSetName = characterSetName;
    }

    /**
     * The name of the collation sequence used to sort the data values in the
     * column. This applies only to columns whose datatype is a form of
     * character string.
     *
     * @return collationName
     */
    public String getCollationName() {
        return collationName;
    }

    public void setCollationName(String collationName) {
        this.collationName = collationName;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public ColumnRole getRole() {
        return role;
    }

    public void setRole(ColumnRole role) {
        this.role = role;
    }

    public List<Cell> getCells() {
        if (cells == null) {
            return null;
        }
        return new ArrayList<Cell>(cells);
    }

    public Iterator<Cell> iterator() {
        return cells.iterator();
    }

    public void setCells(SortedSet<Cell> cells) {
        this.cells = cells;
    }

    public void addCell(Cell cell) {
        if (cells == null) {
            cells = new TreeSet<Cell>(new RowCellComparator());
        }
        cells.add(cell);
        cell.setColumn(this);
        if (cell.getIndentLevel() > 0) {
            setIndented(true);
        }
    }

    public List<Cell> getValues() {
        if (cells == null) {
            return null;
        }
        return (new ArrayList<Cell>(cells)).subList((hasHeaderRow ? 1 : 0), cells.size());
    }

    public Set<FilterType> getFilterTypes() {
        return filterTypes;
    }

    public void setFilterTypes(Set<FilterType> filterTypes) {
        this.filterTypes = filterTypes;
    }

    public void addFilterType(FilterType filterType) {
        if (filterTypes == null) {
            filterTypes = new HashSet<FilterType>();
        }
        filterTypes.add(filterType);
    }

    public Object getMinValue() {
        return minValue;
    }

    public void setMinValue(Object minValue) {
        this.minValue = minValue;
    }

    public Object getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Object maxValue) {
        this.maxValue = maxValue;
    }

    public int getDistinctCount() {
        return distinctCount;
    }

    public void setDistinctCount(int distinctCount) {
        this.distinctCount = distinctCount;
    }

    public String getDistinctValues() {
        return distinctValues;
    }

    public void setDistinctValues(String distinctValues) {
        this.distinctValues = distinctValues;
    }

    public boolean isHasNulls() {
        return hasNulls;
    }

    public void setHasNulls(boolean hasNulls) {
        this.hasNulls = hasNulls;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column cells = (Column) o;

        if (id != cells.id) return false;
        if (name != null ? !name.equals(cells.name) : cells.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
