
package mms.common.models.pmml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
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
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Header"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}MiningBuildTask" minOccurs="0"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}DataDictionary"/>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}TransformationDictionary" minOccurs="0"/>
 *         &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *           &lt;group ref="{http://www.dmg.org/PMML-4_1}MODEL-ELEMENT"/>
 *         &lt;/sequence>
 *         &lt;element ref="{http://www.dmg.org/PMML-4_1}Extension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "header",
    "miningBuildTask",
    "dataDictionary",
    "transformationDictionary",
    "associationModelOrBaselineModelOrClusteringModel",
    "extension"
})
@XmlRootElement(name = "PMML", namespace = "http://www.dmg.org/PMML-4_1")
public class PMML {

    @XmlElement(name = "Header", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected Header header;
    @XmlElement(name = "MiningBuildTask", namespace = "http://www.dmg.org/PMML-4_1")
    protected MiningBuildTask miningBuildTask;
    @XmlElement(name = "DataDictionary", namespace = "http://www.dmg.org/PMML-4_1", required = true)
    protected DataDictionary dataDictionary;
    @XmlElement(name = "TransformationDictionary", namespace = "http://www.dmg.org/PMML-4_1")
    protected TransformationDictionary transformationDictionary;
    @XmlElements({
        @XmlElement(name = "AssociationModel", namespace = "http://www.dmg.org/PMML-4_1", type = AssociationModel.class),
        @XmlElement(name = "BaselineModel", namespace = "http://www.dmg.org/PMML-4_1", type = BaselineModel.class),
        @XmlElement(name = "ClusteringModel", namespace = "http://www.dmg.org/PMML-4_1", type = ClusteringModel.class),
        @XmlElement(name = "GeneralRegressionModel", namespace = "http://www.dmg.org/PMML-4_1", type = GeneralRegressionModel.class),
        @XmlElement(name = "MiningModel", namespace = "http://www.dmg.org/PMML-4_1", type = MiningModel.class),
        @XmlElement(name = "NaiveBayesModel", namespace = "http://www.dmg.org/PMML-4_1", type = NaiveBayesModel.class),
        @XmlElement(name = "NearestNeighborModel", namespace = "http://www.dmg.org/PMML-4_1", type = NearestNeighborModel.class),
        @XmlElement(name = "NeuralNetwork", namespace = "http://www.dmg.org/PMML-4_1", type = NeuralNetwork.class),
        @XmlElement(name = "RegressionModel", namespace = "http://www.dmg.org/PMML-4_1", type = RegressionModel.class),
        @XmlElement(name = "RuleSetModel", namespace = "http://www.dmg.org/PMML-4_1", type = RuleSetModel.class),
        @XmlElement(name = "SequenceModel", namespace = "http://www.dmg.org/PMML-4_1", type = SequenceModel.class),
        @XmlElement(name = "Scorecard", namespace = "http://www.dmg.org/PMML-4_1", type = Scorecard.class),
        @XmlElement(name = "SupportVectorMachineModel", namespace = "http://www.dmg.org/PMML-4_1", type = SupportVectorMachineModel.class),
        @XmlElement(name = "TextModel", namespace = "http://www.dmg.org/PMML-4_1", type = TextModel.class),
        @XmlElement(name = "TimeSeriesModel", namespace = "http://www.dmg.org/PMML-4_1", type = TimeSeriesModel.class),
        @XmlElement(name = "TreeModel", namespace = "http://www.dmg.org/PMML-4_1", type = TreeModel.class)
    })
    protected List<Object> associationModelOrBaselineModelOrClusteringModel;
    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link Header }
     *     
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link Header }
     *     
     */
    public void setHeader(Header value) {
        this.header = value;
    }

    /**
     * Gets the value of the miningBuildTask property.
     * 
     * @return
     *     possible object is
     *     {@link MiningBuildTask }
     *     
     */
    public MiningBuildTask getMiningBuildTask() {
        return miningBuildTask;
    }

    /**
     * Sets the value of the miningBuildTask property.
     * 
     * @param value
     *     allowed object is
     *     {@link MiningBuildTask }
     *     
     */
    public void setMiningBuildTask(MiningBuildTask value) {
        this.miningBuildTask = value;
    }

    /**
     * Gets the value of the dataDictionary property.
     * 
     * @return
     *     possible object is
     *     {@link DataDictionary }
     *     
     */
    public DataDictionary getDataDictionary() {
        return dataDictionary;
    }

    /**
     * Sets the value of the dataDictionary property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataDictionary }
     *     
     */
    public void setDataDictionary(DataDictionary value) {
        this.dataDictionary = value;
    }

    /**
     * Gets the value of the transformationDictionary property.
     * 
     * @return
     *     possible object is
     *     {@link TransformationDictionary }
     *     
     */
    public TransformationDictionary getTransformationDictionary() {
        return transformationDictionary;
    }

    /**
     * Sets the value of the transformationDictionary property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransformationDictionary }
     *     
     */
    public void setTransformationDictionary(TransformationDictionary value) {
        this.transformationDictionary = value;
    }

    /**
     * Gets the value of the associationModelOrBaselineModelOrClusteringModel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the associationModelOrBaselineModelOrClusteringModel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssociationModelOrBaselineModelOrClusteringModel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AssociationModel }
     * {@link BaselineModel }
     * {@link ClusteringModel }
     * {@link GeneralRegressionModel }
     * {@link MiningModel }
     * {@link NaiveBayesModel }
     * {@link NearestNeighborModel }
     * {@link NeuralNetwork }
     * {@link RegressionModel }
     * {@link RuleSetModel }
     * {@link SequenceModel }
     * {@link Scorecard }
     * {@link SupportVectorMachineModel }
     * {@link TextModel }
     * {@link TimeSeriesModel }
     * {@link TreeModel }
     * 
     * 
     */
    public List<Object> getAssociationModelOrBaselineModelOrClusteringModel() {
        if (associationModelOrBaselineModelOrClusteringModel == null) {
            associationModelOrBaselineModelOrClusteringModel = new ArrayList<Object>();
        }
        return this.associationModelOrBaselineModelOrClusteringModel;
    }

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
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
