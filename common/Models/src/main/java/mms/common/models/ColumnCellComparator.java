package mms.common.models;

import java.util.Comparator;

/**
 * User: markmo
 * Date: 8/10/12
 * Time: 11:48 PM
 */
public class ColumnCellComparator implements Comparator<Cell> {

    public int compare(Cell cell1, Cell cell2) {
        if (cell1 == null || cell2 == null) return 0;
        if (cell1.getRowNum() == cell2.getRowNum()) {
            return 0;
        } else {
            return (cell1.getRowNum() < cell2.getRowNum()) ? -1 : 1;
        }
    }
}
