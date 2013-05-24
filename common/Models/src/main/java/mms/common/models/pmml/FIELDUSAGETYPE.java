
package mms.common.models.pmml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FIELD-USAGE-TYPE.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FIELD-USAGE-TYPE">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="active"/>
 *     &lt;enumeration value="predicted"/>
 *     &lt;enumeration value="supplementary"/>
 *     &lt;enumeration value="group"/>
 *     &lt;enumeration value="order"/>
 *     &lt;enumeration value="frequencyWeight"/>
 *     &lt;enumeration value="analysisWeight"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FIELD-USAGE-TYPE", namespace = "http://www.dmg.org/PMML-4_1")
@XmlEnum
public enum FIELDUSAGETYPE {

    @XmlEnumValue("active")
    ACTIVE("active"),
    @XmlEnumValue("predicted")
    PREDICTED("predicted"),
    @XmlEnumValue("supplementary")
    SUPPLEMENTARY("supplementary"),
    @XmlEnumValue("group")
    GROUP("group"),
    @XmlEnumValue("order")
    ORDER("order"),
    @XmlEnumValue("frequencyWeight")
    FREQUENCY_WEIGHT("frequencyWeight"),
    @XmlEnumValue("analysisWeight")
    ANALYSIS_WEIGHT("analysisWeight");
    private final String value;

    FIELDUSAGETYPE(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FIELDUSAGETYPE fromValue(String v) {
        for (FIELDUSAGETYPE c: FIELDUSAGETYPE.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
