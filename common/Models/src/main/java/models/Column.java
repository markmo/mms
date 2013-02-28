package models;

import static utils.JPA_Helper.getSingleResult;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import play.db.jpa.JPA;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: markmo
 * Date: 24/09/12
 * Time: 3:51 PM
 *
 * A column in a result set, a view, a table, or an SQLStructuredType.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "column")
@DiscriminatorValue("COL")
@Audited
public class Column extends AbstractColumn {

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

    private NullableType nullable;

    @Transient
    private int intUnique;

    private boolean isUnique;

    private String characterSetName;

    private String collationName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "datatype_id")
    private DataType dataType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "column_role_id")
    private ColumnRole role;

    @ManyToMany
    @JsonIgnore
    private Set<FilterType> filterTypes;

    // Statistics
    private Object minValue;
    private Object maxValue;
    private int distinctCount;

    @javax.persistence.Column(length = 8000)
    private String distinctValues;

    private boolean hasNulls;

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
}
