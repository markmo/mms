
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
 *         &lt;group ref="{http://www.dmg.org/PMML-4_1}PREDICATE"/>
 *         &lt;group ref="{http://www.dmg.org/PMML-4_1}MODEL-ELEMENT"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="weight" type="{http://www.dmg.org/PMML-4_1}NUMBER" default="1" />
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
    "simplePredicate",
    "compoundPredicate",
    "simpleSetPredicate",
    "_true",
    "_false",
    "associationModel",
    "baselineModel",
    "clusteringModel",
    "generalRegressionModel",
    "miningModel",
    "naiveBayesModel",
    "nearestNeighborModel",
    "neuralNetwork",
    "regressionModel",
    "ruleSetModel",
    "sequenceModel",
    "scorecard",
    "supportVectorMachineModel",
    "textModel",
    "timeSeriesModel",
    "treeModel"
})
@XmlRootElement(name = "Segment", namespace = "http://www.dmg.org/PMML-4_1")
public class Segment {

    @XmlElement(name = "Extension", namespace = "http://www.dmg.org/PMML-4_1")
    protected List<Extension> extension;
    @XmlElement(name = "SimplePredicate", namespace = "http://www.dmg.org/PMML-4_1")
    protected SimplePredicate simplePredicate;
    @XmlElement(name = "CompoundPredicate", namespace = "http://www.dmg.org/PMML-4_1")
    protected CompoundPredicate compoundPredicate;
    @XmlElement(name = "SimpleSetPredicate", namespace = "http://www.dmg.org/PMML-4_1")
    protected SimpleSetPredicate simpleSetPredicate;
    @XmlElement(name = "True", namespace = "http://www.dmg.org/PMML-4_1")
    protected True _true;
    @XmlElement(name = "False", namespace = "http://www.dmg.org/PMML-4_1")
    protected False _false;
    @XmlElement(name = "AssociationModel", namespace = "http://www.dmg.org/PMML-4_1")
    protected AssociationModel associationModel;
    @XmlElement(name = "BaselineModel", namespace = "http://www.dmg.org/PMML-4_1")
    protected BaselineModel baselineModel;
    @XmlElement(name = "ClusteringModel", namespace = "http://www.dmg.org/PMML-4_1")
    protected ClusteringModel clusteringModel;
    @XmlElement(name = "GeneralRegressionModel", namespace = "http://www.dmg.org/PMML-4_1")
    protected GeneralRegressionModel generalRegressionModel;
    @XmlElement(name = "MiningModel", namespace = "http://www.dmg.org/PMML-4_1")
    protected MiningModel miningModel;
    @XmlElement(name = "NaiveBayesModel", namespace = "http://www.dmg.org/PMML-4_1")
    protected NaiveBayesModel naiveBayesModel;
    @XmlElement(name = "NearestNeighborModel", namespace = "http://www.dmg.org/PMML-4_1")
    protected NearestNeighborModel nearestNeighborModel;
    @XmlElement(name = "NeuralNetwork", namespace = "http://www.dmg.org/PMML-4_1")
    protected NeuralNetwork neuralNetwork;
    @XmlElement(name = "RegressionModel", namespace = "http://www.dmg.org/PMML-4_1")
    protected RegressionModel regressionModel;
    @XmlElement(name = "RuleSetModel", namespace = "http://www.dmg.org/PMML-4_1")
    protected RuleSetModel ruleSetModel;
    @XmlElement(name = "SequenceModel", namespace = "http://www.dmg.org/PMML-4_1")
    protected SequenceModel sequenceModel;
    @XmlElement(name = "Scorecard", namespace = "http://www.dmg.org/PMML-4_1")
    protected Scorecard scorecard;
    @XmlElement(name = "SupportVectorMachineModel", namespace = "http://www.dmg.org/PMML-4_1")
    protected SupportVectorMachineModel supportVectorMachineModel;
    @XmlElement(name = "TextModel", namespace = "http://www.dmg.org/PMML-4_1")
    protected TextModel textModel;
    @XmlElement(name = "TimeSeriesModel", namespace = "http://www.dmg.org/PMML-4_1")
    protected TimeSeriesModel timeSeriesModel;
    @XmlElement(name = "TreeModel", namespace = "http://www.dmg.org/PMML-4_1")
    protected TreeModel treeModel;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "weight")
    protected Double weight;

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
     * Gets the value of the simplePredicate property.
     *
     * @return
     *     possible object is
     *     {@link SimplePredicate }
     *
     */
    public SimplePredicate getSimplePredicate() {
        return simplePredicate;
    }

    /**
     * Sets the value of the simplePredicate property.
     *
     * @param value
     *     allowed object is
     *     {@link SimplePredicate }
     *
     */
    public void setSimplePredicate(SimplePredicate value) {
        this.simplePredicate = value;
    }

    /**
     * Gets the value of the compoundPredicate property.
     *
     * @return
     *     possible object is
     *     {@link CompoundPredicate }
     *
     */
    public CompoundPredicate getCompoundPredicate() {
        return compoundPredicate;
    }

    /**
     * Sets the value of the compoundPredicate property.
     *
     * @param value
     *     allowed object is
     *     {@link CompoundPredicate }
     *
     */
    public void setCompoundPredicate(CompoundPredicate value) {
        this.compoundPredicate = value;
    }

    /**
     * Gets the value of the simpleSetPredicate property.
     *
     * @return
     *     possible object is
     *     {@link SimpleSetPredicate }
     *
     */
    public SimpleSetPredicate getSimpleSetPredicate() {
        return simpleSetPredicate;
    }

    /**
     * Sets the value of the simpleSetPredicate property.
     *
     * @param value
     *     allowed object is
     *     {@link SimpleSetPredicate }
     *
     */
    public void setSimpleSetPredicate(SimpleSetPredicate value) {
        this.simpleSetPredicate = value;
    }

    /**
     * Gets the value of the true property.
     *
     * @return
     *     possible object is
     *     {@link True }
     *
     */
    public True getTrue() {
        return _true;
    }

    /**
     * Sets the value of the true property.
     *
     * @param value
     *     allowed object is
     *     {@link True }
     *
     */
    public void setTrue(True value) {
        this._true = value;
    }

    /**
     * Gets the value of the false property.
     *
     * @return
     *     possible object is
     *     {@link False }
     *
     */
    public False getFalse() {
        return _false;
    }

    /**
     * Sets the value of the false property.
     *
     * @param value
     *     allowed object is
     *     {@link False }
     *
     */
    public void setFalse(False value) {
        this._false = value;
    }

    /**
     * Gets the value of the associationModel property.
     *
     * @return
     *     possible object is
     *     {@link AssociationModel }
     *
     */
    public AssociationModel getAssociationModel() {
        return associationModel;
    }

    /**
     * Sets the value of the associationModel property.
     *
     * @param value
     *     allowed object is
     *     {@link AssociationModel }
     *
     */
    public void setAssociationModel(AssociationModel value) {
        this.associationModel = value;
    }

    /**
     * Gets the value of the baselineModel property.
     *
     * @return
     *     possible object is
     *     {@link BaselineModel }
     *
     */
    public BaselineModel getBaselineModel() {
        return baselineModel;
    }

    /**
     * Sets the value of the baselineModel property.
     *
     * @param value
     *     allowed object is
     *     {@link BaselineModel }
     *
     */
    public void setBaselineModel(BaselineModel value) {
        this.baselineModel = value;
    }

    /**
     * Gets the value of the clusteringModel property.
     *
     * @return
     *     possible object is
     *     {@link ClusteringModel }
     *
     */
    public ClusteringModel getClusteringModel() {
        return clusteringModel;
    }

    /**
     * Sets the value of the clusteringModel property.
     *
     * @param value
     *     allowed object is
     *     {@link ClusteringModel }
     *
     */
    public void setClusteringModel(ClusteringModel value) {
        this.clusteringModel = value;
    }

    /**
     * Gets the value of the generalRegressionModel property.
     *
     * @return
     *     possible object is
     *     {@link GeneralRegressionModel }
     *
     */
    public GeneralRegressionModel getGeneralRegressionModel() {
        return generalRegressionModel;
    }

    /**
     * Sets the value of the generalRegressionModel property.
     *
     * @param value
     *     allowed object is
     *     {@link GeneralRegressionModel }
     *
     */
    public void setGeneralRegressionModel(GeneralRegressionModel value) {
        this.generalRegressionModel = value;
    }

    /**
     * Gets the value of the miningModel property.
     *
     * @return
     *     possible object is
     *     {@link MiningModel }
     *
     */
    public MiningModel getMiningModel() {
        return miningModel;
    }

    /**
     * Sets the value of the miningModel property.
     *
     * @param value
     *     allowed object is
     *     {@link MiningModel }
     *
     */
    public void setMiningModel(MiningModel value) {
        this.miningModel = value;
    }

    /**
     * Gets the value of the naiveBayesModel property.
     *
     * @return
     *     possible object is
     *     {@link NaiveBayesModel }
     *
     */
    public NaiveBayesModel getNaiveBayesModel() {
        return naiveBayesModel;
    }

    /**
     * Sets the value of the naiveBayesModel property.
     *
     * @param value
     *     allowed object is
     *     {@link NaiveBayesModel }
     *
     */
    public void setNaiveBayesModel(NaiveBayesModel value) {
        this.naiveBayesModel = value;
    }

    /**
     * Gets the value of the nearestNeighborModel property.
     *
     * @return
     *     possible object is
     *     {@link NearestNeighborModel }
     *
     */
    public NearestNeighborModel getNearestNeighborModel() {
        return nearestNeighborModel;
    }

    /**
     * Sets the value of the nearestNeighborModel property.
     *
     * @param value
     *     allowed object is
     *     {@link NearestNeighborModel }
     *
     */
    public void setNearestNeighborModel(NearestNeighborModel value) {
        this.nearestNeighborModel = value;
    }

    /**
     * Gets the value of the neuralNetwork property.
     *
     * @return
     *     possible object is
     *     {@link NeuralNetwork }
     *
     */
    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    /**
     * Sets the value of the neuralNetwork property.
     *
     * @param value
     *     allowed object is
     *     {@link NeuralNetwork }
     *
     */
    public void setNeuralNetwork(NeuralNetwork value) {
        this.neuralNetwork = value;
    }

    /**
     * Gets the value of the regressionModel property.
     *
     * @return
     *     possible object is
     *     {@link RegressionModel }
     *
     */
    public RegressionModel getRegressionModel() {
        return regressionModel;
    }

    /**
     * Sets the value of the regressionModel property.
     *
     * @param value
     *     allowed object is
     *     {@link RegressionModel }
     *
     */
    public void setRegressionModel(RegressionModel value) {
        this.regressionModel = value;
    }

    /**
     * Gets the value of the ruleSetModel property.
     *
     * @return
     *     possible object is
     *     {@link RuleSetModel }
     *
     */
    public RuleSetModel getRuleSetModel() {
        return ruleSetModel;
    }

    /**
     * Sets the value of the ruleSetModel property.
     *
     * @param value
     *     allowed object is
     *     {@link RuleSetModel }
     *
     */
    public void setRuleSetModel(RuleSetModel value) {
        this.ruleSetModel = value;
    }

    /**
     * Gets the value of the sequenceModel property.
     *
     * @return
     *     possible object is
     *     {@link SequenceModel }
     *
     */
    public SequenceModel getSequenceModel() {
        return sequenceModel;
    }

    /**
     * Sets the value of the sequenceModel property.
     *
     * @param value
     *     allowed object is
     *     {@link SequenceModel }
     *
     */
    public void setSequenceModel(SequenceModel value) {
        this.sequenceModel = value;
    }

    /**
     * Gets the value of the scorecard property.
     *
     * @return
     *     possible object is
     *     {@link Scorecard }
     *
     */
    public Scorecard getScorecard() {
        return scorecard;
    }

    /**
     * Sets the value of the scorecard property.
     *
     * @param value
     *     allowed object is
     *     {@link Scorecard }
     *
     */
    public void setScorecard(Scorecard value) {
        this.scorecard = value;
    }

    /**
     * Gets the value of the supportVectorMachineModel property.
     *
     * @return
     *     possible object is
     *     {@link SupportVectorMachineModel }
     *
     */
    public SupportVectorMachineModel getSupportVectorMachineModel() {
        return supportVectorMachineModel;
    }

    /**
     * Sets the value of the supportVectorMachineModel property.
     *
     * @param value
     *     allowed object is
     *     {@link SupportVectorMachineModel }
     *
     */
    public void setSupportVectorMachineModel(SupportVectorMachineModel value) {
        this.supportVectorMachineModel = value;
    }

    /**
     * Gets the value of the textModel property.
     *
     * @return
     *     possible object is
     *     {@link TextModel }
     *
     */
    public TextModel getTextModel() {
        return textModel;
    }

    /**
     * Sets the value of the textModel property.
     *
     * @param value
     *     allowed object is
     *     {@link TextModel }
     *
     */
    public void setTextModel(TextModel value) {
        this.textModel = value;
    }

    /**
     * Gets the value of the timeSeriesModel property.
     *
     * @return
     *     possible object is
     *     {@link TimeSeriesModel }
     *
     */
    public TimeSeriesModel getTimeSeriesModel() {
        return timeSeriesModel;
    }

    /**
     * Sets the value of the timeSeriesModel property.
     *
     * @param value
     *     allowed object is
     *     {@link TimeSeriesModel }
     *
     */
    public void setTimeSeriesModel(TimeSeriesModel value) {
        this.timeSeriesModel = value;
    }

    /**
     * Gets the value of the treeModel property.
     *
     * @return
     *     possible object is
     *     {@link TreeModel }
     *
     */
    public TreeModel getTreeModel() {
        return treeModel;
    }

    /**
     * Sets the value of the treeModel property.
     *
     * @param value
     *     allowed object is
     *     {@link TreeModel }
     *
     */
    public void setTreeModel(TreeModel value) {
        this.treeModel = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the weight property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     *
     */
    public double getWeight() {
        if (weight == null) {
            return  1.0D;
        } else {
            return weight;
        }
    }

    /**
     * Sets the value of the weight property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
    public void setWeight(Double value) {
        this.weight = value;
    }

}
