
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
 *       &lt;/sequence>
 *       &lt;attribute name="field" use="required" type="{http://www.dmg.org/PMML-4_1}FIELD-NAME" />
 *       &lt;attribute name="fieldWeight" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" default="1" />
 *       &lt;attribute name="compareFunction" type="{http://www.dmg.org/PMML-4_1}COMPARE-FUNCTION" />
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
@XmlRootElement(name = "KNNInput", namespace = "http://www.dmg.org/PMML-4_1")
public class KNNInput {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlAttribute(name = "field", required = true)
    protected String field;
    @XmlAttribute(name = "fieldWeight")
    protected Double fieldWeight;
    @XmlAttribute(name = "compareFunction")
    protected COMPAREFUNCTION compareFunction;

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
     * Gets the value of the field property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField() {
        return field;
    }

    /**
     * Sets the value of the field property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField(String value) {
        this.field = value;
    }

    /**
     * Gets the value of the fieldWeight property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public double getFieldWeight() {
        if (fieldWeight == null) {
            return  1.0D;
        } else {
            return fieldWeight;
        }
    }

    /**
     * Sets the value of the fieldWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFieldWeight(Double value) {
        this.fieldWeight = value;
    }

    /**
     * Gets the value of the compareFunction property.
     * 
     * @return
     *     possible object is
     *     {@link COMPAREFUNCTION }
     *     
     */
    public COMPAREFUNCTION getCompareFunction() {
        return compareFunction;
    }

    /**
     * Sets the value of the compareFunction property.
     * 
     * @param value
     *     allowed object is
     *     {@link COMPAREFUNCTION }
     *     
     */
    public void setCompareFunction(COMPAREFUNCTION value) {
        this.compareFunction = value;
    }

}
