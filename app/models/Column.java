package models;

import javax.persistence.*;
import java.util.List;

import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 5:12 PM
 */
@Entity
@javax.persistence.Table(name="ds_column")
public class Column extends AuditedModel {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public.ds_column_column_id_seq")
    @javax.persistence.Column(name="column_id")
    public Long id;

    @javax.persistence.Column(name="column_name")
    @Constraints.Required
    public String name;

    @ManyToOne
    @JoinColumn(name="table_id")
    public Table table;

    @javax.persistence.Column(name="ordinal_position")
    public int columnIndex;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="data_type_id")
    public DataType dataType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="sql_data_type_id")
    public SqlDataType sqlDataType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="column_role_id")
    public ColumnRole role;

    @javax.persistence.Column(name="autoinc")
    public int intAutoinc;

    @javax.persistence.Column(name="nullable")
    public int intNullable;

    @Transient
    public int intUnique;

    public int precision;

    public int scale;

    public String defaultValue;

    //public List<Cell> cells;

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

//    public static Finder<Long, Column> find = new Finder<Long, Column>(
//            Long.class, Column.class
//    );

    public static List<Column> findByTableId(Long tableId) {
//        return find.where().eq("table.id", tableId).findList();
        Query q = JPA.em().createQuery("from Column c where c.table.id = ?1");
        q.setParameter(1, tableId);
        return q.getResultList();
    }
}
