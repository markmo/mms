
package models.pmml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BASELINE-TEST-STATISTIC.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BASELINE-TEST-STATISTIC">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="zValue"/>
 *     &lt;enumeration value="chiSquareIndependence"/>
 *     &lt;enumeration value="chiSquareDistribution"/>
 *     &lt;enumeration value="CUSUM"/>
 *     &lt;enumeration value="scalarProduct"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "BASELINE-TEST-STATISTIC", namespace = "http://www.dmg.org/PMML-4_1")
@XmlEnum
public enum BASELINETESTSTATISTIC {

    @XmlEnumValue("zValue")
    Z_VALUE("zValue"),
    @XmlEnumValue("chiSquareIndependence")
    CHI_SQUARE_INDEPENDENCE("chiSquareIndependence"),
    @XmlEnumValue("chiSquareDistribution")
    CHI_SQUARE_DISTRIBUTION("chiSquareDistribution"),
    CUSUM("CUSUM"),
    @XmlEnumValue("scalarProduct")
    SCALAR_PRODUCT("scalarProduct");
    private final String value;

    BASELINETESTSTATISTIC(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BASELINETESTSTATISTIC fromValue(String v) {
        for (BASELINETESTSTATISTIC c: BASELINETESTSTATISTIC.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
