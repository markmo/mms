
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}FieldColumnPair" maxOccurs="unbounded"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{http://www.dmg.org/PMML-4_1}TableLocator"/>
 *           &lt;element ref="{http://www.dmg.org/PMML-4_1}InlineTable"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="mapMissingTo" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="defaultValue" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="outputColumn" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="dataType" type="{http://www.dmg.org/PMML-4_1}DATATYPE" />
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
    "fieldColumnPair",
    "tableLocator",
    "inlineTable"
})
@XmlRootElement(name = "MapValues", namespace = "http://www.dmg.org/PMML-4_1")
public class MapValues {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "FieldColumnPair", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected List<FieldColumnPair> fieldColumnPair;
    @XmlElement(name = "TableLocator", namespace = "http://www.dmg.org/PMML-4_1")
    protected TableLocator tableLocator;
    @XmlElement(name = "InlineTable", namespace = "http://www.dmg.org/PMML-4_1")
    protected InlineTable inlineTable;
    @XmlAttribute(name = "mapMissingTo")
    protected String mapMissingTo;
    @XmlAttribute(name = "defaultValue")
    protected String defaultValue;
    @XmlAttribute(name = "outputColumn", required = true)
    protected String outputColumn;
    @XmlAttribute(name = "dataType")
    protected DATATYPE dataType;

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
     * Gets the value of the fieldColumnPair property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fieldColumnPair property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFieldColumnPair().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FieldColumnPair }
     *
     *
     */
    public List<FieldColumnPair> getFieldColumnPair() {
        if (fieldColumnPair == null) {
            fieldColumnPair = new ArrayList<FieldColumnPair>();
        }
        return this.fieldColumnPair;
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
     * Gets the value of the mapMissingTo property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMapMissingTo() {
        return mapMissingTo;
    }

    /**
     * Sets the value of the mapMissingTo property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMapMissingTo(String value) {
        this.mapMissingTo = value;
    }

    /**
     * Gets the value of the defaultValue property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the value of the defaultValue property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    /**
     * Gets the value of the outputColumn property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOutputColumn() {
        return outputColumn;
    }

    /**
     * Sets the value of the outputColumn property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOutputColumn(String value) {
        this.outputColumn = value;
    }

    /**
     * Gets the value of the dataType property.
     *
     * @return
     *     possible object is
     *     {@link DATATYPE }
     *
     */
    public DATATYPE getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     *
     * @param value
     *     allowed object is
     *     {@link DATATYPE }
     *
     */
    public void setDataType(DATATYPE value) {
        this.dataType = value;
    }

}
