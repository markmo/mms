
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Extension" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Comparisons" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="field" use="required" type="{http://www.dmg.org/PMML-4_1}FIELD-NAME" />
 *       &lt;attribute name="isCenterField" default="true">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="true"/>
 *             &lt;enumeration value="false"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="fieldWeight" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" default="1" />
 *       &lt;attribute name="similarityScale" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
 *       &lt;attribute name="compareFunction" type="{http://www.dmg.org/PMML-4_1}COMPARE-FUNCTION" />
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
    "comparisons"
})
@XmlRootElement(name = "ClusteringField", namespace = "http://www.dmg.org/PMML-4_1")
public class ClusteringField {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "Comparisons", namespace = "http://www.dmg.org/PMML-4_1")
    protected Comparisons comparisons;
    @XmlAttribute(name = "field", required = true)
    protected String field;
    @XmlAttribute(name = "isCenterField")
    protected String isCenterField;
    @XmlAttribute(name = "fieldWeight")
    protected Double fieldWeight;
    @XmlAttribute(name = "similarityScale")
    protected Double similarityScale;
    @XmlAttribute(name = "compareFunction")
    protected COMPAREFUNCTION compareFunction;

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
     * Gets the value of the comparisons property.
     *
     * @return
     *     possible object is
     *     {@link Comparisons }
     *
     */
    public Comparisons getComparisons() {
        return comparisons;
    }

    /**
     * Sets the value of the comparisons property.
     *
     * @param value
     *     allowed object is
     *     {@link Comparisons }
     *
     */
    public void setComparisons(Comparisons value) {
        this.comparisons = value;
    }

    /**
     * Gets the value of the field property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getField() {
        return field;
    }

    /**
     * Sets the value of the field property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setField(String value) {
        this.field = value;
    }

    /**
     * Gets the value of the isCenterField property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIsCenterField() {
        if (isCenterField == null) {
            return "true";
        } else {
            return isCenterField;
        }
    }

    /**
     * Sets the value of the isCenterField property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIsCenterField(String value) {
        this.isCenterField = value;
    }

    /**
     * Gets the value of the fieldWeight property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     *
     */
    public double getFieldWeight() {
        if (fieldWeight == null) {
            return  1.0D;
        } else {
            return fieldWeight;
        }
    }

    /**
     * Sets the value of the fieldWeight property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
    public void setFieldWeight(Double value) {
        this.fieldWeight = value;
    }

    /**
     * Gets the value of the similarityScale property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     *
     */
    public Double getSimilarityScale() {
        return similarityScale;
    }

    /**
     * Sets the value of the similarityScale property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
    public void setSimilarityScale(Double value) {
        this.similarityScale = value;
    }

    /**
     * Gets the value of the compareFunction property.
     *
     * @return
     *     possible object is
     *     {@link COMPAREFUNCTION }
     *
     */
    public COMPAREFUNCTION getCompareFunction() {
        return compareFunction;
    }

    /**
     * Sets the value of the compareFunction property.
     *
     * @param value
     *     allowed object is
     *     {@link COMPAREFUNCTION }
     *
     */
    public void setCompareFunction(COMPAREFUNCTION value) {
        this.compareFunction = value;
    }

}
