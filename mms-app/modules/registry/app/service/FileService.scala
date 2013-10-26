package service

import analysis.Analyzer._
import analysis.Dialect
import analysis.EmbedHtmlAnalysisResultWriter
import collection.JavaConversions._
import com.google.inject.Inject
import data.transformers.{EntityRecognitionTransformer, DateTransformer, IntegerTransformer}
import edu.stanford.nlp.ie.AbstractSequenceClassifier
import edu.stanford.nlp.ling.CoreLabel
import io.Source
import java.io._
import java.util.Date
import javax.jcr.Binary
import models.domain._
import models.domain.file.{FlatFile, FileColumn}
import org.eobjects.analyzer.beans._
import org.eobjects.analyzer.beans.convert.{ConvertToBooleanTransformer, ConvertToNumberTransformer}
import org.eobjects.analyzer.beans.stringpattern.{PatternFinderResult, PatternFinderResultHtmlRenderer, PatternFinderAnalyzer}
import org.eobjects.analyzer.configuration.AnalyzerBeansConfigurationImpl
import org.eobjects.analyzer.data.MetaModelInputColumn
import org.eobjects.analyzer.descriptors.{Descriptors, SimpleDescriptorProvider}
import org.eobjects.analyzer.job.builder.{AnalysisJobBuilder, TransformerJobBuilder}
import org.eobjects.analyzer.job.runner.AnalysisRunnerImpl
import org.eobjects.analyzer.result.renderer.{AnnotatedRowsHtmlRenderer, CrosstabHtmlRenderer}
import org.eobjects.analyzer.result.{CrosstabDimension, Crosstab, SimpleAnalysisResult}
import org.eobjects.metamodel.schema.{MutableTable, MutableColumn, ColumnType}
import play.db.jpa.JPA
import scala.Predef._
import scala.Some

class FileService {

  @Inject var repo: FileRepoService = null

  @Inject var classifier: AbstractSequenceClassifier[CoreLabel] = null

  def parseFile(filename: String) {
    var binary: Binary = null
    try {
      binary = repo.getBinary(filename)
      val reader: Reader = new InputStreamReader(binary.getStream)
//      val csv = DSVReader.open(reader)
//      csv.iterator.toList.take(5).foreach(fields => println(fields))
    } finally {
      if (binary != null) binary.dispose()
    }
  }

  def extractMetadata(filename: String, dataset: FlatFile): String = {
    var binary: Binary = null
    try {
      binary = repo.getBinary(filename)
      val dialect = analyzeStructure(filename, binary.getStream)
      analyzeContents(filename, dialect, binary.getStream, dataset)
    } finally {
      if (binary != null) binary.dispose()
    }
  }

  def extractMetadata(filename: String, file: File, dataset: FlatFile): String = {
    var in: InputStream = null
    try {
      in = new FileInputStream(file)
      val dialect = analyzeStructure(filename, in)
      in.close()
      in = new FileInputStream(file)
      analyzeContents(filename, dialect, in, dataset)
    } catch {
      case e: FileNotFoundException => {
        e.printStackTrace()
        ""
      }
    } finally {
      if (in != null) in.close()
    }
  }

  def analyzeStructure(filename: String, in: InputStream): Dialect = {
    var src: Source = null
    try {
      src = Source.fromInputStream(in)
      val sample = src.getLines().take(10).toList
      val (dialect, numColumns) = deduceFileStructure(sample)
      println(s"Number of columns in file $filename: $numColumns")
      dialect
    } finally {
      if (src != null) src.close()
    }
  }

  def analyzeContents(filename: String, dialect: Dialect, in: InputStream, dataset: FlatFile): String = {
    var reader: Reader = null
    try {
      reader = new InputStreamReader(in)
      val dsv = dsvReader(dialect, reader)
      val headers = dsv.readNext() getOrElse List()
      val rows = dsv.all() //if (header) csv.allWithHeaders() else csv.all()
      val columns = rows.transpose

      val columnDataTypes = deduceColumns(columns, headers)
      val (columnNames, columnTypes) = deduceSqlTypesByColumn(columnDataTypes, headers)
//      val conf = new AnalyzerBeansConfigurationImpl()
//      val analysisJobBuilder = createAnalysisJobBuilder(conf,
//        classOf[CrosstabHtmlRenderer],
//        classOf[PatternFinderResultHtmlRenderer],
//        classOf[AnnotatedRowsHtmlRenderer])

      val descriptorProvider = new SimpleDescriptorProvider()
      descriptorProvider.addRendererBeanDescriptor(Descriptors.ofRenderer(classOf[CrosstabHtmlRenderer]))
      descriptorProvider.addRendererBeanDescriptor(Descriptors.ofRenderer(classOf[PatternFinderResultHtmlRenderer]))
      descriptorProvider.addRendererBeanDescriptor(Descriptors.ofRenderer(classOf[AnnotatedRowsHtmlRenderer]))
      val conf = (new AnalyzerBeansConfigurationImpl()).replace(descriptorProvider)
      val analysisJobBuilder = new AnalysisJobBuilder(conf)

      val datastore = createPojoDatastore(filename, rows, columnNames, columnTypes)
      analysisJobBuilder.setDatastore(datastore)
      columnNames.foreach(analysisJobBuilder.addSourceColumns(_))

      val distinctColumnTypes = columnTypes.toSet
      val analyzerJobBuilderMap =
        createAnalyzerJobBuilderMap(analysisJobBuilder, distinctColumnTypes,
          (ColumnType.NVARCHAR, classOf[StringAnalyzer]),
          (ColumnType.INTEGER, classOf[NumberAnalyzer]),
          (ColumnType.DECIMAL, classOf[NumberAnalyzer]),
          (ColumnType.DATE, classOf[DateAndTimeAnalyzer]),
          (ColumnType.BOOLEAN, classOf[BooleanAnalyzer]))

      val transformerJobBuilderMap =
        createTransformerJobBuilderMap(analysisJobBuilder, distinctColumnTypes,
          (ColumnType.INTEGER, classOf[IntegerTransformer]),
          (ColumnType.DECIMAL, classOf[ConvertToNumberTransformer]),
          (ColumnType.DATE, classOf[DateTransformer]),
          (ColumnType.BOOLEAN, classOf[ConvertToBooleanTransformer]))

      val addTransformerJobBuilder: Option[(ColumnType, TransformerJobBuilder[_])] =
        if (columnTypes.contains(ColumnType.LONGNVARCHAR)) {
          val entityTransformerJobBuilder =
            analysisJobBuilder.addTransformer(classOf[EntityRecognitionTransformer])
          entityTransformerJobBuilder.setConfiguredProperty("Classifier", classifier)
          Some((ColumnType.LONGNVARCHAR, entityTransformerJobBuilder))
        } else {
          None
        }

      val originalColumnTypes = List.fill(columnNames.length) {ColumnType.NVARCHAR}
      addAnalyzers(analysisJobBuilder, filename, columnNames, originalColumnTypes, analyzerJobBuilderMap)

      val patternAnalyzerBuilder = analysisJobBuilder.addAnalyzer(classOf[PatternFinderAnalyzer])
      val table = new MutableTable(filename)
      columnNames.zipWithIndex.foreach {
        case (columnName, columnIndex) => {
          patternAnalyzerBuilder.addInputColumn(
            new MetaModelInputColumn(
              new MutableColumn(columnName, ColumnType.NVARCHAR, table, columnIndex, true)))
        }
      }
      addTransformers(analysisJobBuilder, filename, columnNames, columnTypes, analyzerJobBuilderMap,
        if (addTransformerJobBuilder.isEmpty)
          transformerJobBuilderMap
        else
          transformerJobBuilderMap + addTransformerJobBuilder.get)

      val job = analysisJobBuilder.toAnalysisJob
      val runner = new AnalysisRunnerImpl(conf)
      val future = runner.run(job)
      val stringWriter: Writer = new StringWriter()
      val htmlResultWriter = new EmbedHtmlAnalysisResultWriter()
      future.await()
      val results = new SimpleAnalysisResult(future.getResultMap, new Date())

      val columnsByType: List[(String, FileColumn)] = columnNames.zipWithIndex.map {
        case (columnName, columnIndex) => {
          val column = new FileColumn
          column.setDataset(dataset)
          column.setHasHeaderRow(dialect.hasHeader.getOrElse(false))
          column.setName(columnName)
          column.setColumnIndex(columnIndex + 1)
          val columnType = columnTypes(columnIndex)

          import ColumnType._

          val dataTypeName = columnType match {
            case INTEGER | DECIMAL => "Numeric"
            case NVARCHAR | LONGNVARCHAR => "String"
            case BOOLEAN => "Boolean"
            case DATE => "Date"
            case _ => "String"
          }
          val dataType = DataType.findByName(dataTypeName)
          column.setDataType(dataType)

          JPA.em().persist(column)

          (dataTypeName, column)
        }
      }

      future.getResultMap.values() foreach {
        case result: StringAnalyzerResult =>

//          val inputColumnMap = result.getColumns map(inputColumn => {
//            val name = inputColumn.getName
//            if (inputColumn.isPhysicalColumn) {
//              (name, inputColumn)
//            } else {
//              val nameP = """^(.+)(\s+\(as .+\))$""".r
//              val nameP(origName, _) = name
//              (origName, inputColumn)
//            }
//          }) groupBy(_._1)

          columnsByType flatMap {
            case (_, column) => {

              import StringAnalyzer._

              List(MEASURE_AVG_CHARS, MEASURE_AVG_WHITE_SPACES, MEASURE_BLANK_COUNT, MEASURE_DIACRITIC_CHARS,
                MEASURE_DIGIT_CHARS, MEASURE_ENTIRELY_LOWERCASE_COUNT, MEASURE_ENTIRELY_UPPERCASE_COUNT, MEASURE_LOWERCASE_CHARS,
                MEASURE_MAX_CHARS, MEASURE_MAX_WHITE_SPACES, MEASURE_MAX_WORDS, MEASURE_MIN_CHARS, MEASURE_MIN_WHITE_SPACES,
                MEASURE_MIN_WORDS, MEASURE_NON_LETTER_CHARS, MEASURE_NULL_COUNT, MEASURE_ROW_COUNT, MEASURE_TOTAL_CHAR_COUNT,
                MEASURE_UPPERCASE_CHARS, MEASURE_UPPERCASE_CHARS_EXCL_FIRST_LETTERS, MEASURE_WORD_COUNT
              ) map(metricName => {
                val metric = Metric.findByName(metricName)
                val analysisType = AnalysisType.findByName("String analyzer")
                val pk = new MetricValuePK
                pk.setAnalysisType(analysisType)
                pk.setMetric(metric)
                pk.setColumn(column)
                val value = new MetricValue
                value.setPk(pk)
                val metricValue = result.getCrosstab.where(DIMENSION_COLUMN, column.getName)
                  .where(DIMENSION_MEASURES, metricName).get()
                if (metricValue != null) {
                  metricValue match {
                    case v: Number => value.setValue(new java.math.BigDecimal(v.toString))
                    case v => value.setStringValue(v.toString)
                  }
                } else {
                  println("Couldn't find a metric value for Metric=" + metricName +
                    " and Column=" + column.getName + " (as date)")
                }

                JPA.em().persist(value)

                metric
              })
            }
          }
        case result: NumberAnalyzerResult => columnsByType filter {
            case ("Numeric", _) => true
            case _ => false
          } flatMap {
            case (_, column) => {

              val columnTypeByNameMap = (columnNames zip columnTypes).toMap

              import NumberAnalyzer._

              List(MEASURE_GEOMETRIC_MEAN, MEASURE_HIGHEST_VALUE, MEASURE_KURTOSIS, MEASURE_LOWEST_VALUE,
                MEASURE_MEAN, MEASURE_MEDIAN, MEASURE_NULL_COUNT, MEASURE_PERCENTILE25, MEASURE_PERCENTILE75,
                MEASURE_ROW_COUNT, MEASURE_SECOND_MOMENT, MEASURE_SKEWNESS, MEASURE_STANDARD_DEVIATION,
                MEASURE_SUM, MEASURE_SUM_OF_SQUARES, MEASURE_VARIANCE
              ) map(metricName => {
                val metric = Metric.findByName(metricName)
                val analysisType = AnalysisType.findByName("Number analyzer")
                val pk = new MetricValuePK
                pk.setAnalysisType(analysisType)
                pk.setMetric(metric)
                pk.setColumn(column)
                val value = new MetricValue
                value.setPk(pk)
                val name = columnTypeByNameMap(column.getName) match {
                  case ColumnType.INTEGER => column.getName + " (as int)"
                  case ColumnType.DECIMAL => column.getName + " (as number)"
                  case _ => column.getName
                }
                val metricValue = result.getCrosstab.where(DIMENSION_COLUMN, name)
                  .where(DIMENSION_MEASURE, metricName).get()
                if (metricValue != null) {
                  metricValue match {
                    case v: Number => value.setValue(new java.math.BigDecimal(v.toString))
                    case v => value.setStringValue(v.toString)
                  }
                } else {
                  println("Couldn't find a metric value for Metric=" + metricName +
                    " and Column=" + column.getName + " (as date)")
                }
                JPA.em().persist(value)

                metric
              })
            }
          }
        case result: DateAndTimeAnalyzerResult => columnsByType filter {
            case ("Date", _) => true
            case _ => false
          } flatMap {
            case (_, column) => {

              import DateAndTimeAnalyzer._

              List(MEASURE_HIGHEST_DATE, MEASURE_HIGHEST_TIME, MEASURE_KURTOSIS, MEASURE_LOWEST_DATE,
                MEASURE_LOWEST_TIME, MEASURE_MEAN, MEASURE_MEDIAN, MEASURE_NULL_COUNT,
                MEASURE_PERCENTILE25, MEASURE_PERCENTILE75, MEASURE_ROW_COUNT, MEASURE_SKEWNESS
              ) map(metricName => {
                val metric = Metric.findByName(metricName)
                val analysisType = AnalysisType.findByName("Date/time analyzer")
                val pk = new MetricValuePK
                pk.setAnalysisType(analysisType)
                pk.setMetric(metric)
                pk.setColumn(column)
                val value = new MetricValue
                value.setPk(pk)
                val metricValue = result.getCrosstab.where(DIMENSION_COLUMN, column.getName + " (as date)")
                  .where(DIMENSION_MEASURE, metricName).get()
                if (metricValue != null) {
                  metricValue match {
                    case v: Number => value.setValue(new java.math.BigDecimal(v.toString))
                    case v => value.setStringValue(v.toString)
                  }
                } else {
                  println("Couldn't find a metric value for Metric=" + metricName +
                    " and Column=" + column.getName + " (as date)")
                }
                JPA.em().persist(value)

                metric
              })
            }
          }
        case result: BooleanAnalyzerResult => columnsByType filter {
            case ("Boolean", _) => true
            case _ => false
          } flatMap {
            case (_, column) => {

              import BooleanAnalyzer._

              List(MEASURE_FALSE_COUNT, MEASURE_LEAST_FREQUENT, MEASURE_MOST_FREQUENT, MEASURE_NULL_COUNT,
                MEASURE_ROW_COUNT, MEASURE_TRUE_COUNT
              ) map(metricName => {
                val metric = Metric.findByName(metricName)
                val analysisType = AnalysisType.findByName("Boolean analyzer")
                val pk = new MetricValuePK
                pk.setAnalysisType(analysisType)
                pk.setMetric(metric)
                pk.setColumn(column)
                val value = new MetricValue
                value.setPk(pk)
                val metricValue = result.getColumnStatisticsCrosstab.where(DIMENSION_COLUMN, column.getName + " (as boolean)")
                  .where(DIMENSION_MEASURE, metricName).get()
                if (metricValue != null) {
                  metricValue match {
                    case v: Number => value.setValue(new java.math.BigDecimal(v.toString))
                    case v => value.setStringValue(v.toString)
                  }
                } else {
                  println("Couldn't find a metric value for Metric=" + metricName +
                    " and Column=" + column.getName + " (as date)")
                }
                JPA.em().persist(value)

                metric
              })
            }
          }
        case result: PatternFinderResult => columnsByType filter {
          case (_, column) => column.getName == result.getColumn.getName
        } flatMap {
            case (_, column) => {

              import PatternFinderAnalyzer._

              val analysisType = AnalysisType.findByName("Pattern finder")
              val crosstab: Crosstab[_] = result.getSingleCrosstab
              val patternDimension: CrosstabDimension = crosstab.getDimension(DIMENSION_NAME_PATTERN)
              val categories = patternDimension.getCategories
              categories.map(pattern => {
                val patternResult = new PatternResult
                patternResult.setColumn(column)
                patternResult.setAnalysisType(analysisType)
                patternResult.setPattern(pattern)
                val matchCount = crosstab
                    .where(patternDimension, pattern)
                    .where(DIMENSION_NAME_MEASURES, MEASURE_MATCH_COUNT)
                    .get()
                patternResult.setMatchCount(matchCount match {
                  case k: Number => k.intValue()
                  case _ => 0
                })
                val sample = crosstab
                  .where(patternDimension, pattern)
                  .where(DIMENSION_NAME_MEASURES, MEASURE_SAMPLE)
                  .get().asInstanceOf[String]
                patternResult.setSample(sample)

                JPA.em().persist(patternResult)

                patternResult
              })
            }
          }
      }

      htmlResultWriter.write(results, conf, stringWriter)
      if (future.isSuccessful) {
      } else {
        future.getErrors.foreach(e => {
          println(e.getMessage)
          println(e.printStackTrace())
        })
      }

      stringWriter.toString

    } finally {
      if (reader != null) reader.close()
    }
  }
}
