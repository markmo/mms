
package models.pmml;

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
 *         &lt;choice>
 *           &lt;element ref="{http://www.dmg.org/PMML-4_1}FieldValue" maxOccurs="unbounded"/>
 *           &lt;element ref="{http://www.dmg.org/PMML-4_1}FieldValueCount" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="field" use="required" type="{http://www.dmg.org/PMML-4_1}FIELD-NAME" />
 *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
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
    "fieldValue",
    "fieldValueCount"
})
@XmlRootElement(name = "FieldValue", namespace = "http://www.dmg.org/PMML-4_1")
public class FieldValue {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "FieldValue", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<FieldValue> fieldValue;
    @XmlElement(name = "FieldValueCount", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<FieldValueCount> fieldValueCount;
    @XmlAttribute(name = "field", required = true)
    protected String field;
    @XmlAttribute(name = "value", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String value;

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
     * Gets the value of the fieldValue property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fieldValue property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFieldValue().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FieldValue }
     *
     *
     */
    public List<FieldValue> getFieldValue() {
        if (fieldValue == null) {
            fieldValue = new ArrayList<FieldValue>();
        }
        return this.fieldValue;
    }

    /**
     * Gets the value of the fieldValueCount property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fieldValueCount property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFieldValueCount().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FieldValueCount }
     *
     *
     */
    public List<FieldValueCount> getFieldValueCount() {
        if (fieldValueCount == null) {
            fieldValueCount = new ArrayList<FieldValueCount>();
        }
        return this.fieldValueCount;
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

}
