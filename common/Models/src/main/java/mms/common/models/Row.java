package mms.common.models;

import java.util.*;

/**
 * User: markmo
 * Date: 25/03/13
 * Time: 5:24 PM
 */
public class Row implements Iterable<Cell> {

    private Long id;
    private String name;
    private int rowNum;

    protected SortedSet<Cell> cells;

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

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public List<Cell> getCells() {
        return new ArrayList<Cell>(cells);
    }

    public Iterator<Cell> iterator() {
        return cells.iterator();
    }

    public void setCells(SortedSet<Cell> cells) {
        this.cells = cells;
    }

    public void addCell(Cell cell) {
        if (this.cells == null) {
            this.cells = new TreeSet<Cell>(new ColumnCellComparator());
        }
        this.cells.add(cell);
        cell.setRow(this);
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Row row = (Row) o;

        return (id == row.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
