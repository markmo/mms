package models;

import static controllers.Application.getSingleResult;
import static utils.CollectionUtils.safe;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 5:12 PM
 */
@Entity
@Table(name = "ds_column")
@DiscriminatorValue("COL")
@Audited
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Column extends AuditedModel {

    @Id
    @GeneratedValue//(strategy = GenerationType.SEQUENCE, generator = "public.ds_column_column_id_seq")
    @javax.persistence.Column(name = "column_id")
    public long id;

    @javax.persistence.Column(name = "column_name")
    @Constraints.Required
    public String name;

    @ManyToOne
    @JoinColumn(name = "table_id")
    public Table table;

    @javax.persistence.Column(name = "ordinal_position")
    public int columnIndex;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "data_type_id")
    public DataType dataType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sql_data_type_id")
    public SqlDataType sqlDataType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "column_role_id")
    public ColumnRole role;

    @javax.persistence.Column(name = "autoinc")
    public int intAutoinc;

    @javax.persistence.Column(name = "nullable")
    public int intNullable;

    @Transient
    public int intUnique;

    public int precision;

    public int scale;

    public String defaultValue;

    public String minValue;

    public String maxValue;

    public int distinctCount;

    @javax.persistence.Column(length = 8000)
    public String distinctValues;

    public int hasNulls;

    //public List<Cell> cells;

    @ManyToMany
    @JsonIgnore
    private Set<FilterType> filterTypes;

    /*
    @Transient
    private String pruningState;

    @Transient
    private Map<String, String> fieldIdMap;
    */

    public boolean isAutoinc() {
        return (intAutoinc == 1);
    }

    public void setAutoinc(boolean autoinc) {
        this.intAutoinc = (autoinc ? 1 : 0);
    }

    public boolean isNullable() {
        return (intNullable == 1);
    }

    public void setNullable(boolean nullable) {
        this.intNullable = (nullable ? 1 : 0);
    }

    public boolean isUnique() {
        return (intUnique == 1);
    }

    public void setUnique(boolean unique) {
        this.intUnique = (unique ? 1 : 0);
    }

    public Set<FilterType> getFilterTypes() {
        return filterTypes;
    }

    public void setFilterTypes(Set<FilterType> filterTypes) {
        for (FilterType filterType : safe(filterTypes)) {
            addFilterType(filterType);
        }
    }

    public void addFilterType(FilterType filterType) {
        if (filterTypes == null) {
            filterTypes = new HashSet<FilterType>();
        }

//        Getting java.sql.BatchUpdateException
//        try {
//            FilterType f = JPA.em().createQuery(
//                    "from FilterType f where f.name = ?1",
//                    FilterType.class)
//                    .setParameter(1, filterType.name)
//                    .getSingleResult();
//            filterTypes.add(f);
//        } catch (NoResultException e) {
//            JPA.em().persist(filterType);
//            JPA.em().flush();
//            filterTypes.add(filterType);
//        }
    }

    @SuppressWarnings("unchecked")
    public static List<Column> findByTableId(Long tableId) {
        return JPA.em().createQuery(
                "select c from Column c where c.table.id = ?1"
        )
                .setParameter(1, tableId)
                .getResultList();
    }

    public static Column findByName(String name, Table table) {
        return getSingleResult(Column.class,
                JPA.em().createQuery(
                        "select c from Column c where c.name = ?1 and c.table.id = ?2"
                )
                        .setParameter(1, name)
                        .setParameter(2, table.id)
        );
    }
}
