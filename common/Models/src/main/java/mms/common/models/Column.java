package mms.common.models;

import static utils.JPA_Helper.getSingleResult;

import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 24/09/12
 * Time: 3:51 PM
 *
 * A column in a result set, a view, a table, or an SQLStructuredType.
 */
@Entity
@Audited
public abstract class Column extends AbstractColumn implements Iterable<Cell> {

    enum NullableType {
        COLUMN_NO_NULLS,
        COLUMN_NULLABLE,
        COLUMN_NULLABLE_UNKNOWN
    }

    @SuppressWarnings("unchecked")
    public static List<Column> findByDatasetId(Long datasetId) {
        return JPA.em().createQuery(
                "select c from Column c where c.dataset.id = ?1"
        )
                .setParameter(1, datasetId)
                .getResultList();
    }

    public static Column findByName(String name, Dataset dataset) {
        return getSingleResult(Column.class,
                JPA.em().createQuery(
                        "select c from Column c where c.name = ?1 and c.dataset.id = ?2"
                )
                        .setParameter(1, name)
                        .setParameter(2, dataset.getId())
        );
    }

    private int length;

    @Transient
    private NullableType nullable;

    @Transient
    private int intUnique;

    private boolean isUnique;

    private String characterSetName;

    private String collationName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "datatype_id")
    private DataType dataType;

    /**
     * fyi.
     * If you want to audit a relation, where the target entity is not audited
     * (that is the case for example with dictionary-like entities, which don't
     * change and don't have to be audited), just annotate it with
     * @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED). Then,
     * when reading historic versions of your entity, the relation will always
     * point to the "current" related entity.
     */

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "column_role_id")
    private ColumnRole role;

    @ManyToMany
    @JoinTable(
            name = "column_filter_types",
            joinColumns = @JoinColumn(name = "column_id"),
            inverseJoinColumns = @JoinColumn(name = "filter_type_id")
    )
    @JsonIgnore
    private Set<FilterType> filterTypes;

    // Statistics
    private String minValue;
    private String maxValue;
    private int distinctCount;

    @javax.persistence.Column(length = 8000)
    private String distinctValues;

    private boolean hasNulls;


    @Transient
    protected SortedSet<Cell> cells;

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
        intUnique = unique ? 1 : 0;
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

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
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
        cell.setColumn(this);
        cells.add(cell);
    }

    public List<Cell> getValues() {
        if (cells == null) {
            return null;
        }
        return new ArrayList<Cell>(cells);
    }
}
