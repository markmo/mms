
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}DataField" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Taxonomy" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="numberOfFields" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
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
    "dataField",
    "taxonomy"
})
@XmlRootElement(name = "DataDictionary", namespace = "http://www.dmg.org/PMML-4_1")
public class DataDictionary {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "DataField", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected List<DataField> dataField;
    @XmlElement(name = "Taxonomy", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Taxonomy> taxonomy;
    @XmlAttribute(name = "numberOfFields")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger numberOfFields;

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
     * Gets the value of the dataField property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataField property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataField().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataField }
     *
     *
     */
    public List<DataField> getDataField() {
        if (dataField == null) {
            dataField = new ArrayList<DataField>();
        }
        return this.dataField;
    }

    /**
     * Gets the value of the taxonomy property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taxonomy property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaxonomy().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Taxonomy }
     *
     *
     */
    public List<Taxonomy> getTaxonomy() {
        if (taxonomy == null) {
            taxonomy = new ArrayList<Taxonomy>();
        }
        return this.taxonomy;
    }

    /**
     * Gets the value of the numberOfFields property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getNumberOfFields() {
        return numberOfFields;
    }

    /**
     * Sets the value of the numberOfFields property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setNumberOfFields(BigInteger value) {
        this.numberOfFields = value;
    }

}
