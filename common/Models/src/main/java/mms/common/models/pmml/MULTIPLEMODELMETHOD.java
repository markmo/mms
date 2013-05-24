
package mms.common.models.pmml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MULTIPLE-MODEL-METHOD.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MULTIPLE-MODEL-METHOD">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="majorityVote"/>
 *     &lt;enumeration value="weightedMajorityVote"/>
 *     &lt;enumeration value="average"/>
 *     &lt;enumeration value="weightedAverage"/>
 *     &lt;enumeration value="median"/>
 *     &lt;enumeration value="max"/>
 *     &lt;enumeration value="sum"/>
 *     &lt;enumeration value="selectFirst"/>
 *     &lt;enumeration value="selectAll"/>
 *     &lt;enumeration value="modelChain"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MULTIPLE-MODEL-METHOD", namespace = "http://www.dmg.org/PMML-4_1")
@XmlEnum
public enum MULTIPLEMODELMETHOD {

    @XmlEnumValue("majorityVote")
    MAJORITY_VOTE("majorityVote"),
    @XmlEnumValue("weightedMajorityVote")
    WEIGHTED_MAJORITY_VOTE("weightedMajorityVote"),
    @XmlEnumValue("average")
    AVERAGE("average"),
    @XmlEnumValue("weightedAverage")
    WEIGHTED_AVERAGE("weightedAverage"),
    @XmlEnumValue("median")
    MEDIAN("median"),
    @XmlEnumValue("max")
    MAX("max"),
    @XmlEnumValue("sum")
    SUM("sum"),
    @XmlEnumValue("selectFirst")
    SELECT_FIRST("selectFirst"),
    @XmlEnumValue("selectAll")
    SELECT_ALL("selectAll"),
    @XmlEnumValue("modelChain")
    MODEL_CHAIN("modelChain");
    private final String value;

    MULTIPLEMODELMETHOD(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MULTIPLEMODELMETHOD fromValue(String v) {
        for (MULTIPLEMODELMETHOD c: MULTIPLEMODELMETHOD.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
