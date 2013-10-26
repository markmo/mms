
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
 *       &lt;/sequence>
 *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="recordCount" use="required" type="{http://www.dmg.org/PMML-4_1}NUMBER" />
 *       &lt;attribute name="confidence" type="{http://www.dmg.org/PMML-4_1}PROB-NUMBER" />
 *       &lt;attribute name="probability" type="{http://www.dmg.org/PMML-4_1}PROB-NUMBER" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "extension"
})
@XmlRootElement(name = "ScoreDistribution", namespace = "http://www.dmg.org/PMML-4_1")
public class ScoreDistribution {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlAttribute(name = "value", required = true)
    protected String value;
    @XmlAttribute(name = "recordCount", required = true)
    protected double recordCount;
    @XmlAttribute(name = "confidence")
    protected BigDecimal confidence;
    @XmlAttribute(name = "probability")
    protected BigDecimal probability;

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
     * Gets the value of the recordCount property.
     *
     */
    public double getRecordCount() {
        return recordCount;
    }

    /**
     * Sets the value of the recordCount property.
     *
     */
    public void setRecordCount(double value) {
        this.recordCount = value;
    }

    /**
     * Gets the value of the confidence property.
     *
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *
     */
    public BigDecimal getConfidence() {
        return confidence;
    }

    /**
     * Sets the value of the confidence property.
     *
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *
     */
    public void setConfidence(BigDecimal value) {
        this.confidence = value;
    }

    /**
     * Gets the value of the probability property.
     *
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *
     */
    public BigDecimal getProbability() {
        return probability;
    }

    /**
     * Sets the value of the probability property.
     *
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *
     */
    public void setProbability(BigDecimal value) {
        this.probability = value;
    }

}
