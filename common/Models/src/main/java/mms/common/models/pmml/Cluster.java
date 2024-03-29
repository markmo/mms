
package mms.common.models.pmml;

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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}KohonenMap" minOccurs="0"/>
 *         &lt;group ref="{http://www.dmg.org/PMML-4_1}NUM-ARRAY" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Partition" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Covariances" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
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
    "kohonenMap",
    "array",
    "partition",
    "covariances"
})
@XmlRootElement(name = "Cluster", namespace = "http://www.dmg.org/PMML-4_1")
public class Cluster {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "KohonenMap", namespace = "http://www.dmg.org/PMML-4_1")
    protected KohonenMap kohonenMap;
    @XmlElement(name = "Array", namespace = "http://www.dmg.org/PMML-4_1")
    protected ArrayType array;
    @XmlElement(name = "Partition", namespace = "http://www.dmg.org/PMML-4_1")
    protected Partition partition;
    @XmlElement(name = "Covariances", namespace = "http://www.dmg.org/PMML-4_1")
    protected Covariances covariances;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "size")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger size;

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
     * Gets the value of the kohonenMap property.
     * 
     * @return
     *     possible object is
     *     {@link KohonenMap }
     *     
     */
    public KohonenMap getKohonenMap() {
        return kohonenMap;
    }

    /**
     * Sets the value of the kohonenMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link KohonenMap }
     *     
     */
    public void setKohonenMap(KohonenMap value) {
        this.kohonenMap = value;
    }

    /**
     * Gets the value of the array property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayType }
     *     
     */
    public ArrayType getArray() {
        return array;
    }

    /**
     * Sets the value of the array property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayType }
     *     
     */
    public void setArray(ArrayType value) {
        this.array = value;
    }

    /**
     * Gets the value of the partition property.
     * 
     * @return
     *     possible object is
     *     {@link Partition }
     *     
     */
    public Partition getPartition() {
        return partition;
    }

    /**
     * Sets the value of the partition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Partition }
     *     
     */
    public void setPartition(Partition value) {
        this.partition = value;
    }

    /**
     * Gets the value of the covariances property.
     * 
     * @return
     *     possible object is
     *     {@link Covariances }
     *     
     */
    public Covariances getCovariances() {
        return covariances;
    }

    /**
     * Sets the value of the covariances property.
     * 
     * @param value
     *     allowed object is
     *     {@link Covariances }
     *     
     */
    public void setCovariances(Covariances value) {
        this.covariances = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSize(BigInteger value) {
        this.size = value;
    }

}
