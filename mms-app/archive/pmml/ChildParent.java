
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
 *         &lt;choice>
 *           &lt;element ref="{http://www.dmg.org/PMML-4_1}TableLocator"/>
 *           &lt;element ref="{http://www.dmg.org/PMML-4_1}InlineTable"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="childField" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="parentField" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="parentLevelField" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="isRecursive" default="no">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="no"/>
 *             &lt;enumeration value="yes"/>
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
    "extension",
    "tableLocator",
    "inlineTable"
})
@XmlRootElement(name = "ChildParent", namespace = "http://www.dmg.org/PMML-4_1")
public class ChildParent {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "TableLocator", namespace = "http://www.dmg.org/PMML-4_1")
    protected TableLocator tableLocator;
    @XmlElement(name = "InlineTable", namespace = "http://www.dmg.org/PMML-4_1")
    protected InlineTable inlineTable;
    @XmlAttribute(name = "childField", required = true)
    protected String childField;
    @XmlAttribute(name = "parentField", required = true)
    protected String parentField;
    @XmlAttribute(name = "parentLevelField")
    protected String parentLevelField;
    @XmlAttribute(name = "isRecursive")
    protected String isRecursive;

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
     * Gets the value of the tableLocator property.
     *
     * @return
     *     possible object is
     *     {@link TableLocator }
     *
     */
    public TableLocator getTableLocator() {
        return tableLocator;
    }

    /**
     * Sets the value of the tableLocator property.
     *
     * @param value
     *     allowed object is
     *     {@link TableLocator }
     *
     */
    public void setTableLocator(TableLocator value) {
        this.tableLocator = value;
    }

    /**
     * Gets the value of the inlineTable property.
     *
     * @return
     *     possible object is
     *     {@link InlineTable }
     *
     */
    public InlineTable getInlineTable() {
        return inlineTable;
    }

    /**
     * Sets the value of the inlineTable property.
     *
     * @param value
     *     allowed object is
     *     {@link InlineTable }
     *
     */
    public void setInlineTable(InlineTable value) {
        this.inlineTable = value;
    }

    /**
     * Gets the value of the childField property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getChildField() {
        return childField;
    }

    /**
     * Sets the value of the childField property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setChildField(String value) {
        this.childField = value;
    }

    /**
     * Gets the value of the parentField property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getParentField() {
        return parentField;
    }

    /**
     * Sets the value of the parentField property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setParentField(String value) {
        this.parentField = value;
    }

    /**
     * Gets the value of the parentLevelField property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getParentLevelField() {
        return parentLevelField;
    }

    /**
     * Sets the value of the parentLevelField property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setParentLevelField(String value) {
        this.parentLevelField = value;
    }

    /**
     * Gets the value of the isRecursive property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIsRecursive() {
        if (isRecursive == null) {
            return "no";
        } else {
            return isRecursive;
        }
    }

    /**
     * Sets the value of the isRecursive property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIsRecursive(String value) {
        this.isRecursive = value;
    }

}
