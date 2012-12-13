package nz.co.datascience.mms.analyzers;

import nz.co.datascience.mms.model.Cell;

import java.util.List;

/**
 * User: markmo
 * Date: 16/10/12
 * Time: 9:32 PM
 */
public interface Analyzer {

    public AnalysisResult analyze(List<Cell> cells);
}
