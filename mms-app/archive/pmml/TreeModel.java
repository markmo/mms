
package models.pmml;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Extension" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}MiningSchema"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Output" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}ModelStats" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}ModelExplanation" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Targets" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}LocalTransformations" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Node"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}ModelVerification" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Extension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="modelName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="functionName" use="required" type="{http://www.dmg.org/PMML-4_1}MINING-FUNCTION" />
 *       &lt;attribute name="algorithmName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="missingValueStrategy" type="{http://www.dmg.org/PMML-4_1}MISSING-VALUE-STRATEGY" default="none" />
 *       &lt;attribute name="missingValuePenalty" type="{http://www.dmg.org/PMML-4_1}PROB-NUMBER" default="1.0" />
 *       &lt;attribute name="noTrueChildStrategy" type="{http://www.dmg.org/PMML-4_1}NO-TRUE-CHILD-STRATEGY" default="returnNullPrediction" />
 *       &lt;attribute name="splitCharacteristic" default="multiSplit">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="binarySplit"/>
 *             &lt;enumeration value="multiSplit"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="isScorable" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "content"
})
@XmlRootElement(name = "TreeModel", namespace = "http://www.dmg.org/PMML-4_1")
public class TreeModel {

    @XmlElementRefs({
        @XmlElementRef(name = "ModelVerification", namespace = "http://www.dmg.org/PMML-4_1", type = ModelVerification.class, required = false),
        @XmlElementRef(name = "LocalTransformations", namespace = "http://www.dmg.org/PMML-4_1", type = LocalTransformations.class, required = false),
        @XmlElementRef(name = "Output", namespace = "http://www.dmg.org/PMML-4_1", type = Output.class, required = false),
        @XmlElementRef(name = "MiningSchema", namespace = "http://www.dmg.org/PMML-4_1", type = MiningSchema.class, required = false),
        @XmlElementRef(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1", type = Extension.class, required = false),
        @XmlElementRef(name = "Node", namespace = "http://www.dmg.org/PMML-4_1", type = Node.class, required = false),
        @XmlElementRef(name = "ModelExplanation", namespace = "http://www.dmg.org/PMML-4_1", type = ModelExplanation.class, required = false),
        @XmlElementRef(name = "Targets", namespace = "http://www.dmg.org/PMML-4_1", type = Targets.class, required = false),
        @XmlElementRef(name = "ModelStats", namespace = "http://www.dmg.org/PMML-4_1", type = ModelStats.class, required = false)
    })
    protected List<Object> content;
    @XmlAttribute(name = "modelName")
    protected String modelName;
    @XmlAttribute(name = "functionName", required = true)
    protected MININGFUNCTION functionName;
    @XmlAttribute(name = "algorithmName")
    protected String algorithmName;
    @XmlAttribute(name = "missingValueStrategy")
    protected MISSINGVALUESTRATEGY missingValueStrategy;
    @XmlAttribute(name = "missingValuePenalty")
    protected BigDecimal missingValuePenalty;
    @XmlAttribute(name = "noTrueChildStrategy")
    protected NOTRUECHILDSTRATEGY noTrueChildStrategy;
    @XmlAttribute(name = "splitCharacteristic")
    protected String splitCharacteristic;
    @XmlAttribute(name = "isScorable")
    protected Boolean isScorable;

    /**
     * Gets the rest of the content model.
     *
     * <p>
     * You are getting this "catch-all" property because of the following reason:
     * The field name "Extension" is used by two different parts of a schema. See:
     * line 3074 of file:/Users/markmo/src/mms/mms-app/schema/pmml-4-1.xsd
     * line 3065 of file:/Users/markmo/src/mms/mms-app/schema/pmml-4-1.xsd
     * <p>
     * To get rid of this property, apply a property customization to one
     * of both of the following declarations to change their names:
     * Gets the value of the content property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ModelVerification }
     * {@link LocalTransformations }
     * {@link Output }
     * {@link Extension }
     * {@link MiningSchema }
     * {@link ModelExplanation }
     * {@link Node }
     * {@link ModelStats }
     * {@link Targets }
     *
     *
     */
    public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
    }

    /**
     * Gets the value of the modelName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Sets the value of the modelName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setModelName(String value) {
        this.modelName = value;
    }

    /**
     * Gets the value of the functionName property.
     *
     * @return
     *     possible object is
     *     {@link MININGFUNCTION }
     *
     */
    public MININGFUNCTION getFunctionName() {
        return functionName;
    }

    /**
     * Sets the value of the functionName property.
     *
     * @param value
     *     allowed object is
     *     {@link MININGFUNCTION }
     *
     */
    public void setFunctionName(MININGFUNCTION value) {
        this.functionName = value;
    }

    /**
     * Gets the value of the algorithmName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /**
     * Sets the value of the algorithmName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAlgorithmName(String value) {
        this.algorithmName = value;
    }

    /**
     * Gets the value of the missingValueStrategy property.
     *
     * @return
     *     possible object is
     *     {@link MISSINGVALUESTRATEGY }
     *
     */
    public MISSINGVALUESTRATEGY getMissingValueStrategy() {
        if (missingValueStrategy == null) {
            return MISSINGVALUESTRATEGY.NONE;
        } else {
            return missingValueStrategy;
        }
    }

    /**
     * Sets the value of the missingValueStrategy property.
     *
     * @param value
     *     allowed object is
     *     {@link MISSINGVALUESTRATEGY }
     *
     */
    public void setMissingValueStrategy(MISSINGVALUESTRATEGY value) {
        this.missingValueStrategy = value;
    }

    /**
     * Gets the value of the missingValuePenalty property.
     *
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *
     */
    public BigDecimal getMissingValuePenalty() {
        if (missingValuePenalty == null) {
            return new BigDecimal("1.0");
        } else {
            return missingValuePenalty;
        }
    }

    /**
     * Sets the value of the missingValuePenalty property.
     *
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *
     */
    public void setMissingValuePenalty(BigDecimal value) {
        this.missingValuePenalty = value;
    }

    /**
     * Gets the value of the noTrueChildStrategy property.
     *
     * @return
     *     possible object is
     *     {@link NOTRUECHILDSTRATEGY }
     *
     */
    public NOTRUECHILDSTRATEGY getNoTrueChildStrategy() {
        if (noTrueChildStrategy == null) {
            return NOTRUECHILDSTRATEGY.RETURN_NULL_PREDICTION;
        } else {
            return noTrueChildStrategy;
        }
    }

    /**
     * Sets the value of the noTrueChildStrategy property.
     *
     * @param value
     *     allowed object is
     *     {@link NOTRUECHILDSTRATEGY }
     *
     */
    public void setNoTrueChildStrategy(NOTRUECHILDSTRATEGY value) {
        this.noTrueChildStrategy = value;
    }

    /**
     * Gets the value of the splitCharacteristic property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSplitCharacteristic() {
        if (splitCharacteristic == null) {
            return "multiSplit";
        } else {
            return splitCharacteristic;
        }
    }

    /**
     * Sets the value of the splitCharacteristic property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSplitCharacteristic(String value) {
        this.splitCharacteristic = value;
    }

    /**
     * Gets the value of the isScorable property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public boolean isIsScorable() {
        if (isScorable == null) {
            return true;
        } else {
            return isScorable;
        }
    }

    /**
     * Sets the value of the isScorable property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setIsScorable(Boolean value) {
        this.isScorable = value;
    }

}
