
package models.pmml;

import java.math.BigInteger;
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}AntecedentSequence"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Delimiter"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Time" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}ConsequentSequence"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Time" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.dmg.org/PMML-4_1}ELEMENT-ID" />
 *       &lt;attribute name="numberOfSets" use="required" type="{http://www.dmg.org/PMML-4_1}INT-NUMBER" />
 *       &lt;attribute name="occurrence" use="required" type="{http://www.dmg.org/PMML-4_1}INT-NUMBER" />
 *       &lt;attribute name="support" use="required" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
 *       &lt;attribute name="confidence" use="required" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
 *       &lt;attribute name="lift" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
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
@XmlRootElement(name = "SequenceRule", namespace = "http://www.dmg.org/PMML-4_1")
public class SequenceRule {

    @XmlElementRefs({
        @XmlElementRef(name = "ConsequentSequence", namespace = "http://www.dmg.org/PMML-4_1", type = ConsequentSequence.class, required = false),
        @XmlElementRef(name = "Time", namespace = "http://www.dmg.org/PMML-4_1", type = Time.class, required = false),
        @XmlElementRef(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1", type = Extension.class, required = false),
        @XmlElementRef(name = "Delimiter", namespace = "http://www.dmg.org/PMML-4_1", type = Delimiter.class, required = false),
        @XmlElementRef(name = "AntecedentSequence", namespace = "http://www.dmg.org/PMML-4_1", type = AntecedentSequence.class, required = false)
    })
    protected List<Object> content;
    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "numberOfSets", required = true)
    protected BigInteger numberOfSets;
    @XmlAttribute(name = "occurrence", required = true)
    protected BigInteger occurrence;
    @XmlAttribute(name = "support", required = true)
    protected double support;
    @XmlAttribute(name = "confidence", required = true)
    protected double confidence;
    @XmlAttribute(name = "lift")
    protected Double lift;

    /**
     * Gets the rest of the content model.
     *
     * <p>
     * You are getting this "catch-all" property because of the following reason:
     * The field name "Time" is used by two different parts of a schema. See:
     * line 2355 of file:/Users/markmo/src/mms/mms-app/schema/pmml-4-1.xsd
     * line 2353 of file:/Users/markmo/src/mms/mms-app/schema/pmml-4-1.xsd
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
     * {@link ConsequentSequence }
     * {@link Extension }
     * {@link Delimiter }
     * {@link AntecedentSequence }
     * {@link Time }
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
     * Gets the value of the id property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the numberOfSets property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getNumberOfSets() {
        return numberOfSets;
    }

    /**
     * Sets the value of the numberOfSets property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setNumberOfSets(BigInteger value) {
        this.numberOfSets = value;
    }

    /**
     * Gets the value of the occurrence property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getOccurrence() {
        return occurrence;
    }

    /**
     * Sets the value of the occurrence property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setOccurrence(BigInteger value) {
        this.occurrence = value;
    }

    /**
     * Gets the value of the support property.
     *
     */
    public double getSupport() {
        return support;
    }

    /**
     * Sets the value of the support property.
     *
     */
    public void setSupport(double value) {
        this.support = value;
    }

    /**
     * Gets the value of the confidence property.
     *
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * Sets the value of the confidence property.
     *
     */
    public void setConfidence(double value) {
        this.confidence = value;
    }

    /**
     * Gets the value of the lift property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     *
     */
    public Double getLift() {
        return lift;
    }

    /**
     * Sets the value of the lift property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
    public void setLift(Double value) {
        this.lift = value;
    }

}
