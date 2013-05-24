
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
 *         &lt;choice>
 *           &lt;element ref="{http://www.dmg.org/PMML-4_1}BaselineStratum" maxOccurs="unbounded"/>
 *           &lt;element ref="{http://www.dmg.org/PMML-4_1}BaselineCell" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="maxTime" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
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
    "baselineStratum",
    "baselineCell"
})
@XmlRootElement(name = "BaseCumHazardTables", namespace = "http://www.dmg.org/PMML-4_1")
public class BaseCumHazardTables {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "BaselineStratum", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<BaselineStratum> baselineStratum;
    @XmlElement(name = "BaselineCell", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<BaselineCell> baselineCell;
    @XmlAttribute(name = "maxTime")
    protected Double maxTime;

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
     * Gets the value of the baselineStratum property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the baselineStratum property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBaselineStratum().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BaselineStratum }
     * 
     * 
     */
    public List<BaselineStratum> getBaselineStratum() {
        if (baselineStratum == null) {
            baselineStratum = new ArrayList<BaselineStratum>();
        }
        return this.baselineStratum;
    }

    /**
     * Gets the value of the baselineCell property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the baselineCell property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBaselineCell().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BaselineCell }
     * 
     * 
     */
    public List<BaselineCell> getBaselineCell() {
        if (baselineCell == null) {
            baselineCell = new ArrayList<BaselineCell>();
        }
        return this.baselineCell;
    }

    /**
     * Gets the value of the maxTime property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMaxTime() {
        return maxTime;
    }

    /**
     * Sets the value of the maxTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMaxTime(Double value) {
        this.maxTime = value;
    }

}
