
package models.pmml;

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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}TimeAnchor" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}TimeValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="usage" type="{http://www.dmg.org/PMML-4_1}TIMESERIES-USAGE" default="original" />
 *       &lt;attribute name="startTime" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
 *       &lt;attribute name="endTime" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
 *       &lt;attribute name="interpolationMethod" type="{http://www.dmg.org/PMML-4_1}INTERPOLATION-METHOD" default="none" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "timeAnchor",
    "timeValue"
})
@XmlRootElement(name = "TimeSeries", namespace = "http://www.dmg.org/PMML-4_1")
public class TimeSeries {

    @XmlElement(name = "TimeAnchor", namespace = "http://www.dmg.org/PMML-4_1")
    protected TimeAnchor timeAnchor;
    @XmlElement(name = "TimeValue", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<TimeValue> timeValue;
    @XmlAttribute(name = "usage")
    protected TIMESERIESUSAGE usage;
    @XmlAttribute(name = "startTime")
    protected Double startTime;
    @XmlAttribute(name = "endTime")
    protected Double endTime;
    @XmlAttribute(name = "interpolationMethod")
    protected INTERPOLATIONMETHOD interpolationMethod;

    /**
     * Gets the value of the timeAnchor property.
     *
     * @return
     *     possible object is
     *     {@link TimeAnchor }
     *
     */
    public TimeAnchor getTimeAnchor() {
        return timeAnchor;
    }

    /**
     * Sets the value of the timeAnchor property.
     *
     * @param value
     *     allowed object is
     *     {@link TimeAnchor }
     *
     */
    public void setTimeAnchor(TimeAnchor value) {
        this.timeAnchor = value;
    }

    /**
     * Gets the value of the timeValue property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the timeValue property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTimeValue().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TimeValue }
     *
     *
     */
    public List<TimeValue> getTimeValue() {
        if (timeValue == null) {
            timeValue = new ArrayList<TimeValue>();
        }
        return this.timeValue;
    }

    /**
     * Gets the value of the usage property.
     *
     * @return
     *     possible object is
     *     {@link TIMESERIESUSAGE }
     *
     */
    public TIMESERIESUSAGE getUsage() {
        if (usage == null) {
            return TIMESERIESUSAGE.ORIGINAL;
        } else {
            return usage;
        }
    }

    /**
     * Sets the value of the usage property.
     *
     * @param value
     *     allowed object is
     *     {@link TIMESERIESUSAGE }
     *
     */
    public void setUsage(TIMESERIESUSAGE value) {
        this.usage = value;
    }

    /**
     * Gets the value of the startTime property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     *
     */
    public Double getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
    public void setStartTime(Double value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     *
     */
    public Double getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
    public void setEndTime(Double value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the interpolationMethod property.
     *
     * @return
     *     possible object is
     *     {@link INTERPOLATIONMETHOD }
     *
     */
    public INTERPOLATIONMETHOD getInterpolationMethod() {
        if (interpolationMethod == null) {
            return INTERPOLATIONMETHOD.NONE;
        } else {
            return interpolationMethod;
        }
    }

    /**
     * Sets the value of the interpolationMethod property.
     *
     * @param value
     *     allowed object is
     *     {@link INTERPOLATIONMETHOD }
     *
     */
    public void setInterpolationMethod(INTERPOLATIONMETHOD value) {
        this.interpolationMethod = value;
    }

}
