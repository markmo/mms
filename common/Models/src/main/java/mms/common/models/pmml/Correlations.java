
package mms.common.models.pmml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}CorrelationFields"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}CorrelationValues"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}CorrelationMethods" minOccurs="0"/>
 *       &lt;/sequence>
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
    "correlationFields",
    "correlationValues",
    "correlationMethods"
})
@XmlRootElement(name = "Correlations", namespace = "http://www.dmg.org/PMML-4_1")
public class Correlations {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "CorrelationFields", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected CorrelationFields correlationFields;
    @XmlElement(name = "CorrelationValues", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected CorrelationValues correlationValues;
    @XmlElement(name = "CorrelationMethods", namespace = "http://www.dmg.org/PMML-4_1")
    protected CorrelationMethods correlationMethods;

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
     * Gets the value of the correlationFields property.
     * 
     * @return
     *     possible object is
     *     {@link CorrelationFields }
     *     
     */
    public CorrelationFields getCorrelationFields() {
        return correlationFields;
    }

    /**
     * Sets the value of the correlationFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorrelationFields }
     *     
     */
    public void setCorrelationFields(CorrelationFields value) {
        this.correlationFields = value;
    }

    /**
     * Gets the value of the correlationValues property.
     * 
     * @return
     *     possible object is
     *     {@link CorrelationValues }
     *     
     */
    public CorrelationValues getCorrelationValues() {
        return correlationValues;
    }

    /**
     * Sets the value of the correlationValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorrelationValues }
     *     
     */
    public void setCorrelationValues(CorrelationValues value) {
        this.correlationValues = value;
    }

    /**
     * Gets the value of the correlationMethods property.
     * 
     * @return
     *     possible object is
     *     {@link CorrelationMethods }
     *     
     */
    public CorrelationMethods getCorrelationMethods() {
        return correlationMethods;
    }

    /**
     * Sets the value of the correlationMethods property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorrelationMethods }
     *     
     */
    public void setCorrelationMethods(CorrelationMethods value) {
        this.correlationMethods = value;
    }

}
