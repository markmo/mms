
package mms.common.models.pmml;

import java.math.BigInteger;
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
 *       &lt;/sequence>
 *       &lt;attribute name="targetCategory" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="parameterName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="beta" use="required" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
 *       &lt;attribute name="df" type="{http://www.dmg.org/PMML-4_1}INT-NUMBER" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "extension"
})
@XmlRootElement(name = "PCell", namespace = "http://www.dmg.org/PMML-4_1")
public class PCell {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlAttribute(name = "targetCategory")
    protected String targetCategory;
    @XmlAttribute(name = "parameterName", required = true)
    protected String parameterName;
    @XmlAttribute(name = "beta", required = true)
    protected double beta;
    @XmlAttribute(name = "df")
    protected BigInteger df;

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
     * Gets the value of the targetCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetCategory() {
        return targetCategory;
    }

    /**
     * Sets the value of the targetCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetCategory(String value) {
        this.targetCategory = value;
    }

    /**
     * Gets the value of the parameterName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParameterName() {
        return parameterName;
    }

    /**
     * Sets the value of the parameterName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParameterName(String value) {
        this.parameterName = value;
    }

    /**
     * Gets the value of the beta property.
     * 
     */
    public double getBeta() {
        return beta;
    }

    /**
     * Sets the value of the beta property.
     * 
     */
    public void setBeta(double value) {
        this.beta = value;
    }

    /**
     * Gets the value of the df property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDf() {
        return df;
    }

    /**
     * Sets the value of the df property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDf(BigInteger value) {
        this.df = value;
    }

}
