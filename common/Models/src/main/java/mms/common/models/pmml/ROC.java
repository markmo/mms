
package mms.common.models.pmml;

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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}ROCGraph"/>
 *       &lt;/sequence>
 *       &lt;attribute name="positiveTargetFieldValue" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="positiveTargetFieldDisplayValue" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="negativeTargetFieldValue" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="negativeTargetFieldDisplayValue" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "rocGraph"
})
@XmlRootElement(name = "ROC", namespace = "http://www.dmg.org/PMML-4_1")
public class ROC {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "ROCGraph", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected ROCGraph rocGraph;
    @XmlAttribute(name = "positiveTargetFieldValue", required = true)
    protected String positiveTargetFieldValue;
    @XmlAttribute(name = "positiveTargetFieldDisplayValue")
    protected String positiveTargetFieldDisplayValue;
    @XmlAttribute(name = "negativeTargetFieldValue")
    protected String negativeTargetFieldValue;
    @XmlAttribute(name = "negativeTargetFieldDisplayValue")
    protected String negativeTargetFieldDisplayValue;

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
     * Gets the value of the rocGraph property.
     * 
     * @return
     *     possible object is
     *     {@link ROCGraph }
     *     
     */
    public ROCGraph getROCGraph() {
        return rocGraph;
    }

    /**
     * Sets the value of the rocGraph property.
     * 
     * @param value
     *     allowed object is
     *     {@link ROCGraph }
     *     
     */
    public void setROCGraph(ROCGraph value) {
        this.rocGraph = value;
    }

    /**
     * Gets the value of the positiveTargetFieldValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPositiveTargetFieldValue() {
        return positiveTargetFieldValue;
    }

    /**
     * Sets the value of the positiveTargetFieldValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPositiveTargetFieldValue(String value) {
        this.positiveTargetFieldValue = value;
    }

    /**
     * Gets the value of the positiveTargetFieldDisplayValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPositiveTargetFieldDisplayValue() {
        return positiveTargetFieldDisplayValue;
    }

    /**
     * Sets the value of the positiveTargetFieldDisplayValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPositiveTargetFieldDisplayValue(String value) {
        this.positiveTargetFieldDisplayValue = value;
    }

    /**
     * Gets the value of the negativeTargetFieldValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNegativeTargetFieldValue() {
        return negativeTargetFieldValue;
    }

    /**
     * Sets the value of the negativeTargetFieldValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNegativeTargetFieldValue(String value) {
        this.negativeTargetFieldValue = value;
    }

    /**
     * Gets the value of the negativeTargetFieldDisplayValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNegativeTargetFieldDisplayValue() {
        return negativeTargetFieldDisplayValue;
    }

    /**
     * Sets the value of the negativeTargetFieldDisplayValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNegativeTargetFieldDisplayValue(String value) {
        this.negativeTargetFieldDisplayValue = value;
    }

}
