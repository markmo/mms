
package models.pmml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Indices" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}INT-Entries" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="n" type="{http://www.dmg.org/PMML-4_1}INT-NUMBER" />
 *       &lt;attribute name="defaultValue" type="{http://www.dmg.org/PMML-4_1}INT-NUMBER" default="0" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "indices",
    "intEntries"
})
@XmlRootElement(name = "INT-SparseArray", namespace = "http://www.dmg.org/PMML-4_1")
public class INTSparseArray {

    @XmlList
    @XmlElement(name = "Indices", namespace = "http://www.dmg.org/PMML-4_1", type = Integer.class)
    protected List<Integer> indices;
    @XmlList
    @XmlElement(name = "INT-Entries", namespace = "http://www.dmg.org/PMML-4_1", type = Integer.class)
    protected List<Integer> intEntries;
    @XmlAttribute(name = "n")
    protected BigInteger n;
    @XmlAttribute(name = "defaultValue")
    protected BigInteger defaultValue;

    /**
     * Gets the value of the indices property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indices property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndices().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     *
     *
     */
    public List<Integer> getIndices() {
        if (indices == null) {
            indices = new ArrayList<Integer>();
        }
        return this.indices;
    }

    /**
     * Gets the value of the intEntries property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the intEntries property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getINTEntries().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     *
     *
     */
    public List<Integer> getINTEntries() {
        if (intEntries == null) {
            intEntries = new ArrayList<Integer>();
        }
        return this.intEntries;
    }

    /**
     * Gets the value of the n property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getN() {
        return n;
    }

    /**
     * Sets the value of the n property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setN(BigInteger value) {
        this.n = value;
    }

    /**
     * Gets the value of the defaultValue property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getDefaultValue() {
        if (defaultValue == null) {
            return new BigInteger("0");
        } else {
            return defaultValue;
        }
    }

    /**
     * Sets the value of the defaultValue property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setDefaultValue(BigInteger value) {
        this.defaultValue = value;
    }

}
