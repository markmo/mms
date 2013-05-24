
package mms.common.models.pmml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CAT-SCORING-METHOD.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CAT-SCORING-METHOD">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="majorityVote"/>
 *     &lt;enumeration value="weightedMajorityVote"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CAT-SCORING-METHOD", namespace = "http://www.dmg.org/PMML-4_1")
@XmlEnum
public enum CATSCORINGMETHOD {

    @XmlEnumValue("majorityVote")
    MAJORITY_VOTE("majorityVote"),
    @XmlEnumValue("weightedMajorityVote")
    WEIGHTED_MAJORITY_VOTE("weightedMajorityVote");
    private final String value;

    CATSCORINGMETHOD(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CATSCORINGMETHOD fromValue(String v) {
        for (CATSCORINGMETHOD c: CATSCORINGMETHOD.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
