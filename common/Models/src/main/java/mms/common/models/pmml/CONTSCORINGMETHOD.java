
package mms.common.models.pmml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CONT-SCORING-METHOD.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CONT-SCORING-METHOD">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="median"/>
 *     &lt;enumeration value="average"/>
 *     &lt;enumeration value="weightedAverage"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CONT-SCORING-METHOD", namespace = "http://www.dmg.org/PMML-4_1")
@XmlEnum
public enum CONTSCORINGMETHOD {

    @XmlEnumValue("median")
    MEDIAN("median"),
    @XmlEnumValue("average")
    AVERAGE("average"),
    @XmlEnumValue("weightedAverage")
    WEIGHTED_AVERAGE("weightedAverage");
    private final String value;

    CONTSCORINGMETHOD(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CONTSCORINGMETHOD fromValue(String v) {
        for (CONTSCORINGMETHOD c: CONTSCORINGMETHOD.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
