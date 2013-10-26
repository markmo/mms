
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}NumericPredictor" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}CategoricalPredictor" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}PredictorTerm" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="intercept" use="required" type="{http://www.dmg.org/PMML-4_1}REAL-NUMBER" />
 *       &lt;attribute name="targetCategory" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "numericPredictor",
    "categoricalPredictor",
    "predictorTerm"
})
@XmlRootElement(name = "RegressionTable", namespace = "http://www.dmg.org/PMML-4_1")
public class RegressionTable {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "NumericPredictor", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<NumericPredictor> numericPredictor;
    @XmlElement(name = "CategoricalPredictor", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<CategoricalPredictor> categoricalPredictor;
    @XmlElement(name = "PredictorTerm", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<PredictorTerm> predictorTerm;
    @XmlAttribute(name = "intercept", required = true)
    protected double intercept;
    @XmlAttribute(name = "targetCategory")
    protected String targetCategory;

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
     * Gets the value of the numericPredictor property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the numericPredictor property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNumericPredictor().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NumericPredictor }
     *
     *
     */
    public List<NumericPredictor> getNumericPredictor() {
        if (numericPredictor == null) {
            numericPredictor = new ArrayList<NumericPredictor>();
        }
        return this.numericPredictor;
    }

    /**
     * Gets the value of the categoricalPredictor property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categoricalPredictor property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategoricalPredictor().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CategoricalPredictor }
     *
     *
     */
    public List<CategoricalPredictor> getCategoricalPredictor() {
        if (categoricalPredictor == null) {
            categoricalPredictor = new ArrayList<CategoricalPredictor>();
        }
        return this.categoricalPredictor;
    }

    /**
     * Gets the value of the predictorTerm property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the predictorTerm property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPredictorTerm().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PredictorTerm }
     *
     *
     */
    public List<PredictorTerm> getPredictorTerm() {
        if (predictorTerm == null) {
            predictorTerm = new ArrayList<PredictorTerm>();
        }
        return this.predictorTerm;
    }

    /**
     * Gets the value of the intercept property.
     *
     */
    public double getIntercept() {
        return intercept;
    }

    /**
     * Sets the value of the intercept property.
     *
     */
    public void setIntercept(double value) {
        this.intercept = value;
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

}
