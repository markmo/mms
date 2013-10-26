
package models.pmml;

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
 *       &lt;attribute name="coord1" type="{http://www.w3.org/2001/XMLSchema}float" />
 *       &lt;attribute name="coord2" type="{http://www.w3.org/2001/XMLSchema}float" />
 *       &lt;attribute name="coord3" type="{http://www.w3.org/2001/XMLSchema}float" />
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
@XmlRootElement(name = "KohonenMap", namespace = "http://www.dmg.org/PMML-4_1")
public class KohonenMap {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlAttribute(name = "coord1")
    protected Float coord1;
    @XmlAttribute(name = "coord2")
    protected Float coord2;
    @XmlAttribute(name = "coord3")
    protected Float coord3;

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
     * Gets the value of the coord1 property.
     *
     * @return
     *     possible object is
     *     {@link Float }
     *
     */
    public Float getCoord1() {
        return coord1;
    }

    /**
     * Sets the value of the coord1 property.
     *
     * @param value
     *     allowed object is
     *     {@link Float }
     *
     */
    public void setCoord1(Float value) {
        this.coord1 = value;
    }

    /**
     * Gets the value of the coord2 property.
     *
     * @return
     *     possible object is
     *     {@link Float }
     *
     */
    public Float getCoord2() {
        return coord2;
    }

    /**
     * Sets the value of the coord2 property.
     *
     * @param value
     *     allowed object is
     *     {@link Float }
     *
     */
    public void setCoord2(Float value) {
        this.coord2 = value;
    }

    /**
     * Gets the value of the coord3 property.
     *
     * @return
     *     possible object is
     *     {@link Float }
     *
     */
    public Float getCoord3() {
        return coord3;
    }

    /**
     * Sets the value of the coord3 property.
     *
     * @param value
     *     allowed object is
     *     {@link Float }
     *
     */
    public void setCoord3(Float value) {
        this.coord3 = value;
    }

}
