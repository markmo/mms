
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
 *         &lt;choice>
 *           &lt;element ref="{http://www.dmg.org/PMML-4_1}PredictiveModelQuality" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{http://www.dmg.org/PMML-4_1}ClusteringModelQuality" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Correlations" minOccurs="0"/>
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
    "predictiveModelQuality",
    "clusteringModelQuality",
    "correlations"
})
@XmlRootElement(name = "ModelExplanation", namespace = "http://www.dmg.org/PMML-4_1")
public class ModelExplanation {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "PredictiveModelQuality", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<PredictiveModelQuality> predictiveModelQuality;
    @XmlElement(name = "ClusteringModelQuality", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<ClusteringModelQuality> clusteringModelQuality;
    @XmlElement(name = "Correlations", namespace = "http://www.dmg.org/PMML-4_1")
    protected Correlations correlations;

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
     * Gets the value of the predictiveModelQuality property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the predictiveModelQuality property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPredictiveModelQuality().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PredictiveModelQuality }
     *
     *
     */
    public List<PredictiveModelQuality> getPredictiveModelQuality() {
        if (predictiveModelQuality == null) {
            predictiveModelQuality = new ArrayList<PredictiveModelQuality>();
        }
        return this.predictiveModelQuality;
    }

    /**
     * Gets the value of the clusteringModelQuality property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clusteringModelQuality property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClusteringModelQuality().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClusteringModelQuality }
     *
     *
     */
    public List<ClusteringModelQuality> getClusteringModelQuality() {
        if (clusteringModelQuality == null) {
            clusteringModelQuality = new ArrayList<ClusteringModelQuality>();
        }
        return this.clusteringModelQuality;
    }

    /**
     * Gets the value of the correlations property.
     *
     * @return
     *     possible object is
     *     {@link Correlations }
     *
     */
    public Correlations getCorrelations() {
        return correlations;
    }

    /**
     * Sets the value of the correlations property.
     *
     * @param value
     *     allowed object is
     *     {@link Correlations }
     *
     */
    public void setCorrelations(Correlations value) {
        this.correlations = value;
    }

}
