package models.excel;

import models.file.FileColumn;
import models.relational.RowCellComparator;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.*;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 1:43 PM
 */
@Entity
@Table(name = "excel_column")
@Inheritance(strategy = InheritanceType.JOINED)
public class ExcelColumn extends FileColumn implements Iterable<Cell> {

    private boolean isHidden;
    private boolean isIndented;
    private SortedSet<Cell> cells;

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
        cells.add(cell);
        cell.setColumn(this);
        if (cell.getIndentLevel() > 0) {
            setIndented(true);
        }
    }

    public List<Cell> getValues() {
        if (cells == null) {
            return null;
        }
        return (new ArrayList<Cell>(cells)).subList((hasHeaderRow() ? 1 : 0), cells.size());
    }
}
