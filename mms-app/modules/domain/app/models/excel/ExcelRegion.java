package models.domain.excel;

import java.util.*;
import javax.persistence.*;

import com.google.common.base.Joiner;

import models.domain.AbstractColumn;
import models.domain.Cell;
import models.domain.Column;
import models.domain.Dataset;

/**
 * User: markmo
 * Date: 24/09/12
 * Time: 3:52 PM
 */
@Entity
@DiscriminatorValue("XLS")
public class ExcelRegion extends Dataset implements Iterable<ExcelColumn> {

    private String primaryHeading;
    private String secondaryHeading;

    @Transient
    private List<String> annotations = new ArrayList<String>();

    @Transient
    private ExcelCell firstCell;

    @Transient
    private Map<Integer, ExcelColumn> columns = new HashMap<Integer, ExcelColumn>();

    @Transient
    private Map<Integer, ExcelRow> rows = new HashMap<Integer, ExcelRow>();

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
        annotations.add(annotation);
    }

    public void mergeRegionAsAnnotations(ExcelRegion other) {
        if (other != null) {
            for (ExcelRow row : other.rows.values()) {
                List<String> annotation = new ArrayList<String>();
                for (Cell cell : row) {
                    annotation.add(cell.getStringValue());
                }
                addAnnotation(Joiner.on(", ").join(annotation));
            }
        }
    }

    public void mergeRegionAsHeadings(ExcelRegion other) {
        if (other != null) {
            int k = 0;
            for (ExcelRow row : other.rows.values()) {
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

    public ExcelCell getFirstCell() {
        return firstCell;
    }

    public void setFirstCell(ExcelCell firstCell) {
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

    public boolean isRegionUnderAndMatchesWidth(ExcelRegion other) {
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

    public boolean isRegionAbove(ExcelRegion other) {
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

    public char directionFrom(ExcelRegion other) {
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

    public int distanceFrom(ExcelRegion other) {
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

    public void mergeRegion(ExcelRegion other) {
        if (other != null) {
            for (models.domain.Column column : other) {
                for (Cell cell : column.getCells()) {
                    addCell((ExcelCell)cell);
                }
            }
        }
    }

    @Override
    public List<AbstractColumn> getColumns() {
        return new ArrayList<AbstractColumn>(columns.values());
    }

    public Iterator<ExcelColumn> iterator() {
        return columns.values().iterator();
    }

    public ExcelColumn getColumn(int columnIndex) {
        return columns.get(columnIndex);
    }

    public void addColumn(ExcelColumn column) {
        if (column != null) {
            columns.put(column.getColumnIndex(), column);
        }
    }

    public Collection<ExcelRow> getRows() {
        return rows.values();
    }

    public ExcelRow getRow(int rowNum) {
        return rows.get(rowNum);
    }

    public void addRow(ExcelRow row) {
        if (row != null) {
            rows.put(row.getRowNum(), row);
        }
    }

    public void addCell(ExcelCell cell) {
        if (cell != null) {
            if (columns.values().isEmpty()) {
                setFirstCell(cell);
            }
            int columnIndex = cell.getColumnIndex();
            ExcelColumn column = getColumn(columnIndex);
            if (column == null) {
                column = new ExcelColumn();
                column.setColumnIndex(columnIndex);
                addColumn(column);
            }
            column.addCell(cell);
            int rowNum = cell.getRowNum();
            ExcelRow row = getRow(rowNum);
            if (row == null) {
                row = new ExcelRow();
                row.setRowNum(rowNum);
                addRow(row);
            }
            row.addCell(cell);
        }
    }

    public void print() {
        System.out.println("\nRegion " + getId() + "(" + firstCell.getCellRef() + ")" + ":");
        List<AbstractColumn> columns = getColumns();
        int n = columns.size();
        ExcelColumn[] mycolumns = columns.toArray(new ExcelColumn[n]);
        for (int i = 0; i < n; i++) {
            Column column = mycolumns[i];
            if (i > 0) System.out.print("\t");
            System.out.print(column);
        }
        System.out.println();
    }
}
