package nz.co.datascience.mms.model;

import java.util.Comparator;

/**
 * User: markmo
 * Date: 8/10/12
 * Time: 11:48 PM
 */
public class RowCellComparator implements Comparator<Cell> {

    public int compare(Cell cell1, Cell cell2) {
        if (cell1 == null || cell2 == null) return 0;
        if (cell1.getColumnIndex() == cell2.getColumnIndex()) {
            return 0;
        } else {
            return (cell1.getColumnIndex() < cell2.getColumnIndex()) ? -1 : 1;
        }
    }
}
