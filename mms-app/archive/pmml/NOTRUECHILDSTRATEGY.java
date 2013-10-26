
package models.pmml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NO-TRUE-CHILD-STRATEGY.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NO-TRUE-CHILD-STRATEGY">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="returnNullPrediction"/>
 *     &lt;enumeration value="returnLastPrediction"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "NO-TRUE-CHILD-STRATEGY", namespace = "http://www.dmg.org/PMML-4_1")
@XmlEnum
public enum NOTRUECHILDSTRATEGY {

    @XmlEnumValue("returnNullPrediction")
    RETURN_NULL_PREDICTION("returnNullPrediction"),
    @XmlEnumValue("returnLastPrediction")
    RETURN_LAST_PREDICTION("returnLastPrediction");
    private final String value;

    NOTRUECHILDSTRATEGY(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NOTRUECHILDSTRATEGY fromValue(String v) {
        for (NOTRUECHILDSTRATEGY c: NOTRUECHILDSTRATEGY.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
