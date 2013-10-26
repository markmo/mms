package models.domain.file;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import models.domain.Cell;
import models.domain.Column;

/**
 * User: markmo
 * Date: 26/02/13
 * Time: 6:52 PM
 */
@Entity
@DiscriminatorValue("FIL")
public class FileColumn extends Column {

    private boolean hasHeaderRow;

    public boolean hasHeaderRow() {
        return hasHeaderRow;
    }

    public void setHasHeaderRow(boolean hasHeaderRow) {
        this.hasHeaderRow = hasHeaderRow;
    }

    @Override
    public List<Cell> getValues() {
        if (cells == null) {
            return null;
        }
        return (new ArrayList<Cell>(cells)).subList((hasHeaderRow() ? 1 : 0), cells.size());
    }
}
