package models.domain.excel;

import javax.persistence.*;

import models.domain.file.FileColumn;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 1:43 PM
 */
@Entity
@DiscriminatorValue("XLS")
public class ExcelColumn extends FileColumn {

    private boolean isHidden;
    private boolean isIndented;

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public boolean isIndented() {
        return isIndented;
    }

    public void setIndented(boolean indented) {
        isIndented = indented;
    }

    public void addCell(ExcelCell cell) {
        if (cell.getIndentLevel() > 0) {
            setIndented(true);
        }
        super.addCell(cell);
    }
}
