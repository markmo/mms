
package mms.common.models.pmml;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Level"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Trend_ExpoSmooth" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Seasonality_ExpoSmooth" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}TimeValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="RMSE" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
 *       &lt;attribute name="transformation" default="none">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="none"/>
 *             &lt;enumeration value="logarithmic"/>
 *             &lt;enumeration value="squareroot"/>
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
    "level",
    "trendExpoSmooth",
    "seasonalityExpoSmooth",
    "timeValue"
})
@XmlRootElement(name = "ExponentialSmoothing", namespace = "http://www.dmg.org/PMML-4_1")
public class ExponentialSmoothing {

    @XmlElement(name = "Level", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected Level level;
    @XmlElement(name = "Trend_ExpoSmooth", namespace = "http://www.dmg.org/PMML-4_1")
    protected TrendExpoSmooth trendExpoSmooth;
    @XmlElement(name = "Seasonality_ExpoSmooth", namespace = "http://www.dmg.org/PMML-4_1")
    protected SeasonalityExpoSmooth seasonalityExpoSmooth;
    @XmlElement(name = "TimeValue", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<TimeValue> timeValue;
    @XmlAttribute(name = "RMSE")
    protected Double rmse;
    @XmlAttribute(name = "transformation")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String transformation;

    /**
     * Gets the value of the level property.
     * 
     * @return
     *     possible object is
     *     {@link Level }
     *     
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     * 
     * @param value
     *     allowed object is
     *     {@link Level }
     *     
     */
    public void setLevel(Level value) {
        this.level = value;
    }

    /**
     * Gets the value of the trendExpoSmooth property.
     * 
     * @return
     *     possible object is
     *     {@link TrendExpoSmooth }
     *     
     */
    public TrendExpoSmooth getTrendExpoSmooth() {
        return trendExpoSmooth;
    }

    /**
     * Sets the value of the trendExpoSmooth property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrendExpoSmooth }
     *     
     */
    public void setTrendExpoSmooth(TrendExpoSmooth value) {
        this.trendExpoSmooth = value;
    }

    /**
     * Gets the value of the seasonalityExpoSmooth property.
     * 
     * @return
     *     possible object is
     *     {@link SeasonalityExpoSmooth }
     *     
     */
    public SeasonalityExpoSmooth getSeasonalityExpoSmooth() {
        return seasonalityExpoSmooth;
    }

    /**
     * Sets the value of the seasonalityExpoSmooth property.
     * 
     * @param value
     *     allowed object is
     *     {@link SeasonalityExpoSmooth }
     *     
     */
    public void setSeasonalityExpoSmooth(SeasonalityExpoSmooth value) {
        this.seasonalityExpoSmooth = value;
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
     * Gets the value of the rmse property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRMSE() {
        return rmse;
    }

    /**
     * Sets the value of the rmse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRMSE(Double value) {
        this.rmse = value;
    }

    /**
     * Gets the value of the transformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransformation() {
        if (transformation == null) {
            return "none";
        } else {
            return transformation;
        }
    }

    /**
     * Sets the value of the transformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransformation(String value) {
        this.transformation = value;
    }

}
