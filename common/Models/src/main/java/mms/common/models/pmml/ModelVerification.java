
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}VerificationFields"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}InlineTable"/>
 *       &lt;/sequence>
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
    "verificationFields",
    "inlineTable"
})
@XmlRootElement(name = "ModelVerification", namespace = "http://www.dmg.org/PMML-4_1")
public class ModelVerification {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "VerificationFields", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected VerificationFields verificationFields;
    @XmlElement(name = "InlineTable", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected InlineTable inlineTable;
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
     * Gets the value of the verificationFields property.
     * 
     * @return
     *     possible object is
     *     {@link VerificationFields }
     *     
     */
    public VerificationFields getVerificationFields() {
        return verificationFields;
    }

    /**
     * Sets the value of the verificationFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerificationFields }
     *     
     */
    public void setVerificationFields(VerificationFields value) {
        this.verificationFields = value;
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
