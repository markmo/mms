
package models.pmml;

import java.math.BigInteger;
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Timestamp" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="index" type="{http://www.dmg.org/PMML-4_1}INT-NUMBER" />
 *       &lt;attribute name="time" type="{http://www.dmg.org/PMML-4_1}NUMBER" />
 *       &lt;attribute name="value" use="required" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
 *       &lt;attribute name="standardError" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "timestamp"
})
@XmlRootElement(name = "TimeValue", namespace = "http://www.dmg.org/PMML-4_1")
public class TimeValue {

    @XmlElement(name = "Timestamp", namespace = "http://www.dmg.org/PMML-4_1")
    protected Timestamp timestamp;
    @XmlAttribute(name = "index")
    protected BigInteger index;
    @XmlAttribute(name = "time")
    protected Double time;
    @XmlAttribute(name = "value", required = true)
    protected double value;
    @XmlAttribute(name = "standardError")
    protected Double standardError;

    /**
     * Gets the value of the timestamp property.
     *
     * @return
     *     possible object is
     *     {@link Timestamp }
     *
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     *
     * @param value
     *     allowed object is
     *     {@link Timestamp }
     *
     */
    public void setTimestamp(Timestamp value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the index property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setIndex(BigInteger value) {
        this.index = value;
    }

    /**
     * Gets the value of the time property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     *
     */
    public Double getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
    public void setTime(Double value) {
        this.time = value;
    }

    /**
     * Gets the value of the value property.
     *
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Gets the value of the standardError property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     *
     */
    public Double getStandardError() {
        return standardError;
    }

    /**
     * Sets the value of the standardError property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
    public void setStandardError(Double value) {
        this.standardError = value;
    }

}
