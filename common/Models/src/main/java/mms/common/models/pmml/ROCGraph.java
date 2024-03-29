
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}XCoordinates"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}YCoordinates"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}BoundaryValues" minOccurs="0"/>
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
    "xCoordinates",
    "yCoordinates",
    "boundaryValues"
})
@XmlRootElement(name = "ROCGraph", namespace = "http://www.dmg.org/PMML-4_1")
public class ROCGraph {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "XCoordinates", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected XCoordinates xCoordinates;
    @XmlElement(name = "YCoordinates", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected YCoordinates yCoordinates;
    @XmlElement(name = "BoundaryValues", namespace = "http://www.dmg.org/PMML-4_1")
    protected BoundaryValues boundaryValues;

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
     * Gets the value of the xCoordinates property.
     * 
     * @return
     *     possible object is
     *     {@link XCoordinates }
     *     
     */
    public XCoordinates getXCoordinates() {
        return xCoordinates;
    }

    /**
     * Sets the value of the xCoordinates property.
     * 
     * @param value
     *     allowed object is
     *     {@link XCoordinates }
     *     
     */
    public void setXCoordinates(XCoordinates value) {
        this.xCoordinates = value;
    }

    /**
     * Gets the value of the yCoordinates property.
     * 
     * @return
     *     possible object is
     *     {@link YCoordinates }
     *     
     */
    public YCoordinates getYCoordinates() {
        return yCoordinates;
    }

    /**
     * Sets the value of the yCoordinates property.
     * 
     * @param value
     *     allowed object is
     *     {@link YCoordinates }
     *     
     */
    public void setYCoordinates(YCoordinates value) {
        this.yCoordinates = value;
    }

    /**
     * Gets the value of the boundaryValues property.
     * 
     * @return
     *     possible object is
     *     {@link BoundaryValues }
     *     
     */
    public BoundaryValues getBoundaryValues() {
        return boundaryValues;
    }

    /**
     * Sets the value of the boundaryValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link BoundaryValues }
     *     
     */
    public void setBoundaryValues(BoundaryValues value) {
        this.boundaryValues = value;
    }

}
