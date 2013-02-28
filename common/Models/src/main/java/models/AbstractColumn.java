package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 * User: markmo
 * Date: 27/02/13
 * Time: 10:17 PM
 */
@Entity
@Table(name = "abstract_column")
public abstract class AbstractColumn extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "column_id")
    private Long id;

    @Column(name = "column_name")
    @Constraints.Required
    private String name;

    @Column(name = "ordinal_position")
    private int columnIndex;

    private AbstractColumn parent;

    @ManyToOne
    @JoinColumn(name = "dataset_id")
    @JsonIgnore
    private Dataset dataset;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public AbstractColumn getParent() {
        return parent;
    }

    public void setParent(AbstractColumn parent) {
        this.parent = parent;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractColumn that = (AbstractColumn) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
