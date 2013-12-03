
package models.pmml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OPTYPE.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OPTYPE">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="categorical"/>
 *     &lt;enumeration value="ordinal"/>
 *     &lt;enumeration value="continuous"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "OPTYPE", namespace = "http://www.dmg.org/PMML-4_1")
@XmlEnum
public enum OPTYPE {

    @XmlEnumValue("categorical")
    CATEGORICAL("categorical"),
    @XmlEnumValue("ordinal")
    ORDINAL("ordinal"),
    @XmlEnumValue("continuous")
    CONTINUOUS("continuous");
    private final String value;

    OPTYPE(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OPTYPE fromValue(String v) {
        for (OPTYPE c: OPTYPE.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}