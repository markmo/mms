package mms.common.models.excel;

import mms.common.models.Row;

/**
 * User: markmo
 * Date: 3/10/12
 * Time: 10:03 PM
 */
public class ExcelRow extends Row {

    private int outlineLevel;
    private int maxIndentLevel;

    public int getOutlineLevel() {
        return outlineLevel;
    }

    public void setOutlineLevel(int outlineLevel) {
        this.outlineLevel = outlineLevel;
    }

    public int getMaxIndentLevel() {
        return maxIndentLevel;
    }

    public void setMaxIndentLevel(int maxIndentLevel) {
        this.maxIndentLevel = maxIndentLevel;
    }
}
