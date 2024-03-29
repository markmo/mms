
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
 *       &lt;choice>
 *         &lt;group ref="{http://www.dmg.org/PMML-4_1}CONTINUOUS-DISTRIBUTION-TYPES"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "anyDistribution",
    "gaussianDistribution",
    "poissonDistribution",
    "uniformDistribution",
    "extension"
})
@XmlRootElement(name = "Alternate", namespace = "http://www.dmg.org/PMML-4_1")
public class Alternate {

    @XmlElement(name = "AnyDistribution", namespace = "http://www.dmg.org/PMML-4_1")
    protected AnyDistribution anyDistribution;
    @XmlElement(name = "GaussianDistribution", namespace = "http://www.dmg.org/PMML-4_1")
    protected GaussianDistribution gaussianDistribution;
    @XmlElement(name = "PoissonDistribution", namespace = "http://www.dmg.org/PMML-4_1")
    protected PoissonDistribution poissonDistribution;
    @XmlElement(name = "UniformDistribution", namespace = "http://www.dmg.org/PMML-4_1")
    protected UniformDistribution uniformDistribution;
    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;

    /**
     * Gets the value of the anyDistribution property.
     * 
     * @return
     *     possible object is
     *     {@link AnyDistribution }
     *     
     */
    public AnyDistribution getAnyDistribution() {
        return anyDistribution;
    }

    /**
     * Sets the value of the anyDistribution property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnyDistribution }
     *     
     */
    public void setAnyDistribution(AnyDistribution value) {
        this.anyDistribution = value;
    }

    /**
     * Gets the value of the gaussianDistribution property.
     * 
     * @return
     *     possible object is
     *     {@link GaussianDistribution }
     *     
     */
    public GaussianDistribution getGaussianDistribution() {
        return gaussianDistribution;
    }

    /**
     * Sets the value of the gaussianDistribution property.
     * 
     * @param value
     *     allowed object is
     *     {@link GaussianDistribution }
     *     
     */
    public void setGaussianDistribution(GaussianDistribution value) {
        this.gaussianDistribution = value;
    }

    /**
     * Gets the value of the poissonDistribution property.
     * 
     * @return
     *     possible object is
     *     {@link PoissonDistribution }
     *     
     */
    public PoissonDistribution getPoissonDistribution() {
        return poissonDistribution;
    }

    /**
     * Sets the value of the poissonDistribution property.
     * 
     * @param value
     *     allowed object is
     *     {@link PoissonDistribution }
     *     
     */
    public void setPoissonDistribution(PoissonDistribution value) {
        this.poissonDistribution = value;
    }

    /**
     * Gets the value of the uniformDistribution property.
     * 
     * @return
     *     possible object is
     *     {@link UniformDistribution }
     *     
     */
    public UniformDistribution getUniformDistribution() {
        return uniformDistribution;
    }

    /**
     * Sets the value of the uniformDistribution property.
     * 
     * @param value
     *     allowed object is
     *     {@link UniformDistribution }
     *     
     */
    public void setUniformDistribution(UniformDistribution value) {
        this.uniformDistribution = value;
    }

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

}
