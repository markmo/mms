
package mms.common.models.pmml;

import java.math.BigInteger;
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Coefficient" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="numberOfCoefficients" type="{http://www.dmg.org/PMML-4_1}INT-NUMBER" />
 *       &lt;attribute name="absoluteValue" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" default="0" />
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
    "coefficient"
})
@XmlRootElement(name = "Coefficients", namespace = "http://www.dmg.org/PMML-4_1")
public class Coefficients {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "Coefficient", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected List<Coefficient> coefficient;
    @XmlAttribute(name = "numberOfCoefficients")
    protected BigInteger numberOfCoefficients;
    @XmlAttribute(name = "absoluteValue")
    protected Double absoluteValue;

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
     * Gets the value of the coefficient property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the coefficient property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCoefficient().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Coefficient }
     * 
     * 
     */
    public List<Coefficient> getCoefficient() {
        if (coefficient == null) {
            coefficient = new ArrayList<Coefficient>();
        }
        return this.coefficient;
    }

    /**
     * Gets the value of the numberOfCoefficients property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfCoefficients() {
        return numberOfCoefficients;
    }

    /**
     * Sets the value of the numberOfCoefficients property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfCoefficients(BigInteger value) {
        this.numberOfCoefficients = value;
    }

    /**
     * Gets the value of the absoluteValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public double getAbsoluteValue() {
        if (absoluteValue == null) {
            return  0.0D;
        } else {
            return absoluteValue;
        }
    }

    /**
     * Sets the value of the absoluteValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAbsoluteValue(Double value) {
        this.absoluteValue = value;
    }

}
