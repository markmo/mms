
package models.pmml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TIME-EXCEPTION-TYPE.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TIME-EXCEPTION-TYPE">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="exclude"/>
 *     &lt;enumeration value="include"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "TIME-EXCEPTION-TYPE", namespace = "http://www.dmg.org/PMML-4_1")
@XmlEnum
public enum TIMEEXCEPTIONTYPE {

    @XmlEnumValue("exclude")
    EXCLUDE("exclude"),
    @XmlEnumValue("include")
    INCLUDE("include");
    private final String value;

    TIMEEXCEPTIONTYPE(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TIMEEXCEPTIONTYPE fromValue(String v) {
        for (TIMEEXCEPTIONTYPE c: TIMEEXCEPTIONTYPE.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
