
package mms.common.models.pmml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for INTERPOLATION-METHOD.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="INTERPOLATION-METHOD">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="none"/>
 *     &lt;enumeration value="linear"/>
 *     &lt;enumeration value="exponentialSpline"/>
 *     &lt;enumeration value="cubicSpline"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "INTERPOLATION-METHOD", namespace = "http://www.dmg.org/PMML-4_1")
@XmlEnum
public enum INTERPOLATIONMETHOD {

    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("linear")
    LINEAR("linear"),
    @XmlEnumValue("exponentialSpline")
    EXPONENTIAL_SPLINE("exponentialSpline"),
    @XmlEnumValue("cubicSpline")
    CUBIC_SPLINE("cubicSpline");
    private final String value;

    INTERPOLATIONMETHOD(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static INTERPOLATIONMETHOD fromValue(String v) {
        for (INTERPOLATIONMETHOD c: INTERPOLATIONMETHOD.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
