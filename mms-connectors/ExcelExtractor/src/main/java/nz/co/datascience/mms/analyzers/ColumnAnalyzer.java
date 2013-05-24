package nz.co.datascience.mms.analyzers;

import mms.common.models.Cell;

import java.util.List;

/**
 * User: markmo
 * Date: 29/09/12
 * Time: 11:46 AM
 */
public class ColumnAnalyzer implements Analyzer {

    public AnalysisResult analyze(List<Cell> cells) {
        return analyzeTypes(cells);
    }

    public AnalysisResult analyzeTypes(List<Cell> cells) {
        TypeAnalyzer analyzer = new TypeAnalyzer();
        return analyzer.analyze(cells);
    }
}
