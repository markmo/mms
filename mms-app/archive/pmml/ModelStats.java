
package models.pmml;

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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}UnivariateStats" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}MultivariateStats" maxOccurs="unbounded" minOccurs="0"/>
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
    "univariateStats",
    "multivariateStats"
})
@XmlRootElement(name = "ModelStats", namespace = "http://www.dmg.org/PMML-4_1")
public class ModelStats {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "UnivariateStats", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<UnivariateStats> univariateStats;
    @XmlElement(name = "MultivariateStats", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<MultivariateStats> multivariateStats;

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
     * Gets the value of the univariateStats property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the univariateStats property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnivariateStats().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UnivariateStats }
     *
     *
     */
    public List<UnivariateStats> getUnivariateStats() {
        if (univariateStats == null) {
            univariateStats = new ArrayList<UnivariateStats>();
        }
        return this.univariateStats;
    }

    /**
     * Gets the value of the multivariateStats property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the multivariateStats property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMultivariateStats().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MultivariateStats }
     *
     *
     */
    public List<MultivariateStats> getMultivariateStats() {
        if (multivariateStats == null) {
            multivariateStats = new ArrayList<MultivariateStats>();
        }
        return this.multivariateStats;
    }

}
