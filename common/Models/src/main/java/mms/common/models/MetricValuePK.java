package mms.common.models;

import java.io.Serializable;
import javax.persistence.*;

/**
 * User: markmo
 * Date: 18/04/13
 * Time: 2:46 PM
 */
@Embeddable
public class MetricValuePK implements Serializable {

    private static final long serialVersionUID = -4958317424126880002L;

    @ManyToOne
    @JoinColumn(name = "column_id")
    private Column column;

    @ManyToOne
    @JoinColumn(name = "analysis_type_id")
    private AnalysisType analysisType;

    @ManyToOne
    @JoinColumn(name = "metric_id")
    private Metric metric;

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public AnalysisType getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(AnalysisType analysisType) {
        this.analysisType = analysisType;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    @Override
    public String toString() {
        return String.format("MetricValue{Column=%s(%s); AnalysisType=%s(%s); Metric=%s(%s)}",
                column.getName(), column.getId(),
                analysisType.getName(), analysisType.getId(),
                metric.getName(), metric.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetricValuePK that = (MetricValuePK) o;

        if (!analysisType.equals(that.analysisType)) return false;
        if (!column.equals(that.column)) return false;
        if (!metric.equals(that.metric)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = column.hashCode();
        result = 31 * result + analysisType.hashCode();
        result = 31 * result + metric.hashCode();
        return result;
    }
}
