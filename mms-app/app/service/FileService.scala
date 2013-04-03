package service

import analysis.FileUtils._
import collection.JavaConversions._
import collection.mutable
import java.io._
import javax.jcr.Binary
import io.Source
import com.google.inject.Inject
import analysis.{EmbedHtmlAnalysisResultWriter, DSVReader}
import org.eobjects.metamodel.util.SimpleTableDef
import org.eobjects.metamodel.schema.{MutableTable, MutableColumn, ColumnType}
import org.eobjects.metamodel.pojo.ArrayTableDataProvider
import org.eobjects.analyzer.configuration.AnalyzerBeansConfigurationImpl
import org.eobjects.analyzer.job.builder.AnalysisJobBuilder
import org.eobjects.analyzer.connection.PojoDatastore
import org.eobjects.analyzer.job.runner.AnalysisRunnerImpl
import org.eobjects.analyzer.beans.{BooleanAnalyzer, DateAndTimeAnalyzer, NumberAnalyzer, StringAnalyzer}
import org.eobjects.analyzer.data.MetaModelInputColumn
import org.eobjects.analyzer.result.renderer.{AnnotatedRowsHtmlRenderer, CrosstabHtmlRenderer, CrosstabTextRenderer}
import org.eobjects.analyzer.result.{SimpleAnalysisResult, CrosstabResult}
import org.eobjects.analyzer.beans.stringpattern.{PatternFinderResultHtmlRenderer, PatternFinderResultTextRenderer, PatternFinderResult, PatternFinderAnalyzer}
import data.transformers.{EntityRecognitionTransformer, DateTransformer, IntegerTransformer}
import utils.DateUtils._
import org.eobjects.analyzer.beans.convert.{ConvertToBooleanTransformer, ConvertToNumberTransformer}
import org.eobjects.analyzer.result.html.HtmlAnalysisResultWriter
import org.eobjects.analyzer.descriptors.{Descriptors, SimpleDescriptorProvider}
import java.util.Date
import org.joda.time.DateTime
import scala.Some
import edu.stanford.nlp.ling.CoreLabel
import edu.stanford.nlp.ie.AbstractSequenceClassifier

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

  def extractMetadata(filename: String): String = {
    var binary: Binary = null
    try {
      binary = repo.getBinary(filename)
      extractMetadata(filename, binary.getStream)
    } finally {
      if (binary != null) binary.dispose()
    }
  }

  def extractMetadata(filename: String, file: File): String = {
    try {
      extractMetadata(filename, new FileInputStream(file))
    } catch {
      case e: FileNotFoundException => {
        e.printStackTrace()
        ""
      }
    }
  }

  def extractMetadata(filename: String, in: InputStream): String = {
    var src: Source = null
    try {
      src = Source.fromInputStream(in)
      val lines = src.getLines().take(10)
      val data = lines.mkString("\n")
      val dialect = sniff(data)
      println(dialect)
      var reader: Reader = new StringReader(data)
      var csv = DSVReader.open(reader, dialect.delimiter.get,
        dialect.quoteChar.getOrElse('\0'), dialect.escapeChar, 0, false,
        dialect.skipInitialSpace.get, dialect.quoteChar == None)
      val stream = csv.toStream()
      val sample = stream.take(20)
      val firstRow = sample(0)
      val numberColumns = firstRow.length
      println(s"Number of columns: $numberColumns")
      val header = hasHeader(sample.toList)
      println(s"has-header: $header")
      dialect.hasHeader = Some(header)

      // reset the stream
      csv.close()
      //val skipRow = if (header) 1 else 0
      reader = new StringReader(data)
      csv = DSVReader.open(reader, dialect.delimiter.get,
        dialect.quoteChar.getOrElse('\0'), dialect.escapeChar, 0, false,
        dialect.skipInitialSpace.get, dialect.quoteChar == None)
      val headers = csv.readNext() getOrElse List()
      val rows = csv.all() //if (header) csv.allWithHeaders() else csv.all()
      val columns = rows.transpose

      val columnDataTypes = mutable.HashMap[String, mutable.Set[String]]().withDefaultValue(mutable.Set[String]())
      for (col <- columns.zipWithIndex;
           cell <- col._1) {
        val dataType = deduceDataType(cell) getOrElse "Unknown"
        val colName = headers(col._2)
        columnDataTypes(colName) = columnDataTypes(colName) + dataType
      }

      val columnTypes: List[(String, ColumnType)] = columnDataTypes.toList.map(entry => {
        val (colName, dataTypes) = entry
        if (dataTypes.size == 1) {
          dataTypes.head match {
            case "Integer" => (colName, ColumnType.INTEGER)
            case "Decimal" => (colName, ColumnType.DECIMAL)
            case "Date" => (colName, ColumnType.DATE)
            case "Boolean" | "Integer-Boolean" => (colName, ColumnType.BOOLEAN)
            case "Text" => (colName, ColumnType.LONGNVARCHAR)
            case _ => (colName, ColumnType.NVARCHAR)
          }
        } else if (dataTypes.size == 2 && dataTypes.count(_.startsWith("Integer")) == 2) {
          (colName, ColumnType.INTEGER)
        } else {
          (colName, ColumnType.NVARCHAR)
        }
      })
      val (colNames, colTypes) =
        columnTypes
        .sortWith((e1, e2) => headers.indexOf(e1._1) < headers.indexOf(e2._1))
        .unzip

      var arrays: mutable.ArrayBuffer[Array[AnyRef]] = mutable.ArrayBuffer()
      rows.foreach(arrays += _.toArray)

      val tableDef = new SimpleTableDef(filename, colNames.toArray, colTypes.toArray)
      val dataProvider = new ArrayTableDataProvider(tableDef, arrays)
      val descriptorProvider = new SimpleDescriptorProvider()
      descriptorProvider.addRendererBeanDescriptor(Descriptors.ofRenderer(classOf[CrosstabHtmlRenderer]))
      descriptorProvider.addRendererBeanDescriptor(Descriptors.ofRenderer(classOf[PatternFinderResultHtmlRenderer]))
      descriptorProvider.addRendererBeanDescriptor(Descriptors.ofRenderer(classOf[AnnotatedRowsHtmlRenderer]))
      val conf = (new AnalyzerBeansConfigurationImpl()).replace(descriptorProvider)
      val builder = new AnalysisJobBuilder(conf)
      val datastore = new PojoDatastore("file", filename, mutable.ArrayBuffer(dataProvider))
      builder.setDatastore(datastore)
      colNames.foreach(builder.addSourceColumns(_))
      val stringAnalyzerBuilder = builder.addAnalyzer(classOf[StringAnalyzer])
      val patternAnalyzerBuilder = builder.addAnalyzer(classOf[PatternFinderAnalyzer])
      val table = new MutableTable(filename)
      colNames.zipWithIndex.foreach(c => {
        val (colName: String, i: Int) = c
        if (colTypes(i) == ColumnType.NVARCHAR)
          stringAnalyzerBuilder.addInputColumn(new MetaModelInputColumn(new MutableColumn(colName, ColumnType.NVARCHAR, table, i, true)))
        patternAnalyzerBuilder.addInputColumn(new MetaModelInputColumn(new MutableColumn(colName, ColumnType.NVARCHAR, table, i, true)))
      })
      val job = builder.toAnalysisJob
      val runner = new AnalysisRunnerImpl(conf)
      val future = runner.run(job)
      val stringWriter: Writer = new StringWriter()
      val htmlResultWriter = new EmbedHtmlAnalysisResultWriter()
      future.await()
      if (future.isSuccessful) {
  //        val writer = new TextAnalysisResultWriter()
  //        val stringWriter = new StringWriter()
  //        writer.write(future, conf, new ImmutableRef(stringWriter), new ImmutableRef(System.out))
        /*
        future.getResults.foreach { result =>
          result match {
            case r: CrosstabResult => {
              println()
              println("Text Analysis:")
              val rendered = new CrosstabTextRenderer().render(r)
              val lines = rendered.split("\n")
              lines.foreach(println(_))
            }
            case r: PatternFinderResult => {
              val rendered = new PatternFinderResultTextRenderer().render(r)
              val lines = rendered.split("\n")
              println()
              println(s"Column: ${r.getColumn.getName}")
              lines.foreach(println(_))
            }
            case r => println(r)
          }
        }*/
      } else {
        future.getErrors.foreach(e => {
          println(e.getMessage)
          println(e.printStackTrace())
        })
      }

      val builder2 = new AnalysisJobBuilder(conf)
      builder2.setDatastore(datastore)
      colNames.foreach(builder2.addSourceColumns(_))
      val integerTransformerBuilder = if (colTypes.contains(ColumnType.INTEGER)) builder2.addTransformer(classOf[IntegerTransformer]) else null
      val decimalTransformerBuilder = if (colTypes.contains(ColumnType.DECIMAL)) builder2.addTransformer(classOf[ConvertToNumberTransformer]) else null
      val dateTransformerBuilder = if (colTypes.contains(ColumnType.DATE)) builder2.addTransformer(classOf[DateTransformer]) else null
      val booleanTransformerBuilder = if (colTypes.contains(ColumnType.BOOLEAN)) builder2.addTransformer(classOf[ConvertToBooleanTransformer]) else null
      val entityTransformerBuilder = if (colTypes.contains(ColumnType.LONGNVARCHAR)) builder2.addTransformer(classOf[EntityRecognitionTransformer]) else null
      val numberAnalyzerBuilder = if (colTypes.contains(ColumnType.INTEGER) || colTypes.contains(ColumnType.DECIMAL)) builder2.addAnalyzer(classOf[NumberAnalyzer]) else null
      val dateAnalyzerBuilder = if (colTypes.contains(ColumnType.DATE)) builder2.addAnalyzer(classOf[DateAndTimeAnalyzer]) else null
      val booleanAnalyzerBuilder = if (colTypes.contains(ColumnType.BOOLEAN)) builder2.addAnalyzer(classOf[BooleanAnalyzer]) else null
      if (colTypes.contains(ColumnType.INTEGER) || colTypes.contains(ColumnType.DECIMAL)) numberAnalyzerBuilder.setConfiguredProperty("Descriptive statistics", true)
      if (colTypes.contains(ColumnType.DATE)) dateAnalyzerBuilder.setConfiguredProperty("Descriptive statistics", true)
      if (colTypes.contains(ColumnType.LONGNVARCHAR)) entityTransformerBuilder.setConfiguredProperty("Classifier", classifier)
      colNames.zipWithIndex.foreach(c => {
        val (colName: String, i: Int) = c
        if (colTypes(i) == ColumnType.INTEGER) {
          integerTransformerBuilder.addInputColumn(new MetaModelInputColumn(new MutableColumn(colName, ColumnType.NVARCHAR, table, i, true)))
          numberAnalyzerBuilder.addInputColumn(integerTransformerBuilder.getOutputColumnByName(colName + " (as int)"))
        } else if (colTypes(i) == ColumnType.DECIMAL) {
          decimalTransformerBuilder.addInputColumn(new MetaModelInputColumn(new MutableColumn(colName, ColumnType.NVARCHAR, table, i, true)))
          numberAnalyzerBuilder.addInputColumn(decimalTransformerBuilder.getOutputColumnByName(colName + " (as number)"))
        } else if (colTypes(i) == ColumnType.DATE) {
          dateTransformerBuilder.addInputColumn(new MetaModelInputColumn(new MutableColumn(colName, ColumnType.NVARCHAR, table, i, true)))
          dateAnalyzerBuilder.addInputColumn(dateTransformerBuilder.getOutputColumnByName(colName + " (as date)"))
        } else if (colTypes(i) == ColumnType.BOOLEAN) {
          booleanTransformerBuilder.addInputColumn(new MetaModelInputColumn(new MutableColumn(colName, ColumnType.NVARCHAR, table, i, true)))
          booleanAnalyzerBuilder.addInputColumn(booleanTransformerBuilder.getOutputColumnByName(colName + " (as boolean)"))
        } else if (colTypes(i) == ColumnType.LONGNVARCHAR) {
          entityTransformerBuilder.addInputColumn(new MetaModelInputColumn(new MutableColumn(colName, ColumnType.NVARCHAR, table, i, true)))
        }
      })
      val job2 = builder2.toAnalysisJob
      val runner2 = new AnalysisRunnerImpl(conf)
      val future2 = runner2.run(job2)
      future2.await()
      val resultsMap = future.getResultMap
      val combinedResultsMap = resultsMap ++ future2.getResultMap
      val results = new SimpleAnalysisResult(combinedResultsMap, new Date())
      htmlResultWriter.write(results, conf, stringWriter)
      //println(stringWriter)
      //println()
      //println("Number Analysis:")
      if (future2.isSuccessful) {
        /*
        future2.getResults.foreach { result =>
          result match {
            case r: CrosstabResult => {
              val rendered = new CrosstabTextRenderer().render(r)
              val lines = rendered.split("\n")
              lines.foreach(println(_))
            }
            case r => println(r)
          }
        }*/
      } else {
        future.getErrors.foreach(e => {
          println(e.getMessage)
          println(e.printStackTrace())
        })
      }

      stringWriter.toString

    } finally {
      if (src != null) src.close()
    }
  }

  def deduceDataType(value: String): Option[String] = {
    if (value == null || value.length == 0) {
      None
    } else {
      val v = value.trim
      if (v.length > 0) {
        try {
          val int = Integer.parseInt(v)
          if (int == 0 || int == 1)
            return Some("Integer-Boolean")
          else
            return Some("Integer")
        } catch {
          case e: NumberFormatException => //skip
        }
        try {
          java.lang.Double.parseDouble(v)
          return Some("Decimal")
        } catch {
          case e: NumberFormatException => //skip
        }
        val date = parseDate(v)
        if (date != None)
          return Some("Date")
        val bool = parseBoolean(v)
        if (bool != None)
          return Some("Boolean")
        if (v.length >= 128)
          Some("Text")
        else
          Some("String")
      } else {
        Some("String")
      }
    }
  }

  def parseBoolean(value: String = "") = {
    val v = value.trim.toLowerCase
    if (List("true", "yes", "1").contains(v))
      Some(true)
    else if (List("false", "no", "0").contains(v))
      Some(false)
    else
      None
   }
}
