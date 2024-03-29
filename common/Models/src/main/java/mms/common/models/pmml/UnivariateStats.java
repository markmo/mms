
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Counts" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}NumericInfo" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}DiscrStats" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}ContStats" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Anova" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="field" type="{http://www.dmg.org/PMML-4_1}FIELD-NAME" />
 *       &lt;attribute name="weighted" default="0">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="0"/>
 *             &lt;enumeration value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
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
    "counts",
    "numericInfo",
    "discrStats",
    "contStats",
    "anova"
})
@XmlRootElement(name = "UnivariateStats", namespace = "http://www.dmg.org/PMML-4_1")
public class UnivariateStats {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "Counts", namespace = "http://www.dmg.org/PMML-4_1")
    protected Counts counts;
    @XmlElement(name = "NumericInfo", namespace = "http://www.dmg.org/PMML-4_1")
    protected NumericInfo numericInfo;
    @XmlElement(name = "DiscrStats", namespace = "http://www.dmg.org/PMML-4_1")
    protected DiscrStats discrStats;
    @XmlElement(name = "ContStats", namespace = "http://www.dmg.org/PMML-4_1")
    protected ContStats contStats;
    @XmlElement(name = "Anova", namespace = "http://www.dmg.org/PMML-4_1")
    protected Anova anova;
    @XmlAttribute(name = "field")
    protected String field;
    @XmlAttribute(name = "weighted")
    protected String weighted;

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
     * Gets the value of the counts property.
     * 
     * @return
     *     possible object is
     *     {@link Counts }
     *     
     */
    public Counts getCounts() {
        return counts;
    }

    /**
     * Sets the value of the counts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Counts }
     *     
     */
    public void setCounts(Counts value) {
        this.counts = value;
    }

    /**
     * Gets the value of the numericInfo property.
     * 
     * @return
     *     possible object is
     *     {@link NumericInfo }
     *     
     */
    public NumericInfo getNumericInfo() {
        return numericInfo;
    }

    /**
     * Sets the value of the numericInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link NumericInfo }
     *     
     */
    public void setNumericInfo(NumericInfo value) {
        this.numericInfo = value;
    }

    /**
     * Gets the value of the discrStats property.
     * 
     * @return
     *     possible object is
     *     {@link DiscrStats }
     *     
     */
    public DiscrStats getDiscrStats() {
        return discrStats;
    }

    /**
     * Sets the value of the discrStats property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiscrStats }
     *     
     */
    public void setDiscrStats(DiscrStats value) {
        this.discrStats = value;
    }

    /**
     * Gets the value of the contStats property.
     * 
     * @return
     *     possible object is
     *     {@link ContStats }
     *     
     */
    public ContStats getContStats() {
        return contStats;
    }

    /**
     * Sets the value of the contStats property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContStats }
     *     
     */
    public void setContStats(ContStats value) {
        this.contStats = value;
    }

    /**
     * Gets the value of the anova property.
     * 
     * @return
     *     possible object is
     *     {@link Anova }
     *     
     */
    public Anova getAnova() {
        return anova;
    }

    /**
     * Sets the value of the anova property.
     * 
     * @param value
     *     allowed object is
     *     {@link Anova }
     *     
     */
    public void setAnova(Anova value) {
        this.anova = value;
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
     * Gets the value of the weighted property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeighted() {
        if (weighted == null) {
            return "0";
        } else {
            return weighted;
        }
    }

    /**
     * Sets the value of the weighted property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeighted(String value) {
        this.weighted = value;
    }

}
