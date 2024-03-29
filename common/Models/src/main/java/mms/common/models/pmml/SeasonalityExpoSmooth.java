
package mms.common.models.pmml;

import java.math.BigInteger;
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
 *         &lt;group ref="{http://www.dmg.org/PMML-4_1}REAL-ARRAY"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="additive"/>
 *             &lt;enumeration value="multiplicative"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="period" use="required" type="{http://www.dmg.org/PMML-4_1}INT-NUMBER" />
 *       &lt;attribute name="unit" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="phase" type="{http://www.dmg.org/PMML-4_1}INT-NUMBER" />
 *       &lt;attribute name="delta" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
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
@XmlRootElement(name = "Seasonality_ExpoSmooth", namespace = "http://www.dmg.org/PMML-4_1")
public class SeasonalityExpoSmooth {

    @XmlElement(name = "Array", namespace = "http://www.dmg.org/PMML-4_1")
    protected ArrayType array;
    @XmlAttribute(name = "type", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;
    @XmlAttribute(name = "period", required = true)
    protected BigInteger period;
    @XmlAttribute(name = "unit")
    protected String unit;
    @XmlAttribute(name = "phase")
    protected BigInteger phase;
    @XmlAttribute(name = "delta")
    protected Double delta;

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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPeriod(BigInteger value) {
        this.period = value;
    }

    /**
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    /**
     * Gets the value of the phase property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPhase() {
        return phase;
    }

    /**
     * Sets the value of the phase property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPhase(BigInteger value) {
        this.phase = value;
    }

    /**
     * Gets the value of the delta property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDelta() {
        return delta;
    }

    /**
     * Sets the value of the delta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDelta(Double value) {
        this.delta = value;
    }

}
