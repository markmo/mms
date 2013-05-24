
package mms.common.models.pmml;

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
 *       &lt;attribute name="c00-parameter" use="required" type="{http://www.dmg.org/PMML-4_1}NUMBER" />
 *       &lt;attribute name="c01-parameter" use="required" type="{http://www.dmg.org/PMML-4_1}NUMBER" />
 *       &lt;attribute name="c10-parameter" use="required" type="{http://www.dmg.org/PMML-4_1}NUMBER" />
 *       &lt;attribute name="c11-parameter" use="required" type="{http://www.dmg.org/PMML-4_1}NUMBER" />
 *       &lt;attribute name="d00-parameter" use="required" type="{http://www.dmg.org/PMML-4_1}NUMBER" />
 *       &lt;attribute name="d01-parameter" use="required" type="{http://www.dmg.org/PMML-4_1}NUMBER" />
 *       &lt;attribute name="d10-parameter" use="required" type="{http://www.dmg.org/PMML-4_1}NUMBER" />
 *       &lt;attribute name="d11-parameter" use="required" type="{http://www.dmg.org/PMML-4_1}NUMBER" />
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
@XmlRootElement(name = "binarySimilarity", namespace = "http://www.dmg.org/PMML-4_1")
public class BinarySimilarity {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlAttribute(name = "c00-parameter", required = true)
    protected double c00Parameter;
    @XmlAttribute(name = "c01-parameter", required = true)
    protected double c01Parameter;
    @XmlAttribute(name = "c10-parameter", required = true)
    protected double c10Parameter;
    @XmlAttribute(name = "c11-parameter", required = true)
    protected double c11Parameter;
    @XmlAttribute(name = "d00-parameter", required = true)
    protected double d00Parameter;
    @XmlAttribute(name = "d01-parameter", required = true)
    protected double d01Parameter;
    @XmlAttribute(name = "d10-parameter", required = true)
    protected double d10Parameter;
    @XmlAttribute(name = "d11-parameter", required = true)
    protected double d11Parameter;

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
     * Gets the value of the c00Parameter property.
     * 
     */
    public double getC00Parameter() {
        return c00Parameter;
    }

    /**
     * Sets the value of the c00Parameter property.
     * 
     */
    public void setC00Parameter(double value) {
        this.c00Parameter = value;
    }

    /**
     * Gets the value of the c01Parameter property.
     * 
     */
    public double getC01Parameter() {
        return c01Parameter;
    }

    /**
     * Sets the value of the c01Parameter property.
     * 
     */
    public void setC01Parameter(double value) {
        this.c01Parameter = value;
    }

    /**
     * Gets the value of the c10Parameter property.
     * 
     */
    public double getC10Parameter() {
        return c10Parameter;
    }

    /**
     * Sets the value of the c10Parameter property.
     * 
     */
    public void setC10Parameter(double value) {
        this.c10Parameter = value;
    }

    /**
     * Gets the value of the c11Parameter property.
     * 
     */
    public double getC11Parameter() {
        return c11Parameter;
    }

    /**
     * Sets the value of the c11Parameter property.
     * 
     */
    public void setC11Parameter(double value) {
        this.c11Parameter = value;
    }

    /**
     * Gets the value of the d00Parameter property.
     * 
     */
    public double getD00Parameter() {
        return d00Parameter;
    }

    /**
     * Sets the value of the d00Parameter property.
     * 
     */
    public void setD00Parameter(double value) {
        this.d00Parameter = value;
    }

    /**
     * Gets the value of the d01Parameter property.
     * 
     */
    public double getD01Parameter() {
        return d01Parameter;
    }

    /**
     * Sets the value of the d01Parameter property.
     * 
     */
    public void setD01Parameter(double value) {
        this.d01Parameter = value;
    }

    /**
     * Gets the value of the d10Parameter property.
     * 
     */
    public double getD10Parameter() {
        return d10Parameter;
    }

    /**
     * Sets the value of the d10Parameter property.
     * 
     */
    public void setD10Parameter(double value) {
        this.d10Parameter = value;
    }

    /**
     * Gets the value of the d11Parameter property.
     * 
     */
    public double getD11Parameter() {
        return d11Parameter;
    }

    /**
     * Sets the value of the d11Parameter property.
     * 
     */
    public void setD11Parameter(double value) {
        this.d11Parameter = value;
    }

}
