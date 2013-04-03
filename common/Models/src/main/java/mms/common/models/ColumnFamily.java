package mms.common.models;

import java.util.Set;
import javax.persistence.*;

/**
 * User: markmo
 * Date: 28/02/13
 * Time: 7:20 AM
 *
 * Alias: Group (File)
 *
 * A Group is a structured data type and is used to collect together Field instances within a Record.
 */
//@Entity
//@DiscriminatorValue("FAM")
public class ColumnFamily extends AbstractColumn {

    @Transient
    private Set<AbstractColumn> children;

    public Set<AbstractColumn> getChildren() {
        return children;
    }

    public void setChildren(Set<AbstractColumn> children) {
        this.children = children;
    }
}
