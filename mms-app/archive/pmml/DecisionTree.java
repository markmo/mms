
package models.pmml;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Output" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}ModelStats" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Targets" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}LocalTransformations" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}ResultField" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Node"/>
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
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "extension",
    "output",
    "modelStats",
    "targets",
    "localTransformations",
    "resultField",
    "node"
})
@XmlRootElement(name = "DecisionTree", namespace = "http://www.dmg.org/PMML-4_1")
public class DecisionTree {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "Output", namespace = "http://www.dmg.org/PMML-4_1")
    protected Output output;
    @XmlElement(name = "ModelStats", namespace = "http://www.dmg.org/PMML-4_1")
    protected ModelStats modelStats;
    @XmlElement(name = "Targets", namespace = "http://www.dmg.org/PMML-4_1")
    protected Targets targets;
    @XmlElement(name = "LocalTransformations", namespace = "http://www.dmg.org/PMML-4_1")
    protected LocalTransformations localTransformations;
    @XmlElement(name = "ResultField", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<ResultField> resultField;
    @XmlElement(name = "Node", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected Node node;
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

    /**
     * Gets the value of the extension property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extension property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtension().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Extension }
     *
     *
     */
    public List<Extension> getExtension() {
        if (extension == null) {
            extension = new ArrayList<Extension>();
        }
        return this.extension;
    }

    /**
     * Gets the value of the output property.
     *
     * @return
     *     possible object is
     *     {@link Output }
     *
     */
    public Output getOutput() {
        return output;
    }

    /**
     * Sets the value of the output property.
     *
     * @param value
     *     allowed object is
     *     {@link Output }
     *
     */
    public void setOutput(Output value) {
        this.output = value;
    }

    /**
     * Gets the value of the modelStats property.
     *
     * @return
     *     possible object is
     *     {@link ModelStats }
     *
     */
    public ModelStats getModelStats() {
        return modelStats;
    }

    /**
     * Sets the value of the modelStats property.
     *
     * @param value
     *     allowed object is
     *     {@link ModelStats }
     *
     */
    public void setModelStats(ModelStats value) {
        this.modelStats = value;
    }

    /**
     * Gets the value of the targets property.
     *
     * @return
     *     possible object is
     *     {@link Targets }
     *
     */
    public Targets getTargets() {
        return targets;
    }

    /**
     * Sets the value of the targets property.
     *
     * @param value
     *     allowed object is
     *     {@link Targets }
     *
     */
    public void setTargets(Targets value) {
        this.targets = value;
    }

    /**
     * Gets the value of the localTransformations property.
     *
     * @return
     *     possible object is
     *     {@link LocalTransformations }
     *
     */
    public LocalTransformations getLocalTransformations() {
        return localTransformations;
    }

    /**
     * Sets the value of the localTransformations property.
     *
     * @param value
     *     allowed object is
     *     {@link LocalTransformations }
     *
     */
    public void setLocalTransformations(LocalTransformations value) {
        this.localTransformations = value;
    }

    /**
     * Gets the value of the resultField property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resultField property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResultField().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResultField }
     *
     *
     */
    public List<ResultField> getResultField() {
        if (resultField == null) {
            resultField = new ArrayList<ResultField>();
        }
        return this.resultField;
    }

    /**
     * Gets the value of the node property.
     *
     * @return
     *     possible object is
     *     {@link Node }
     *
     */
    public Node getNode() {
        return node;
    }

    /**
     * Sets the value of the node property.
     *
     * @param value
     *     allowed object is
     *     {@link Node }
     *
     */
    public void setNode(Node value) {
        this.node = value;
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

}
