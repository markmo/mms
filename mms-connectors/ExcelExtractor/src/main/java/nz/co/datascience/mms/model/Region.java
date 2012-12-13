package nz.co.datascience.mms.model;

import com.google.common.base.Joiner;

import java.util.*;

/**
 * User: markmo
 * Date: 24/09/12
 * Time: 3:52 PM
 */
public class Region implements Iterable<Column> {

    private int id;
    private String primaryHeading;
    private String secondaryHeading;
    private List<String> annotations = new ArrayList<String>();
    private Cell firstCell;
    private Map<Integer, Column> columns = new HashMap<Integer, Column>();
    private Map<Integer, Row> rows = new HashMap<Integer, Row>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrimaryHeading() {
        return primaryHeading;
    }

    public void setPrimaryHeading(String primaryHeading) {
        this.primaryHeading = primaryHeading;
    }

    public String getSecondaryHeading() {
        return secondaryHeading;
    }

    public void setSecondaryHeading(String secondaryHeading) {
        this.secondaryHeading = secondaryHeading;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public void addAnnotation(String annotation) {
        this.annotations.add(annotation);
    }

    public void mergeRegionAsAnnotations(Region other) {
        if (other != null) {
            for (Row row : other.rows.values()) {
                List<String> annotation = new ArrayList<String>();
                for (Cell cell : row) {
                    annotation.add(cell.getStringValue());
                }
                addAnnotation(Joiner.on(", ").join(annotation));
            }
        }
    }

    public void mergeRegionAsHeadings(Region other) {
        if (other != null) {
            int k = 0;
            for (Row row : other.rows.values()) {
                List<String> heading = new ArrayList<String>();
                for (Cell cell : row) {
                    heading.add(cell.getStringValue());
                }
                if (k == 0) {
                    setPrimaryHeading(Joiner.on(", ").join(heading));
                } else if (k == 1) {
                    setSecondaryHeading(Joiner.on(", ").join(heading));
                } else {
                    break;
                }
                k += 1;
            }
        }
    }

    public Cell getFirstCell() {
        return firstCell;
    }

    public void setFirstCell(Cell firstCell) {
        this.firstCell = firstCell;
    }

    public int getLeftColumnIndex() {
        Integer[] columnIndexes = columns.keySet().toArray(new Integer[columns.keySet().size()]);
        Arrays.sort(columnIndexes);
        return columnIndexes[0];
    }

    public int getRightColumnIndex() {
        Integer[] columnIndexes = columns.keySet().toArray(new Integer[columns.keySet().size()]);
        Arrays.sort(columnIndexes);
        return columnIndexes[columnIndexes.length - 1];
    }

    public int getWidth() {
        return getRightColumnIndex() - getLeftColumnIndex() + 1;
    }

    public int getTopRowNum() {
        Integer[] rowNumbers = rows.keySet().toArray(new Integer[rows.keySet().size()]);
        Arrays.sort(rowNumbers);
        return rowNumbers[0];
    }

    public int getBottomRowNum() {
        Integer[] rowNumbers = rows.keySet().toArray(new Integer[rows.keySet().size()]);
        Arrays.sort(rowNumbers);
        return rowNumbers[rowNumbers.length - 1];
    }

    public int getHeight() {
        return getBottomRowNum() - getTopRowNum() + 1;
    }

    public boolean isRegionUnderAndMatchesWidth(Region other) {
        if (other != null) {
            if (other.getTopRowNum() < getTopRowNum() &&
                other.getLeftColumnIndex() == getLeftColumnIndex() &&
                other.getRightColumnIndex() == getRightColumnIndex())
            {
                return true;
            }
        }
        return false;
    }

    public boolean isRegionAbove(Region other) {
        if (other != null) {
            // allow overlap
//            System.out.println("other: " + other.getTopRowNum() + ", " + other.getLeftColumnIndex() + ", " + other.getBottomRowNum() + ", " + other.getRightColumnIndex());
//            System.out.println("this: " + getTopRowNum() + ", " + getLeftColumnIndex() + ", " + getBottomRowNum() + ", " + getRightColumnIndex());
            if (other.getTopRowNum() < getTopRowNum() &&
                other.getLeftColumnIndex() <= getRightColumnIndex() &&
                other.getRightColumnIndex() >= getLeftColumnIndex())
            {
                return true;
            }
        }
        return false;
    }

    public char directionFrom(Region other) {
        if (other != null) {
            if (other.getBottomRowNum() < getTopRowNum()) {
                return 'N';
            } else if (other.getTopRowNum() > getBottomRowNum()) {
                return 'S';
            } else if (other.getRightColumnIndex() < getLeftColumnIndex()) {
                return 'E';
            } else if (other.getLeftColumnIndex() > getRightColumnIndex()) {
                return 'W';
            }
        }
        return '\u0000';
    }

    public int distanceFrom(Region other) {
        if (other != null) {
            switch (directionFrom(other)) {
                case 'N':
                    return getTopRowNum() - other.getBottomRowNum();
                case 'S':
                    return other.getTopRowNum() - getBottomRowNum();
                case 'E':
                    return getLeftColumnIndex() - other.getRightColumnIndex();
                case 'W':
                    return other.getLeftColumnIndex() - getRightColumnIndex();
            }
        }
        return 99;
    }

    public void mergeRegion(Region other) {
        if (other != null) {
            for (Column column : other) {
                for (Cell cell : column.getCells()) {
                    addCell(cell);
                }
            }
        }
    }

    public Collection<Column> getColumns() {
        return columns.values();
    }

    public Iterator<Column> iterator() {
        return columns.values().iterator();
    }

    public Column getColumn(int columnIndex) {
        return columns.get(columnIndex);
    }

    public void addColumn(Column column) {
        if (column != null) {
            columns.put(column.getColumnIndex(), column);
        }
    }

    public Collection<Row> getRows() {
        return rows.values();
    }

    public Row getRow(int rowNum) {
        return rows.get(rowNum);
    }

    public void addRow(Row row) {
        if (row != null) {
            rows.put(row.getRowNum(), row);
        }
    }

    public void addCell(Cell cell) {
        if (cell != null) {
            if (columns.values().isEmpty()) {
                setFirstCell(cell);
            }
            int columnIndex = cell.getColumnIndex();
            Column column = getColumn(columnIndex);
            if (column == null) {
                column = new Column();
                column.setColumnIndex(columnIndex);
                addColumn(column);
            }
            column.addCell(cell);
            int rowNum = cell.getRowNum();
            Row row = getRow(rowNum);
            if (row == null) {
                row = new Row();
                row.setRowNum(rowNum);
                addRow(row);
            }
            row.addCell(cell);
        }
    }

    public void print() {
        System.out.println("\nRegion " + id + "(" + firstCell.getCellRef() + ")" + ":");
        Collection<Column> columns = getColumns();
        int n = columns.size();
        Column[] mycolumns = columns.toArray(new Column[n]);
        for (int i = 0; i < n; i++) {
            Column column = mycolumns[i];
            if (i > 0) System.out.print("\t");
            System.out.print(column);
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Region region = (Region) o;

        return (id == region.id);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
