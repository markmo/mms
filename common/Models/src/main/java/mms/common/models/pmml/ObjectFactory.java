
package mms.common.models.pmml;

import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mms.common.models.pmml package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SpectralAnalysis_QNAME = new QName("http://www.dmg.org/PMML-4_1", "SpectralAnalysis");
    private final static QName _INTEntries_QNAME = new QName("http://www.dmg.org/PMML-4_1", "INT-Entries");
    private final static QName _REALEntries_QNAME = new QName("http://www.dmg.org/PMML-4_1", "REAL-Entries");
    private final static QName _CountTable_QNAME = new QName("http://www.dmg.org/PMML-4_1", "CountTable");
    private final static QName _ARIMA_QNAME = new QName("http://www.dmg.org/PMML-4_1", "ARIMA");
    private final static QName _Indices_QNAME = new QName("http://www.dmg.org/PMML-4_1", "Indices");
    private final static QName _SeasonalTrendDecomposition_QNAME = new QName("http://www.dmg.org/PMML-4_1", "SeasonalTrendDecomposition");
    private final static QName _NormalizedCountTable_QNAME = new QName("http://www.dmg.org/PMML-4_1", "NormalizedCountTable");
    private final static QName _Array_QNAME = new QName("http://www.dmg.org/PMML-4_1", "Array");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mms.common.models.pmml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Characteristic }
     * 
     */
    public Characteristic createCharacteristic() {
        return new Characteristic();
    }

    /**
     * Create an instance of {@link Extension }
     * 
     */
    public Extension createExtension() {
        return new Extension();
    }

    /**
     * Create an instance of {@link Attribute }
     * 
     */
    public Attribute createAttribute() {
        return new Attribute();
    }

    /**
     * Create an instance of {@link SimplePredicate }
     * 
     */
    public SimplePredicate createSimplePredicate() {
        return new SimplePredicate();
    }

    /**
     * Create an instance of {@link CompoundPredicate }
     * 
     */
    public CompoundPredicate createCompoundPredicate() {
        return new CompoundPredicate();
    }

    /**
     * Create an instance of {@link SimpleSetPredicate }
     * 
     */
    public SimpleSetPredicate createSimpleSetPredicate() {
        return new SimpleSetPredicate();
    }

    /**
     * Create an instance of {@link ArrayType }
     * 
     */
    public ArrayType createArrayType() {
        return new ArrayType();
    }

    /**
     * Create an instance of {@link True }
     * 
     */
    public True createTrue() {
        return new True();
    }

    /**
     * Create an instance of {@link False }
     * 
     */
    public False createFalse() {
        return new False();
    }

    /**
     * Create an instance of {@link MiningSchema }
     * 
     */
    public MiningSchema createMiningSchema() {
        return new MiningSchema();
    }

    /**
     * Create an instance of {@link MiningField }
     * 
     */
    public MiningField createMiningField() {
        return new MiningField();
    }

    /**
     * Create an instance of {@link TextModelSimiliarity }
     * 
     */
    public TextModelSimiliarity createTextModelSimiliarity() {
        return new TextModelSimiliarity();
    }

    /**
     * Create an instance of {@link TimeCycle }
     * 
     */
    public TimeCycle createTimeCycle() {
        return new TimeCycle();
    }

    /**
     * Create an instance of {@link Characteristics }
     * 
     */
    public Characteristics createCharacteristics() {
        return new Characteristics();
    }

    /**
     * Create an instance of {@link PCell }
     * 
     */
    public PCell createPCell() {
        return new PCell();
    }

    /**
     * Create an instance of {@link Matrix }
     * 
     */
    public Matrix createMatrix() {
        return new Matrix();
    }

    /**
     * Create an instance of {@link MatCell }
     * 
     */
    public MatCell createMatCell() {
        return new MatCell();
    }

    /**
     * Create an instance of {@link ItemRef }
     * 
     */
    public ItemRef createItemRef() {
        return new ItemRef();
    }

    /**
     * Create an instance of {@link LiftGraph }
     * 
     */
    public LiftGraph createLiftGraph() {
        return new LiftGraph();
    }

    /**
     * Create an instance of {@link XCoordinates }
     * 
     */
    public XCoordinates createXCoordinates() {
        return new XCoordinates();
    }

    /**
     * Create an instance of {@link YCoordinates }
     * 
     */
    public YCoordinates createYCoordinates() {
        return new YCoordinates();
    }

    /**
     * Create an instance of {@link BoundaryValues }
     * 
     */
    public BoundaryValues createBoundaryValues() {
        return new BoundaryValues();
    }

    /**
     * Create an instance of {@link BoundaryValueMeans }
     * 
     */
    public BoundaryValueMeans createBoundaryValueMeans() {
        return new BoundaryValueMeans();
    }

    /**
     * Create an instance of {@link ClusteringModel }
     * 
     */
    public ClusteringModel createClusteringModel() {
        return new ClusteringModel();
    }

    /**
     * Create an instance of {@link Output }
     * 
     */
    public Output createOutput() {
        return new Output();
    }

    /**
     * Create an instance of {@link OutputField }
     * 
     */
    public OutputField createOutputField() {
        return new OutputField();
    }

    /**
     * Create an instance of {@link Decisions }
     * 
     */
    public Decisions createDecisions() {
        return new Decisions();
    }

    /**
     * Create an instance of {@link Decision }
     * 
     */
    public Decision createDecision() {
        return new Decision();
    }

    /**
     * Create an instance of {@link Constant }
     * 
     */
    public Constant createConstant() {
        return new Constant();
    }

    /**
     * Create an instance of {@link FieldRef }
     * 
     */
    public FieldRef createFieldRef() {
        return new FieldRef();
    }

    /**
     * Create an instance of {@link NormContinuous }
     * 
     */
    public NormContinuous createNormContinuous() {
        return new NormContinuous();
    }

    /**
     * Create an instance of {@link LinearNorm }
     * 
     */
    public LinearNorm createLinearNorm() {
        return new LinearNorm();
    }

    /**
     * Create an instance of {@link NormDiscrete }
     * 
     */
    public NormDiscrete createNormDiscrete() {
        return new NormDiscrete();
    }

    /**
     * Create an instance of {@link Discretize }
     * 
     */
    public Discretize createDiscretize() {
        return new Discretize();
    }

    /**
     * Create an instance of {@link DiscretizeBin }
     * 
     */
    public DiscretizeBin createDiscretizeBin() {
        return new DiscretizeBin();
    }

    /**
     * Create an instance of {@link Interval }
     * 
     */
    public Interval createInterval() {
        return new Interval();
    }

    /**
     * Create an instance of {@link MapValues }
     * 
     */
    public MapValues createMapValues() {
        return new MapValues();
    }

    /**
     * Create an instance of {@link FieldColumnPair }
     * 
     */
    public FieldColumnPair createFieldColumnPair() {
        return new FieldColumnPair();
    }

    /**
     * Create an instance of {@link TableLocator }
     * 
     */
    public TableLocator createTableLocator() {
        return new TableLocator();
    }

    /**
     * Create an instance of {@link InlineTable }
     * 
     */
    public InlineTable createInlineTable() {
        return new InlineTable();
    }

    /**
     * Create an instance of {@link Row }
     * 
     */
    public Row createRow() {
        return new Row();
    }

    /**
     * Create an instance of {@link Apply }
     * 
     */
    public Apply createApply() {
        return new Apply();
    }

    /**
     * Create an instance of {@link Aggregate }
     * 
     */
    public Aggregate createAggregate() {
        return new Aggregate();
    }

    /**
     * Create an instance of {@link ModelStats }
     * 
     */
    public ModelStats createModelStats() {
        return new ModelStats();
    }

    /**
     * Create an instance of {@link UnivariateStats }
     * 
     */
    public UnivariateStats createUnivariateStats() {
        return new UnivariateStats();
    }

    /**
     * Create an instance of {@link Counts }
     * 
     */
    public Counts createCounts() {
        return new Counts();
    }

    /**
     * Create an instance of {@link NumericInfo }
     * 
     */
    public NumericInfo createNumericInfo() {
        return new NumericInfo();
    }

    /**
     * Create an instance of {@link Quantile }
     * 
     */
    public Quantile createQuantile() {
        return new Quantile();
    }

    /**
     * Create an instance of {@link DiscrStats }
     * 
     */
    public DiscrStats createDiscrStats() {
        return new DiscrStats();
    }

    /**
     * Create an instance of {@link ContStats }
     * 
     */
    public ContStats createContStats() {
        return new ContStats();
    }

    /**
     * Create an instance of {@link Anova }
     * 
     */
    public Anova createAnova() {
        return new Anova();
    }

    /**
     * Create an instance of {@link AnovaRow }
     * 
     */
    public AnovaRow createAnovaRow() {
        return new AnovaRow();
    }

    /**
     * Create an instance of {@link MultivariateStats }
     * 
     */
    public MultivariateStats createMultivariateStats() {
        return new MultivariateStats();
    }

    /**
     * Create an instance of {@link MultivariateStat }
     * 
     */
    public MultivariateStat createMultivariateStat() {
        return new MultivariateStat();
    }

    /**
     * Create an instance of {@link ModelExplanation }
     * 
     */
    public ModelExplanation createModelExplanation() {
        return new ModelExplanation();
    }

    /**
     * Create an instance of {@link PredictiveModelQuality }
     * 
     */
    public PredictiveModelQuality createPredictiveModelQuality() {
        return new PredictiveModelQuality();
    }

    /**
     * Create an instance of {@link ConfusionMatrix }
     * 
     */
    public ConfusionMatrix createConfusionMatrix() {
        return new ConfusionMatrix();
    }

    /**
     * Create an instance of {@link ClassLabels }
     * 
     */
    public ClassLabels createClassLabels() {
        return new ClassLabels();
    }

    /**
     * Create an instance of {@link LiftData }
     * 
     */
    public LiftData createLiftData() {
        return new LiftData();
    }

    /**
     * Create an instance of {@link ModelLiftGraph }
     * 
     */
    public ModelLiftGraph createModelLiftGraph() {
        return new ModelLiftGraph();
    }

    /**
     * Create an instance of {@link OptimumLiftGraph }
     * 
     */
    public OptimumLiftGraph createOptimumLiftGraph() {
        return new OptimumLiftGraph();
    }

    /**
     * Create an instance of {@link RandomLiftGraph }
     * 
     */
    public RandomLiftGraph createRandomLiftGraph() {
        return new RandomLiftGraph();
    }

    /**
     * Create an instance of {@link ROC }
     * 
     */
    public ROC createROC() {
        return new ROC();
    }

    /**
     * Create an instance of {@link ROCGraph }
     * 
     */
    public ROCGraph createROCGraph() {
        return new ROCGraph();
    }

    /**
     * Create an instance of {@link ClusteringModelQuality }
     * 
     */
    public ClusteringModelQuality createClusteringModelQuality() {
        return new ClusteringModelQuality();
    }

    /**
     * Create an instance of {@link Correlations }
     * 
     */
    public Correlations createCorrelations() {
        return new Correlations();
    }

    /**
     * Create an instance of {@link CorrelationFields }
     * 
     */
    public CorrelationFields createCorrelationFields() {
        return new CorrelationFields();
    }

    /**
     * Create an instance of {@link CorrelationValues }
     * 
     */
    public CorrelationValues createCorrelationValues() {
        return new CorrelationValues();
    }

    /**
     * Create an instance of {@link CorrelationMethods }
     * 
     */
    public CorrelationMethods createCorrelationMethods() {
        return new CorrelationMethods();
    }

    /**
     * Create an instance of {@link LocalTransformations }
     * 
     */
    public LocalTransformations createLocalTransformations() {
        return new LocalTransformations();
    }

    /**
     * Create an instance of {@link DerivedField }
     * 
     */
    public DerivedField createDerivedField() {
        return new DerivedField();
    }

    /**
     * Create an instance of {@link Value }
     * 
     */
    public Value createValue() {
        return new Value();
    }

    /**
     * Create an instance of {@link ComparisonMeasure }
     * 
     */
    public ComparisonMeasure createComparisonMeasure() {
        return new ComparisonMeasure();
    }

    /**
     * Create an instance of {@link Euclidean }
     * 
     */
    public Euclidean createEuclidean() {
        return new Euclidean();
    }

    /**
     * Create an instance of {@link SquaredEuclidean }
     * 
     */
    public SquaredEuclidean createSquaredEuclidean() {
        return new SquaredEuclidean();
    }

    /**
     * Create an instance of {@link Chebychev }
     * 
     */
    public Chebychev createChebychev() {
        return new Chebychev();
    }

    /**
     * Create an instance of {@link CityBlock }
     * 
     */
    public CityBlock createCityBlock() {
        return new CityBlock();
    }

    /**
     * Create an instance of {@link Minkowski }
     * 
     */
    public Minkowski createMinkowski() {
        return new Minkowski();
    }

    /**
     * Create an instance of {@link SimpleMatching }
     * 
     */
    public SimpleMatching createSimpleMatching() {
        return new SimpleMatching();
    }

    /**
     * Create an instance of {@link Jaccard }
     * 
     */
    public Jaccard createJaccard() {
        return new Jaccard();
    }

    /**
     * Create an instance of {@link Tanimoto }
     * 
     */
    public Tanimoto createTanimoto() {
        return new Tanimoto();
    }

    /**
     * Create an instance of {@link BinarySimilarity }
     * 
     */
    public BinarySimilarity createBinarySimilarity() {
        return new BinarySimilarity();
    }

    /**
     * Create an instance of {@link ClusteringField }
     * 
     */
    public ClusteringField createClusteringField() {
        return new ClusteringField();
    }

    /**
     * Create an instance of {@link Comparisons }
     * 
     */
    public Comparisons createComparisons() {
        return new Comparisons();
    }

    /**
     * Create an instance of {@link MissingValueWeights }
     * 
     */
    public MissingValueWeights createMissingValueWeights() {
        return new MissingValueWeights();
    }

    /**
     * Create an instance of {@link Cluster }
     * 
     */
    public Cluster createCluster() {
        return new Cluster();
    }

    /**
     * Create an instance of {@link KohonenMap }
     * 
     */
    public KohonenMap createKohonenMap() {
        return new KohonenMap();
    }

    /**
     * Create an instance of {@link Partition }
     * 
     */
    public Partition createPartition() {
        return new Partition();
    }

    /**
     * Create an instance of {@link PartitionFieldStats }
     * 
     */
    public PartitionFieldStats createPartitionFieldStats() {
        return new PartitionFieldStats();
    }

    /**
     * Create an instance of {@link Covariances }
     * 
     */
    public Covariances createCovariances() {
        return new Covariances();
    }

    /**
     * Create an instance of {@link ModelVerification }
     * 
     */
    public ModelVerification createModelVerification() {
        return new ModelVerification();
    }

    /**
     * Create an instance of {@link VerificationFields }
     * 
     */
    public VerificationFields createVerificationFields() {
        return new VerificationFields();
    }

    /**
     * Create an instance of {@link VerificationField }
     * 
     */
    public VerificationField createVerificationField() {
        return new VerificationField();
    }

    /**
     * Create an instance of {@link LinearKernelType }
     * 
     */
    public LinearKernelType createLinearKernelType() {
        return new LinearKernelType();
    }

    /**
     * Create an instance of {@link SupportVectorMachineModel }
     * 
     */
    public SupportVectorMachineModel createSupportVectorMachineModel() {
        return new SupportVectorMachineModel();
    }

    /**
     * Create an instance of {@link Targets }
     * 
     */
    public Targets createTargets() {
        return new Targets();
    }

    /**
     * Create an instance of {@link Target }
     * 
     */
    public Target createTarget() {
        return new Target();
    }

    /**
     * Create an instance of {@link TargetValue }
     * 
     */
    public TargetValue createTargetValue() {
        return new TargetValue();
    }

    /**
     * Create an instance of {@link PolynomialKernelType }
     * 
     */
    public PolynomialKernelType createPolynomialKernelType() {
        return new PolynomialKernelType();
    }

    /**
     * Create an instance of {@link RadialBasisKernelType }
     * 
     */
    public RadialBasisKernelType createRadialBasisKernelType() {
        return new RadialBasisKernelType();
    }

    /**
     * Create an instance of {@link SigmoidKernelType }
     * 
     */
    public SigmoidKernelType createSigmoidKernelType() {
        return new SigmoidKernelType();
    }

    /**
     * Create an instance of {@link VectorDictionary }
     * 
     */
    public VectorDictionary createVectorDictionary() {
        return new VectorDictionary();
    }

    /**
     * Create an instance of {@link VectorFields }
     * 
     */
    public VectorFields createVectorFields() {
        return new VectorFields();
    }

    /**
     * Create an instance of {@link VectorInstance }
     * 
     */
    public VectorInstance createVectorInstance() {
        return new VectorInstance();
    }

    /**
     * Create an instance of {@link REALSparseArray }
     * 
     */
    public REALSparseArray createREALSparseArray() {
        return new REALSparseArray();
    }

    /**
     * Create an instance of {@link SupportVectorMachine }
     * 
     */
    public SupportVectorMachine createSupportVectorMachine() {
        return new SupportVectorMachine();
    }

    /**
     * Create an instance of {@link SupportVectors }
     * 
     */
    public SupportVectors createSupportVectors() {
        return new SupportVectors();
    }

    /**
     * Create an instance of {@link SupportVector }
     * 
     */
    public SupportVector createSupportVector() {
        return new SupportVector();
    }

    /**
     * Create an instance of {@link Coefficients }
     * 
     */
    public Coefficients createCoefficients() {
        return new Coefficients();
    }

    /**
     * Create an instance of {@link Coefficient }
     * 
     */
    public Coefficient createCoefficient() {
        return new Coefficient();
    }

    /**
     * Create an instance of {@link NeuralOutput }
     * 
     */
    public NeuralOutput createNeuralOutput() {
        return new NeuralOutput();
    }

    /**
     * Create an instance of {@link Item }
     * 
     */
    public Item createItem() {
        return new Item();
    }

    /**
     * Create an instance of {@link TimeSeries }
     * 
     */
    public TimeSeries createTimeSeries() {
        return new TimeSeries();
    }

    /**
     * Create an instance of {@link TimeAnchor }
     * 
     */
    public TimeAnchor createTimeAnchor() {
        return new TimeAnchor();
    }

    /**
     * Create an instance of {@link TimeException }
     * 
     */
    public TimeException createTimeException() {
        return new TimeException();
    }

    /**
     * Create an instance of {@link TimeValue }
     * 
     */
    public TimeValue createTimeValue() {
        return new TimeValue();
    }

    /**
     * Create an instance of {@link Timestamp }
     * 
     */
    public Timestamp createTimestamp() {
        return new Timestamp();
    }

    /**
     * Create an instance of {@link NeuralInput }
     * 
     */
    public NeuralInput createNeuralInput() {
        return new NeuralInput();
    }

    /**
     * Create an instance of {@link ParamMatrix }
     * 
     */
    public ParamMatrix createParamMatrix() {
        return new ParamMatrix();
    }

    /**
     * Create an instance of {@link PCovCell }
     * 
     */
    public PCovCell createPCovCell() {
        return new PCovCell();
    }

    /**
     * Create an instance of {@link BaselineModel }
     * 
     */
    public BaselineModel createBaselineModel() {
        return new BaselineModel();
    }

    /**
     * Create an instance of {@link TestDistributions }
     * 
     */
    public TestDistributions createTestDistributions() {
        return new TestDistributions();
    }

    /**
     * Create an instance of {@link Baseline }
     * 
     */
    public Baseline createBaseline() {
        return new Baseline();
    }

    /**
     * Create an instance of {@link AnyDistribution }
     * 
     */
    public AnyDistribution createAnyDistribution() {
        return new AnyDistribution();
    }

    /**
     * Create an instance of {@link GaussianDistribution }
     * 
     */
    public GaussianDistribution createGaussianDistribution() {
        return new GaussianDistribution();
    }

    /**
     * Create an instance of {@link PoissonDistribution }
     * 
     */
    public PoissonDistribution createPoissonDistribution() {
        return new PoissonDistribution();
    }

    /**
     * Create an instance of {@link UniformDistribution }
     * 
     */
    public UniformDistribution createUniformDistribution() {
        return new UniformDistribution();
    }

    /**
     * Create an instance of {@link COUNTTABLETYPE }
     * 
     */
    public COUNTTABLETYPE createCOUNTTABLETYPE() {
        return new COUNTTABLETYPE();
    }

    /**
     * Create an instance of {@link Alternate }
     * 
     */
    public Alternate createAlternate() {
        return new Alternate();
    }

    /**
     * Create an instance of {@link RuleSetModel }
     * 
     */
    public RuleSetModel createRuleSetModel() {
        return new RuleSetModel();
    }

    /**
     * Create an instance of {@link RuleSet }
     * 
     */
    public RuleSet createRuleSet() {
        return new RuleSet();
    }

    /**
     * Create an instance of {@link RuleSelectionMethod }
     * 
     */
    public RuleSelectionMethod createRuleSelectionMethod() {
        return new RuleSelectionMethod();
    }

    /**
     * Create an instance of {@link ScoreDistribution }
     * 
     */
    public ScoreDistribution createScoreDistribution() {
        return new ScoreDistribution();
    }

    /**
     * Create an instance of {@link SimpleRule }
     * 
     */
    public SimpleRule createSimpleRule() {
        return new SimpleRule();
    }

    /**
     * Create an instance of {@link CompoundRule }
     * 
     */
    public CompoundRule createCompoundRule() {
        return new CompoundRule();
    }

    /**
     * Create an instance of {@link Node }
     * 
     */
    public Node createNode() {
        return new Node();
    }

    /**
     * Create an instance of {@link Regression }
     * 
     */
    public Regression createRegression() {
        return new Regression();
    }

    /**
     * Create an instance of {@link ResultField }
     * 
     */
    public ResultField createResultField() {
        return new ResultField();
    }

    /**
     * Create an instance of {@link RegressionTable }
     * 
     */
    public RegressionTable createRegressionTable() {
        return new RegressionTable();
    }

    /**
     * Create an instance of {@link NumericPredictor }
     * 
     */
    public NumericPredictor createNumericPredictor() {
        return new NumericPredictor();
    }

    /**
     * Create an instance of {@link CategoricalPredictor }
     * 
     */
    public CategoricalPredictor createCategoricalPredictor() {
        return new CategoricalPredictor();
    }

    /**
     * Create an instance of {@link PredictorTerm }
     * 
     */
    public PredictorTerm createPredictorTerm() {
        return new PredictorTerm();
    }

    /**
     * Create an instance of {@link DecisionTree }
     * 
     */
    public DecisionTree createDecisionTree() {
        return new DecisionTree();
    }

    /**
     * Create an instance of {@link EventValues }
     * 
     */
    public EventValues createEventValues() {
        return new EventValues();
    }

    /**
     * Create an instance of {@link BayesInput }
     * 
     */
    public BayesInput createBayesInput() {
        return new BayesInput();
    }

    /**
     * Create an instance of {@link PairCounts }
     * 
     */
    public PairCounts createPairCounts() {
        return new PairCounts();
    }

    /**
     * Create an instance of {@link TargetValueCounts }
     * 
     */
    public TargetValueCounts createTargetValueCounts() {
        return new TargetValueCounts();
    }

    /**
     * Create an instance of {@link TargetValueCount }
     * 
     */
    public TargetValueCount createTargetValueCount() {
        return new TargetValueCount();
    }

    /**
     * Create an instance of {@link Header }
     * 
     */
    public Header createHeader() {
        return new Header();
    }

    /**
     * Create an instance of {@link Application }
     * 
     */
    public Application createApplication() {
        return new Application();
    }

    /**
     * Create an instance of {@link Annotation }
     * 
     */
    public Annotation createAnnotation() {
        return new Annotation();
    }

    /**
     * Create an instance of {@link PPMatrix }
     * 
     */
    public PPMatrix createPPMatrix() {
        return new PPMatrix();
    }

    /**
     * Create an instance of {@link PPCell }
     * 
     */
    public PPCell createPPCell() {
        return new PPCell();
    }

    /**
     * Create an instance of {@link SeasonalityExpoSmooth }
     * 
     */
    public SeasonalityExpoSmooth createSeasonalityExpoSmooth() {
        return new SeasonalityExpoSmooth();
    }

    /**
     * Create an instance of {@link NearestNeighborModel }
     * 
     */
    public NearestNeighborModel createNearestNeighborModel() {
        return new NearestNeighborModel();
    }

    /**
     * Create an instance of {@link TrainingInstances }
     * 
     */
    public TrainingInstances createTrainingInstances() {
        return new TrainingInstances();
    }

    /**
     * Create an instance of {@link InstanceFields }
     * 
     */
    public InstanceFields createInstanceFields() {
        return new InstanceFields();
    }

    /**
     * Create an instance of {@link InstanceField }
     * 
     */
    public InstanceField createInstanceField() {
        return new InstanceField();
    }

    /**
     * Create an instance of {@link KNNInputs }
     * 
     */
    public KNNInputs createKNNInputs() {
        return new KNNInputs();
    }

    /**
     * Create an instance of {@link KNNInput }
     * 
     */
    public KNNInput createKNNInput() {
        return new KNNInput();
    }

    /**
     * Create an instance of {@link Segmentation }
     * 
     */
    public Segmentation createSegmentation() {
        return new Segmentation();
    }

    /**
     * Create an instance of {@link Segment }
     * 
     */
    public Segment createSegment() {
        return new Segment();
    }

    /**
     * Create an instance of {@link AssociationModel }
     * 
     */
    public AssociationModel createAssociationModel() {
        return new AssociationModel();
    }

    /**
     * Create an instance of {@link Itemset }
     * 
     */
    public Itemset createItemset() {
        return new Itemset();
    }

    /**
     * Create an instance of {@link AssociationRule }
     * 
     */
    public AssociationRule createAssociationRule() {
        return new AssociationRule();
    }

    /**
     * Create an instance of {@link GeneralRegressionModel }
     * 
     */
    public GeneralRegressionModel createGeneralRegressionModel() {
        return new GeneralRegressionModel();
    }

    /**
     * Create an instance of {@link ParameterList }
     * 
     */
    public ParameterList createParameterList() {
        return new ParameterList();
    }

    /**
     * Create an instance of {@link Parameter }
     * 
     */
    public Parameter createParameter() {
        return new Parameter();
    }

    /**
     * Create an instance of {@link FactorList }
     * 
     */
    public FactorList createFactorList() {
        return new FactorList();
    }

    /**
     * Create an instance of {@link Predictor }
     * 
     */
    public Predictor createPredictor() {
        return new Predictor();
    }

    /**
     * Create an instance of {@link Categories }
     * 
     */
    public Categories createCategories() {
        return new Categories();
    }

    /**
     * Create an instance of {@link Category }
     * 
     */
    public Category createCategory() {
        return new Category();
    }

    /**
     * Create an instance of {@link CovariateList }
     * 
     */
    public CovariateList createCovariateList() {
        return new CovariateList();
    }

    /**
     * Create an instance of {@link PCovMatrix }
     * 
     */
    public PCovMatrix createPCovMatrix() {
        return new PCovMatrix();
    }

    /**
     * Create an instance of {@link BaseCumHazardTables }
     * 
     */
    public BaseCumHazardTables createBaseCumHazardTables() {
        return new BaseCumHazardTables();
    }

    /**
     * Create an instance of {@link BaselineStratum }
     * 
     */
    public BaselineStratum createBaselineStratum() {
        return new BaselineStratum();
    }

    /**
     * Create an instance of {@link BaselineCell }
     * 
     */
    public BaselineCell createBaselineCell() {
        return new BaselineCell();
    }

    /**
     * Create an instance of {@link MiningModel }
     * 
     */
    public MiningModel createMiningModel() {
        return new MiningModel();
    }

    /**
     * Create an instance of {@link NaiveBayesModel }
     * 
     */
    public NaiveBayesModel createNaiveBayesModel() {
        return new NaiveBayesModel();
    }

    /**
     * Create an instance of {@link BayesInputs }
     * 
     */
    public BayesInputs createBayesInputs() {
        return new BayesInputs();
    }

    /**
     * Create an instance of {@link BayesOutput }
     * 
     */
    public BayesOutput createBayesOutput() {
        return new BayesOutput();
    }

    /**
     * Create an instance of {@link NeuralNetwork }
     * 
     */
    public NeuralNetwork createNeuralNetwork() {
        return new NeuralNetwork();
    }

    /**
     * Create an instance of {@link NeuralInputs }
     * 
     */
    public NeuralInputs createNeuralInputs() {
        return new NeuralInputs();
    }

    /**
     * Create an instance of {@link NeuralLayer }
     * 
     */
    public NeuralLayer createNeuralLayer() {
        return new NeuralLayer();
    }

    /**
     * Create an instance of {@link Neuron }
     * 
     */
    public Neuron createNeuron() {
        return new Neuron();
    }

    /**
     * Create an instance of {@link Con }
     * 
     */
    public Con createCon() {
        return new Con();
    }

    /**
     * Create an instance of {@link NeuralOutputs }
     * 
     */
    public NeuralOutputs createNeuralOutputs() {
        return new NeuralOutputs();
    }

    /**
     * Create an instance of {@link RegressionModel }
     * 
     */
    public RegressionModel createRegressionModel() {
        return new RegressionModel();
    }

    /**
     * Create an instance of {@link SequenceModel }
     * 
     */
    public SequenceModel createSequenceModel() {
        return new SequenceModel();
    }

    /**
     * Create an instance of {@link Constraints }
     * 
     */
    public Constraints createConstraints() {
        return new Constraints();
    }

    /**
     * Create an instance of {@link SetPredicate }
     * 
     */
    public SetPredicate createSetPredicate() {
        return new SetPredicate();
    }

    /**
     * Create an instance of {@link Sequence }
     * 
     */
    public Sequence createSequence() {
        return new Sequence();
    }

    /**
     * Create an instance of {@link SetReference }
     * 
     */
    public SetReference createSetReference() {
        return new SetReference();
    }

    /**
     * Create an instance of {@link Delimiter }
     * 
     */
    public Delimiter createDelimiter() {
        return new Delimiter();
    }

    /**
     * Create an instance of {@link Time }
     * 
     */
    public Time createTime() {
        return new Time();
    }

    /**
     * Create an instance of {@link SequenceRule }
     * 
     */
    public SequenceRule createSequenceRule() {
        return new SequenceRule();
    }

    /**
     * Create an instance of {@link AntecedentSequence }
     * 
     */
    public AntecedentSequence createAntecedentSequence() {
        return new AntecedentSequence();
    }

    /**
     * Create an instance of {@link SequenceReference }
     * 
     */
    public SequenceReference createSequenceReference() {
        return new SequenceReference();
    }

    /**
     * Create an instance of {@link ConsequentSequence }
     * 
     */
    public ConsequentSequence createConsequentSequence() {
        return new ConsequentSequence();
    }

    /**
     * Create an instance of {@link Scorecard }
     * 
     */
    public Scorecard createScorecard() {
        return new Scorecard();
    }

    /**
     * Create an instance of {@link TextModel }
     * 
     */
    public TextModel createTextModel() {
        return new TextModel();
    }

    /**
     * Create an instance of {@link TextDictionary }
     * 
     */
    public TextDictionary createTextDictionary() {
        return new TextDictionary();
    }

    /**
     * Create an instance of {@link Taxonomy }
     * 
     */
    public Taxonomy createTaxonomy() {
        return new Taxonomy();
    }

    /**
     * Create an instance of {@link ChildParent }
     * 
     */
    public ChildParent createChildParent() {
        return new ChildParent();
    }

    /**
     * Create an instance of {@link TextCorpus }
     * 
     */
    public TextCorpus createTextCorpus() {
        return new TextCorpus();
    }

    /**
     * Create an instance of {@link TextDocument }
     * 
     */
    public TextDocument createTextDocument() {
        return new TextDocument();
    }

    /**
     * Create an instance of {@link DocumentTermMatrix }
     * 
     */
    public DocumentTermMatrix createDocumentTermMatrix() {
        return new DocumentTermMatrix();
    }

    /**
     * Create an instance of {@link TextModelNormalization }
     * 
     */
    public TextModelNormalization createTextModelNormalization() {
        return new TextModelNormalization();
    }

    /**
     * Create an instance of {@link TimeSeriesModel }
     * 
     */
    public TimeSeriesModel createTimeSeriesModel() {
        return new TimeSeriesModel();
    }

    /**
     * Create an instance of {@link ExponentialSmoothing }
     * 
     */
    public ExponentialSmoothing createExponentialSmoothing() {
        return new ExponentialSmoothing();
    }

    /**
     * Create an instance of {@link Level }
     * 
     */
    public Level createLevel() {
        return new Level();
    }

    /**
     * Create an instance of {@link TrendExpoSmooth }
     * 
     */
    public TrendExpoSmooth createTrendExpoSmooth() {
        return new TrendExpoSmooth();
    }

    /**
     * Create an instance of {@link TreeModel }
     * 
     */
    public TreeModel createTreeModel() {
        return new TreeModel();
    }

    /**
     * Create an instance of {@link DataDictionary }
     * 
     */
    public DataDictionary createDataDictionary() {
        return new DataDictionary();
    }

    /**
     * Create an instance of {@link DataField }
     * 
     */
    public DataField createDataField() {
        return new DataField();
    }

    /**
     * Create an instance of {@link FieldValue }
     * 
     */
    public FieldValue createFieldValue() {
        return new FieldValue();
    }

    /**
     * Create an instance of {@link FieldValueCount }
     * 
     */
    public FieldValueCount createFieldValueCount() {
        return new FieldValueCount();
    }

    /**
     * Create an instance of {@link TransformationDictionary }
     * 
     */
    public TransformationDictionary createTransformationDictionary() {
        return new TransformationDictionary();
    }

    /**
     * Create an instance of {@link DefineFunction }
     * 
     */
    public DefineFunction createDefineFunction() {
        return new DefineFunction();
    }

    /**
     * Create an instance of {@link ParameterField }
     * 
     */
    public ParameterField createParameterField() {
        return new ParameterField();
    }

    /**
     * Create an instance of {@link INTSparseArray }
     * 
     */
    public INTSparseArray createINTSparseArray() {
        return new INTSparseArray();
    }

    /**
     * Create an instance of {@link PMML }
     * 
     */
    public PMML createPMML() {
        return new PMML();
    }

    /**
     * Create an instance of {@link MiningBuildTask }
     * 
     */
    public MiningBuildTask createMiningBuildTask() {
        return new MiningBuildTask();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.dmg.org/PMML-4_1", name = "SpectralAnalysis")
    public JAXBElement<Object> createSpectralAnalysis(Object value) {
        return new JAXBElement<Object>(_SpectralAnalysis_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link List }{@code <}{@link Integer }{@code >}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.dmg.org/PMML-4_1", name = "INT-Entries")
    public JAXBElement<List<Integer>> createINTEntries(List<Integer> value) {
        return new JAXBElement<List<Integer>>(_INTEntries_QNAME, ((Class) List.class), null, ((List<Integer> ) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link List }{@code <}{@link Double }{@code >}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.dmg.org/PMML-4_1", name = "REAL-Entries")
    public JAXBElement<List<Double>> createREALEntries(List<Double> value) {
        return new JAXBElement<List<Double>>(_REALEntries_QNAME, ((Class) List.class), null, ((List<Double> ) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link COUNTTABLETYPE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.dmg.org/PMML-4_1", name = "CountTable")
    public JAXBElement<COUNTTABLETYPE> createCountTable(COUNTTABLETYPE value) {
        return new JAXBElement<COUNTTABLETYPE>(_CountTable_QNAME, COUNTTABLETYPE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.dmg.org/PMML-4_1", name = "ARIMA")
    public JAXBElement<Object> createARIMA(Object value) {
        return new JAXBElement<Object>(_ARIMA_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link List }{@code <}{@link Integer }{@code >}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.dmg.org/PMML-4_1", name = "Indices")
    public JAXBElement<List<Integer>> createIndices(List<Integer> value) {
        return new JAXBElement<List<Integer>>(_Indices_QNAME, ((Class) List.class), null, ((List<Integer> ) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.dmg.org/PMML-4_1", name = "SeasonalTrendDecomposition")
    public JAXBElement<Object> createSeasonalTrendDecomposition(Object value) {
        return new JAXBElement<Object>(_SeasonalTrendDecomposition_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link COUNTTABLETYPE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.dmg.org/PMML-4_1", name = "NormalizedCountTable")
    public JAXBElement<COUNTTABLETYPE> createNormalizedCountTable(COUNTTABLETYPE value) {
        return new JAXBElement<COUNTTABLETYPE>(_NormalizedCountTable_QNAME, COUNTTABLETYPE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.dmg.org/PMML-4_1", name = "Array")
    public JAXBElement<ArrayType> createArray(ArrayType value) {
        return new JAXBElement<ArrayType>(_Array_QNAME, ArrayType.class, null, value);
    }

}
