
package models.pmml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;group ref="{http://www.dmg.org/PMML-4_1}REAL-ARRAY" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="trend" default="additive">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="additive"/>
 *             &lt;enumeration value="damped_additive"/>
 *             &lt;enumeration value="multiplicative"/>
 *             &lt;enumeration value="damped_multiplicative"/>
 *             &lt;enumeration value="polynomial_exponential"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="gamma" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
 *       &lt;attribute name="phi" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" default="1" />
 *       &lt;attribute name="smoothedValue" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "array"
})
@XmlRootElement(name = "Trend_ExpoSmooth", namespace = "http://www.dmg.org/PMML-4_1")
public class TrendExpoSmooth {

    @XmlElement(name = "Array", namespace = "http://www.dmg.org/PMML-4_1")
    protected ArrayType array;
    @XmlAttribute(name = "trend")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String trend;
    @XmlAttribute(name = "gamma")
    protected Double gamma;
    @XmlAttribute(name = "phi")
    protected Double phi;
    @XmlAttribute(name = "smoothedValue")
    protected Double smoothedValue;

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
     * Gets the value of the trend property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTrend() {
        if (trend == null) {
            return "additive";
        } else {
            return trend;
        }
    }

    /**
     * Sets the value of the trend property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTrend(String value) {
        this.trend = value;
    }

    /**
     * Gets the value of the gamma property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     *
     */
    public Double getGamma() {
        return gamma;
    }

    /**
     * Sets the value of the gamma property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
    public void setGamma(Double value) {
        this.gamma = value;
    }

    /**
     * Gets the value of the phi property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     *
     */
    public double getPhi() {
        if (phi == null) {
            return  1.0D;
        } else {
            return phi;
        }
    }

    /**
     * Sets the value of the phi property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
    public void setPhi(Double value) {
        this.phi = value;
    }

    /**
     * Gets the value of the smoothedValue property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     *
     */
    public Double getSmoothedValue() {
        return smoothedValue;
    }

    /**
     * Sets the value of the smoothedValue property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
    public void setSmoothedValue(Double value) {
        this.smoothedValue = value;
    }

}
