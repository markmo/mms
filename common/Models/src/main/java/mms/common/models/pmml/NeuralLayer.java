
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Neuron" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="numberOfNeurons" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="activationFunction" type="{http://www.dmg.org/PMML-4_1}ACTIVATION-FUNCTION" />
 *       &lt;attribute name="threshold" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
 *       &lt;attribute name="width" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
 *       &lt;attribute name="altitude" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
 *       &lt;attribute name="normalizationMethod" type="{http://www.dmg.org/PMML-4_1}NN-NORMALIZATION-METHOD" />
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
    "neuron"
})
@XmlRootElement(name = "NeuralLayer", namespace = "http://www.dmg.org/PMML-4_1")
public class NeuralLayer {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "Neuron", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected List<Neuron> neuron;
    @XmlAttribute(name = "numberOfNeurons")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger numberOfNeurons;
    @XmlAttribute(name = "activationFunction")
    protected ACTIVATIONFUNCTION activationFunction;
    @XmlAttribute(name = "threshold")
    protected Double threshold;
    @XmlAttribute(name = "width")
    protected Double width;
    @XmlAttribute(name = "altitude")
    protected Double altitude;
    @XmlAttribute(name = "normalizationMethod")
    protected NNNORMALIZATIONMETHOD normalizationMethod;

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
     * Gets the value of the neuron property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the neuron property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNeuron().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Neuron }
     * 
     * 
     */
    public List<Neuron> getNeuron() {
        if (neuron == null) {
            neuron = new ArrayList<Neuron>();
        }
        return this.neuron;
    }

    /**
     * Gets the value of the numberOfNeurons property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfNeurons() {
        return numberOfNeurons;
    }

    /**
     * Sets the value of the numberOfNeurons property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfNeurons(BigInteger value) {
        this.numberOfNeurons = value;
    }

    /**
     * Gets the value of the activationFunction property.
     * 
     * @return
     *     possible object is
     *     {@link ACTIVATIONFUNCTION }
     *     
     */
    public ACTIVATIONFUNCTION getActivationFunction() {
        return activationFunction;
    }

    /**
     * Sets the value of the activationFunction property.
     * 
     * @param value
     *     allowed object is
     *     {@link ACTIVATIONFUNCTION }
     *     
     */
    public void setActivationFunction(ACTIVATIONFUNCTION value) {
        this.activationFunction = value;
    }

    /**
     * Gets the value of the threshold property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThreshold() {
        return threshold;
    }

    /**
     * Sets the value of the threshold property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThreshold(Double value) {
        this.threshold = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWidth(Double value) {
        this.width = value;
    }

    /**
     * Gets the value of the altitude property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAltitude() {
        return altitude;
    }

    /**
     * Sets the value of the altitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAltitude(Double value) {
        this.altitude = value;
    }

    /**
     * Gets the value of the normalizationMethod property.
     * 
     * @return
     *     possible object is
     *     {@link NNNORMALIZATIONMETHOD }
     *     
     */
    public NNNORMALIZATIONMETHOD getNormalizationMethod() {
        return normalizationMethod;
    }

    /**
     * Sets the value of the normalizationMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link NNNORMALIZATIONMETHOD }
     *     
     */
    public void setNormalizationMethod(NNNORMALIZATIONMETHOD value) {
        this.normalizationMethod = value;
    }

}
