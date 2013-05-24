
package mms.common.models.pmml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for REGRESSIONNORMALIZATIONMETHOD.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="REGRESSIONNORMALIZATIONMETHOD">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="none"/>
 *     &lt;enumeration value="simplemax"/>
 *     &lt;enumeration value="softmax"/>
 *     &lt;enumeration value="logit"/>
 *     &lt;enumeration value="probit"/>
 *     &lt;enumeration value="cloglog"/>
 *     &lt;enumeration value="exp"/>
 *     &lt;enumeration value="loglog"/>
 *     &lt;enumeration value="cauchit"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "REGRESSIONNORMALIZATIONMETHOD", namespace = "http://www.dmg.org/PMML-4_1")
@XmlEnum
public enum REGRESSIONNORMALIZATIONMETHOD {

    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("simplemax")
    SIMPLEMAX("simplemax"),
    @XmlEnumValue("softmax")
    SOFTMAX("softmax"),
    @XmlEnumValue("logit")
    LOGIT("logit"),
    @XmlEnumValue("probit")
    PROBIT("probit"),
    @XmlEnumValue("cloglog")
    CLOGLOG("cloglog"),
    @XmlEnumValue("exp")
    EXP("exp"),
    @XmlEnumValue("loglog")
    LOGLOG("loglog"),
    @XmlEnumValue("cauchit")
    CAUCHIT("cauchit");
    private final String value;

    REGRESSIONNORMALIZATIONMETHOD(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static REGRESSIONNORMALIZATIONMETHOD fromValue(String v) {
        for (REGRESSIONNORMALIZATIONMETHOD c: REGRESSIONNORMALIZATIONMETHOD.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
