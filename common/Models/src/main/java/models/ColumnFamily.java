package models;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Set;

/**
 * User: markmo
 * Date: 28/02/13
 * Time: 7:20 AM
 *
 * Alias: Group (File)
 *
 * A Group is a structured data type and is used to collect together Field instances within a Record.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "column_family")
public class ColumnFamily extends AbstractColumn {

    private Set<AbstractColumn> children;

    public Set<AbstractColumn> getChildren() {
        return children;
    }

    public void setChildren(Set<AbstractColumn> children) {
        this.children = children;
    }
}
