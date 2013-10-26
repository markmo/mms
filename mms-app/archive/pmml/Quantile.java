
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
 *       &lt;attribute name="quantileLimit" use="required" type="{http://www.dmg.org/PMML-4_1}PERCENTAGE-NUMBER" />
 *       &lt;attribute name="quantileValue" use="required" type="{http://www.dmg.org/PMML-4_1}NUMBER" />
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
@XmlRootElement(name = "Quantile", namespace = "http://www.dmg.org/PMML-4_1")
public class Quantile {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlAttribute(name = "quantileLimit", required = true)
    protected BigDecimal quantileLimit;
    @XmlAttribute(name = "quantileValue", required = true)
    protected double quantileValue;

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
     * Gets the value of the quantileLimit property.
     *
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *
     */
    public BigDecimal getQuantileLimit() {
        return quantileLimit;
    }

    /**
     * Sets the value of the quantileLimit property.
     *
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *
     */
    public void setQuantileLimit(BigDecimal value) {
        this.quantileLimit = value;
    }

    /**
     * Gets the value of the quantileValue property.
     *
     */
    public double getQuantileValue() {
        return quantileValue;
    }

    /**
     * Sets the value of the quantileValue property.
     *
     */
    public void setQuantileValue(double value) {
        this.quantileValue = value;
    }

}
