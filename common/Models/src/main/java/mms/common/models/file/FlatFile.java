package mms.common.models.file;

import javax.persistence.*;

import mms.common.models.Dataset;

/**
 * User: markmo
 * Date: 27/02/13
 * Time: 8:37 PM
 *
 * Alias: RecordFile
 */
@Entity
@DiscriminatorValue("FIL")
public class FlatFile extends Dataset {

    private boolean isFirstRowIsHeader;
    private String rowDelimiter;
    private String columnDelimiter;
    private String textQualifier;

    /**
     * True if the contents of fields in the first record of the file contain
     * field names applicable to subsequent records.
     *
     * Alias: isSelfDescribing
     *
     * @return boolean
     */
    public boolean isFirstRowIsHeader() {
        return isFirstRowIsHeader;
    }

    public void setFirstRowIsHeader(boolean firstRowIsHeader) {
        isFirstRowIsHeader = firstRowIsHeader;
    }

    /**
     * Contains the value that serves as a logical end-of-record indication in
     * a stream-oriented file. A common example includes the usage of carriage-
     * return characters and carriage-return/line- feed character pairs as new-
     * line characters in ASCII text files.
     *
     * @return delimiter string
     */
    public String getRowDelimiter() {
        return rowDelimiter;
    }

    public void setRowDelimiter(String rowDelimiter) {
        this.rowDelimiter = rowDelimiter;
    }

    public String getColumnDelimiter() {
        return columnDelimiter;
    }

    public void setColumnDelimiter(String columnDelimiter) {
        this.columnDelimiter = columnDelimiter;
    }

    public String getTextQualifier() {
        return textQualifier;
    }

    public void setTextQualifier(String textQualifier) {
        this.textQualifier = textQualifier;
    }
}
