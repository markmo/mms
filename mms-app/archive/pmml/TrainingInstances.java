
package models.pmml;

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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}InstanceFields"/>
 *         &lt;choice>
 *           &lt;element ref="{http://www.dmg.org/PMML-4_1}TableLocator"/>
 *           &lt;element ref="{http://www.dmg.org/PMML-4_1}InlineTable"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="isTransformed" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="recordCount" type="{http://www.dmg.org/PMML-4_1}INT-NUMBER" />
 *       &lt;attribute name="fieldCount" type="{http://www.dmg.org/PMML-4_1}INT-NUMBER" />
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
    "instanceFields",
    "tableLocator",
    "inlineTable"
})
@XmlRootElement(name = "TrainingInstances", namespace = "http://www.dmg.org/PMML-4_1")
public class TrainingInstances {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "InstanceFields", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected InstanceFields instanceFields;
    @XmlElement(name = "TableLocator", namespace = "http://www.dmg.org/PMML-4_1")
    protected TableLocator tableLocator;
    @XmlElement(name = "InlineTable", namespace = "http://www.dmg.org/PMML-4_1")
    protected InlineTable inlineTable;
    @XmlAttribute(name = "isTransformed")
    protected Boolean isTransformed;
    @XmlAttribute(name = "recordCount")
    protected BigInteger recordCount;
    @XmlAttribute(name = "fieldCount")
    protected BigInteger fieldCount;

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
     * Gets the value of the instanceFields property.
     *
     * @return
     *     possible object is
     *     {@link InstanceFields }
     *
     */
    public InstanceFields getInstanceFields() {
        return instanceFields;
    }

    /**
     * Sets the value of the instanceFields property.
     *
     * @param value
     *     allowed object is
     *     {@link InstanceFields }
     *
     */
    public void setInstanceFields(InstanceFields value) {
        this.instanceFields = value;
    }

    /**
     * Gets the value of the tableLocator property.
     *
     * @return
     *     possible object is
     *     {@link TableLocator }
     *
     */
    public TableLocator getTableLocator() {
        return tableLocator;
    }

    /**
     * Sets the value of the tableLocator property.
     *
     * @param value
     *     allowed object is
     *     {@link TableLocator }
     *
     */
    public void setTableLocator(TableLocator value) {
        this.tableLocator = value;
    }

    /**
     * Gets the value of the inlineTable property.
     *
     * @return
     *     possible object is
     *     {@link InlineTable }
     *
     */
    public InlineTable getInlineTable() {
        return inlineTable;
    }

    /**
     * Sets the value of the inlineTable property.
     *
     * @param value
     *     allowed object is
     *     {@link InlineTable }
     *
     */
    public void setInlineTable(InlineTable value) {
        this.inlineTable = value;
    }

    /**
     * Gets the value of the isTransformed property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public boolean isIsTransformed() {
        if (isTransformed == null) {
            return false;
        } else {
            return isTransformed;
        }
    }

    /**
     * Sets the value of the isTransformed property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setIsTransformed(Boolean value) {
        this.isTransformed = value;
    }

    /**
     * Gets the value of the recordCount property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getRecordCount() {
        return recordCount;
    }

    /**
     * Sets the value of the recordCount property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setRecordCount(BigInteger value) {
        this.recordCount = value;
    }

    /**
     * Gets the value of the fieldCount property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getFieldCount() {
        return fieldCount;
    }

    /**
     * Sets the value of the fieldCount property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setFieldCount(BigInteger value) {
        this.fieldCount = value;
    }

}
