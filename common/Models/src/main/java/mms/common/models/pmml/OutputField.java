
package mms.common.models.pmml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;sequence minOccurs="0">
 *           &lt;element ref="{http://www.dmg.org/PMML-4_1}Decisions" minOccurs="0"/>
 *           &lt;group ref="{http://www.dmg.org/PMML-4_1}EXPRESSION"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.dmg.org/PMML-4_1}FIELD-NAME" />
 *       &lt;attribute name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="optype" type="{http://www.dmg.org/PMML-4_1}OPTYPE" />
 *       &lt;attribute name="dataType" type="{http://www.dmg.org/PMML-4_1}DATATYPE" />
 *       &lt;attribute name="targetField" type="{http://www.dmg.org/PMML-4_1}FIELD-NAME" />
 *       &lt;attribute name="feature" type="{http://www.dmg.org/PMML-4_1}RESULT-FEATURE" />
 *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ruleFeature" type="{http://www.dmg.org/PMML-4_1}RULE-FEATURE" default="consequent" />
 *       &lt;attribute name="algorithm" default="exclusiveRecommendation">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="recommendation"/>
 *             &lt;enumeration value="exclusiveRecommendation"/>
 *             &lt;enumeration value="ruleAssociation"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="rank" type="{http://www.dmg.org/PMML-4_1}INT-NUMBER" default="1" />
 *       &lt;attribute name="rankBasis" default="confidence">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="confidence"/>
 *             &lt;enumeration value="support"/>
 *             &lt;enumeration value="lift"/>
 *             &lt;enumeration value="leverage"/>
 *             &lt;enumeration value="affinity"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="rankOrder" default="descending">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="descending"/>
 *             &lt;enumeration value="ascending"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="isMultiValued" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" default="0" />
 *       &lt;attribute name="segmentId" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "decisions",
    "constant",
    "fieldRef",
    "normContinuous",
    "normDiscrete",
    "discretize",
    "mapValues",
    "apply",
    "aggregate"
})
@XmlRootElement(name = "OutputField", namespace = "http://www.dmg.org/PMML-4_1")
public class OutputField {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "Decisions", namespace = "http://www.dmg.org/PMML-4_1")
    protected Decisions decisions;
    @XmlElement(name = "Constant", namespace = "http://www.dmg.org/PMML-4_1")
    protected Constant constant;
    @XmlElement(name = "FieldRef", namespace = "http://www.dmg.org/PMML-4_1")
    protected FieldRef fieldRef;
    @XmlElement(name = "NormContinuous", namespace = "http://www.dmg.org/PMML-4_1")
    protected NormContinuous normContinuous;
    @XmlElement(name = "NormDiscrete", namespace = "http://www.dmg.org/PMML-4_1")
    protected NormDiscrete normDiscrete;
    @XmlElement(name = "Discretize", namespace = "http://www.dmg.org/PMML-4_1")
    protected Discretize discretize;
    @XmlElement(name = "MapValues", namespace = "http://www.dmg.org/PMML-4_1")
    protected MapValues mapValues;
    @XmlElement(name = "Apply", namespace = "http://www.dmg.org/PMML-4_1")
    protected Apply apply;
    @XmlElement(name = "Aggregate", namespace = "http://www.dmg.org/PMML-4_1")
    protected Aggregate aggregate;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "displayName")
    protected String displayName;
    @XmlAttribute(name = "optype")
    protected OPTYPE optype;
    @XmlAttribute(name = "dataType")
    protected DATATYPE dataType;
    @XmlAttribute(name = "targetField")
    protected String targetField;
    @XmlAttribute(name = "feature")
    protected RESULTFEATURE feature;
    @XmlAttribute(name = "value")
    protected String value;
    @XmlAttribute(name = "ruleFeature")
    protected RULEFEATURE ruleFeature;
    @XmlAttribute(name = "algorithm")
    protected String algorithm;
    @XmlAttribute(name = "rank")
    protected BigInteger rank;
    @XmlAttribute(name = "rankBasis")
    protected String rankBasis;
    @XmlAttribute(name = "rankOrder")
    protected String rankOrder;
    @XmlAttribute(name = "isMultiValued")
    @XmlSchemaType(name = "anySimpleType")
    protected String isMultiValued;
    @XmlAttribute(name = "segmentId")
    protected String segmentId;

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
     * Gets the value of the decisions property.
     * 
     * @return
     *     possible object is
     *     {@link Decisions }
     *     
     */
    public Decisions getDecisions() {
        return decisions;
    }

    /**
     * Sets the value of the decisions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Decisions }
     *     
     */
    public void setDecisions(Decisions value) {
        this.decisions = value;
    }

    /**
     * Gets the value of the constant property.
     * 
     * @return
     *     possible object is
     *     {@link Constant }
     *     
     */
    public Constant getConstant() {
        return constant;
    }

    /**
     * Sets the value of the constant property.
     * 
     * @param value
     *     allowed object is
     *     {@link Constant }
     *     
     */
    public void setConstant(Constant value) {
        this.constant = value;
    }

    /**
     * Gets the value of the fieldRef property.
     * 
     * @return
     *     possible object is
     *     {@link FieldRef }
     *     
     */
    public FieldRef getFieldRef() {
        return fieldRef;
    }

    /**
     * Sets the value of the fieldRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldRef }
     *     
     */
    public void setFieldRef(FieldRef value) {
        this.fieldRef = value;
    }

    /**
     * Gets the value of the normContinuous property.
     * 
     * @return
     *     possible object is
     *     {@link NormContinuous }
     *     
     */
    public NormContinuous getNormContinuous() {
        return normContinuous;
    }

    /**
     * Sets the value of the normContinuous property.
     * 
     * @param value
     *     allowed object is
     *     {@link NormContinuous }
     *     
     */
    public void setNormContinuous(NormContinuous value) {
        this.normContinuous = value;
    }

    /**
     * Gets the value of the normDiscrete property.
     * 
     * @return
     *     possible object is
     *     {@link NormDiscrete }
     *     
     */
    public NormDiscrete getNormDiscrete() {
        return normDiscrete;
    }

    /**
     * Sets the value of the normDiscrete property.
     * 
     * @param value
     *     allowed object is
     *     {@link NormDiscrete }
     *     
     */
    public void setNormDiscrete(NormDiscrete value) {
        this.normDiscrete = value;
    }

    /**
     * Gets the value of the discretize property.
     * 
     * @return
     *     possible object is
     *     {@link Discretize }
     *     
     */
    public Discretize getDiscretize() {
        return discretize;
    }

    /**
     * Sets the value of the discretize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Discretize }
     *     
     */
    public void setDiscretize(Discretize value) {
        this.discretize = value;
    }

    /**
     * Gets the value of the mapValues property.
     * 
     * @return
     *     possible object is
     *     {@link MapValues }
     *     
     */
    public MapValues getMapValues() {
        return mapValues;
    }

    /**
     * Sets the value of the mapValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link MapValues }
     *     
     */
    public void setMapValues(MapValues value) {
        this.mapValues = value;
    }

    /**
     * Gets the value of the apply property.
     * 
     * @return
     *     possible object is
     *     {@link Apply }
     *     
     */
    public Apply getApply() {
        return apply;
    }

    /**
     * Sets the value of the apply property.
     * 
     * @param value
     *     allowed object is
     *     {@link Apply }
     *     
     */
    public void setApply(Apply value) {
        this.apply = value;
    }

    /**
     * Gets the value of the aggregate property.
     * 
     * @return
     *     possible object is
     *     {@link Aggregate }
     *     
     */
    public Aggregate getAggregate() {
        return aggregate;
    }

    /**
     * Sets the value of the aggregate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Aggregate }
     *     
     */
    public void setAggregate(Aggregate value) {
        this.aggregate = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the optype property.
     * 
     * @return
     *     possible object is
     *     {@link OPTYPE }
     *     
     */
    public OPTYPE getOptype() {
        return optype;
    }

    /**
     * Sets the value of the optype property.
     * 
     * @param value
     *     allowed object is
     *     {@link OPTYPE }
     *     
     */
    public void setOptype(OPTYPE value) {
        this.optype = value;
    }

    /**
     * Gets the value of the dataType property.
     * 
     * @return
     *     possible object is
     *     {@link DATATYPE }
     *     
     */
    public DATATYPE getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DATATYPE }
     *     
     */
    public void setDataType(DATATYPE value) {
        this.dataType = value;
    }

    /**
     * Gets the value of the targetField property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetField() {
        return targetField;
    }

    /**
     * Sets the value of the targetField property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetField(String value) {
        this.targetField = value;
    }

    /**
     * Gets the value of the feature property.
     * 
     * @return
     *     possible object is
     *     {@link RESULTFEATURE }
     *     
     */
    public RESULTFEATURE getFeature() {
        return feature;
    }

    /**
     * Sets the value of the feature property.
     * 
     * @param value
     *     allowed object is
     *     {@link RESULTFEATURE }
     *     
     */
    public void setFeature(RESULTFEATURE value) {
        this.feature = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the ruleFeature property.
     * 
     * @return
     *     possible object is
     *     {@link RULEFEATURE }
     *     
     */
    public RULEFEATURE getRuleFeature() {
        if (ruleFeature == null) {
            return RULEFEATURE.CONSEQUENT;
        } else {
            return ruleFeature;
        }
    }

    /**
     * Sets the value of the ruleFeature property.
     * 
     * @param value
     *     allowed object is
     *     {@link RULEFEATURE }
     *     
     */
    public void setRuleFeature(RULEFEATURE value) {
        this.ruleFeature = value;
    }

    /**
     * Gets the value of the algorithm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlgorithm() {
        if (algorithm == null) {
            return "exclusiveRecommendation";
        } else {
            return algorithm;
        }
    }

    /**
     * Sets the value of the algorithm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlgorithm(String value) {
        this.algorithm = value;
    }

    /**
     * Gets the value of the rank property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRank() {
        if (rank == null) {
            return new BigInteger("1");
        } else {
            return rank;
        }
    }

    /**
     * Sets the value of the rank property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRank(BigInteger value) {
        this.rank = value;
    }

    /**
     * Gets the value of the rankBasis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRankBasis() {
        if (rankBasis == null) {
            return "confidence";
        } else {
            return rankBasis;
        }
    }

    /**
     * Sets the value of the rankBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRankBasis(String value) {
        this.rankBasis = value;
    }

    /**
     * Gets the value of the rankOrder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRankOrder() {
        if (rankOrder == null) {
            return "descending";
        } else {
            return rankOrder;
        }
    }

    /**
     * Sets the value of the rankOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRankOrder(String value) {
        this.rankOrder = value;
    }

    /**
     * Gets the value of the isMultiValued property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsMultiValued() {
        if (isMultiValued == null) {
            return "0";
        } else {
            return isMultiValued;
        }
    }

    /**
     * Sets the value of the isMultiValued property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsMultiValued(String value) {
        this.isMultiValued = value;
    }

    /**
     * Gets the value of the segmentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentId() {
        return segmentId;
    }

    /**
     * Sets the value of the segmentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentId(String value) {
        this.segmentId = value;
    }

}
