
package models.pmml;

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
 *       &lt;/sequence>
 *       &lt;attribute name="totalFreq" use="required" type="{http://www.dmg.org/PMML-4_1}NUMBER" />
 *       &lt;attribute name="missingFreq" type="{http://www.dmg.org/PMML-4_1}NUMBER" />
 *       &lt;attribute name="invalidFreq" type="{http://www.dmg.org/PMML-4_1}NUMBER" />
 *       &lt;attribute name="cardinality" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
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
@XmlRootElement(name = "Counts", namespace = "http://www.dmg.org/PMML-4_1")
public class Counts {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlAttribute(name = "totalFreq", required = true)
    protected double totalFreq;
    @XmlAttribute(name = "missingFreq")
    protected Double missingFreq;
    @XmlAttribute(name = "invalidFreq")
    protected Double invalidFreq;
    @XmlAttribute(name = "cardinality")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger cardinality;

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
     * Gets the value of the totalFreq property.
     *
     */
    public double getTotalFreq() {
        return totalFreq;
    }

    /**
     * Sets the value of the totalFreq property.
     *
     */
    public void setTotalFreq(double value) {
        this.totalFreq = value;
    }

    /**
     * Gets the value of the missingFreq property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     *
     */
    public Double getMissingFreq() {
        return missingFreq;
    }

    /**
     * Sets the value of the missingFreq property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
    public void setMissingFreq(Double value) {
        this.missingFreq = value;
    }

    /**
     * Gets the value of the invalidFreq property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     *
     */
    public Double getInvalidFreq() {
        return invalidFreq;
    }

    /**
     * Sets the value of the invalidFreq property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
    public void setInvalidFreq(Double value) {
        this.invalidFreq = value;
    }

    /**
     * Gets the value of the cardinality property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getCardinality() {
        return cardinality;
    }

    /**
     * Sets the value of the cardinality property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setCardinality(BigInteger value) {
        this.cardinality = value;
    }

}