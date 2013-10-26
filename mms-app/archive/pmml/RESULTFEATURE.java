
package models.pmml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RESULT-FEATURE.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RESULT-FEATURE">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="predictedValue"/>
 *     &lt;enumeration value="predictedDisplayValue"/>
 *     &lt;enumeration value="transformedValue"/>
 *     &lt;enumeration value="decision"/>
 *     &lt;enumeration value="probability"/>
 *     &lt;enumeration value="affinity"/>
 *     &lt;enumeration value="residual"/>
 *     &lt;enumeration value="standardError"/>
 *     &lt;enumeration value="clusterId"/>
 *     &lt;enumeration value="clusterAffinity"/>
 *     &lt;enumeration value="entityId"/>
 *     &lt;enumeration value="entityAffinity"/>
 *     &lt;enumeration value="warning"/>
 *     &lt;enumeration value="ruleValue"/>
 *     &lt;enumeration value="reasonCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "RESULT-FEATURE", namespace = "http://www.dmg.org/PMML-4_1")
@XmlEnum
public enum RESULTFEATURE {

    @XmlEnumValue("predictedValue")
    PREDICTED_VALUE("predictedValue"),
    @XmlEnumValue("predictedDisplayValue")
    PREDICTED_DISPLAY_VALUE("predictedDisplayValue"),
    @XmlEnumValue("transformedValue")
    TRANSFORMED_VALUE("transformedValue"),
    @XmlEnumValue("decision")
    DECISION("decision"),
    @XmlEnumValue("probability")
    PROBABILITY("probability"),
    @XmlEnumValue("affinity")
    AFFINITY("affinity"),
    @XmlEnumValue("residual")
    RESIDUAL("residual"),
    @XmlEnumValue("standardError")
    STANDARD_ERROR("standardError"),
    @XmlEnumValue("clusterId")
    CLUSTER_ID("clusterId"),
    @XmlEnumValue("clusterAffinity")
    CLUSTER_AFFINITY("clusterAffinity"),
    @XmlEnumValue("entityId")
    ENTITY_ID("entityId"),
    @XmlEnumValue("entityAffinity")
    ENTITY_AFFINITY("entityAffinity"),
    @XmlEnumValue("warning")
    WARNING("warning"),
    @XmlEnumValue("ruleValue")
    RULE_VALUE("ruleValue"),
    @XmlEnumValue("reasonCode")
    REASON_CODE("reasonCode");
    private final String value;

    RESULTFEATURE(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RESULTFEATURE fromValue(String v) {
        for (RESULTFEATURE c: RESULTFEATURE.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
