package mms.common.models;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;

import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 18/04/13
 * Time: 2:44 PM
 */
@Entity
public class MetricValue {

    @EmbeddedId
    private MetricValuePK pk;

    @JoinColumn(name = "numeric_value")
    private BigDecimal value;

    @JoinColumn(name = "string_value")
    private String stringValue;

    @SuppressWarnings("unchecked")
    public static List<MetricValue> findByColumnId(Long columnId) {
        return JPA.em().createQuery(
                "select v from MetricValue v where v.pk.column.id = ?1"
        )
                .setParameter(1, columnId)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public static List<MetricValue> findByColumnAndAnalysisType(Long columnId,
                                                                String analysisTypeName) {
        return JPA.em().createQuery(
                "select v from MetricValue v where v.pk.column.id = ?1 " +
                   "and v.pk.analysisType.name = ?2"
        )
                .setParameter(1, columnId)
                .setParameter(2, analysisTypeName)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public static List<MetricValue> findByColumnAndAnalysisTypeAndMetric(Long columnId,
                                                                         String analysisTypeName,
                                                                         String metricName) {
        return JPA.em().createQuery(
                "select v from MetricValue v where v.pk.column.id = ?1 " +
                   "and v.pk.analysisType.name = ?2 " +
                   "and v.pk.metric.name = ?3"
        )
                .setParameter(1, columnId)
                .setParameter(2, analysisTypeName)
                .setParameter(3, metricName)
                .getResultList();
    }

    public MetricValuePK getPk() {
        return pk;
    }

    public void setPk(MetricValuePK pk) {
        this.pk = pk;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        if (value != null) {
            return String.valueOf(value);
        } else {
            return stringValue;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetricValue that = (MetricValue) o;

        if (pk != null ? !pk.equals(that.pk) : that.pk != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return pk != null ? pk.hashCode() : 0;
    }
}
