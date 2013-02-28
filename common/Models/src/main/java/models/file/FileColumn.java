package models.file;

import models.Column;

import javax.persistence.Entity;

/**
 * User: markmo
 * Date: 26/02/13
 * Time: 6:52 PM
 */
@Entity
public class FileColumn extends Column {

    private boolean hasHeaderRow;

    public boolean hasHeaderRow() {
        return hasHeaderRow;
    }

    public void setHasHeaderRow(boolean hasHeaderRow) {
        this.hasHeaderRow = hasHeaderRow;
    }
}
