
package mms.common.models.pmml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TIMESERIES-ALGORITHM.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TIMESERIES-ALGORITHM">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ARIMA"/>
 *     &lt;enumeration value="ExponentialSmoothing"/>
 *     &lt;enumeration value="SeasonalTrendDecomposition"/>
 *     &lt;enumeration value="SpectralAnalysis"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TIMESERIES-ALGORITHM", namespace = "http://www.dmg.org/PMML-4_1")
@XmlEnum
public enum TIMESERIESALGORITHM {

    ARIMA("ARIMA"),
    @XmlEnumValue("ExponentialSmoothing")
    EXPONENTIAL_SMOOTHING("ExponentialSmoothing"),
    @XmlEnumValue("SeasonalTrendDecomposition")
    SEASONAL_TREND_DECOMPOSITION("SeasonalTrendDecomposition"),
    @XmlEnumValue("SpectralAnalysis")
    SPECTRAL_ANALYSIS("SpectralAnalysis");
    private final String value;

    TIMESERIESALGORITHM(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TIMESERIESALGORITHM fromValue(String v) {
        for (TIMESERIESALGORITHM c: TIMESERIESALGORITHM.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
