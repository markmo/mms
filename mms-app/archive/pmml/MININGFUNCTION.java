
package models.pmml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MINING-FUNCTION.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MINING-FUNCTION">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="associationRules"/>
 *     &lt;enumeration value="sequences"/>
 *     &lt;enumeration value="classification"/>
 *     &lt;enumeration value="regression"/>
 *     &lt;enumeration value="clustering"/>
 *     &lt;enumeration value="timeSeries"/>
 *     &lt;enumeration value="mixed"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "MINING-FUNCTION", namespace = "http://www.dmg.org/PMML-4_1")
@XmlEnum
public enum MININGFUNCTION {

    @XmlEnumValue("associationRules")
    ASSOCIATION_RULES("associationRules"),
    @XmlEnumValue("sequences")
    SEQUENCES("sequences"),
    @XmlEnumValue("classification")
    CLASSIFICATION("classification"),
    @XmlEnumValue("regression")
    REGRESSION("regression"),
    @XmlEnumValue("clustering")
    CLUSTERING("clustering"),
    @XmlEnumValue("timeSeries")
    TIME_SERIES("timeSeries"),
    @XmlEnumValue("mixed")
    MIXED("mixed");
    private final String value;

    MININGFUNCTION(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MININGFUNCTION fromValue(String v) {
        for (MININGFUNCTION c: MININGFUNCTION.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
