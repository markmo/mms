package mms.common.models;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 18/04/13
 * Time: 5:22 PM
 */
@Entity
public class PatternResult {

    @Id
    @GeneratedValue
    @javax.persistence.Column(name = "pattern_result_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "column_id")
    private Column column;

    @ManyToOne
    @JoinColumn(name = "analysis_type_id")
    private AnalysisType analysisType;

    private String pattern;

    private Integer matchCount;

    private String sample;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Integer getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(Integer matchCount) {
        this.matchCount = matchCount;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatternResult that = (PatternResult) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
