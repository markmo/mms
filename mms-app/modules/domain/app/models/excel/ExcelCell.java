package models.domain.excel;

import models.domain.Cell;

/**
 * User: markmo
 * Date: 24/09/12
 * Time: 3:54 PM
 */
public class ExcelCell extends Cell {

    private boolean isFormula;
    private int indentLevel;

    public boolean isFormula() {
        return isFormula;
    }

    public void setFormula(boolean isFormula) {
        this.isFormula = isFormula;
    }

    public int getIndentLevel() {
        return indentLevel;
    }

    public void setIndentLevel(int indentLevel) {
        this.indentLevel = indentLevel;
        if (column != null && indentLevel > 0 && column instanceof ExcelColumn) {
            ((ExcelColumn)column).setIndented(true);
        }
    }
}
