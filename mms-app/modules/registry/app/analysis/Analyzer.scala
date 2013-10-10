package analysis

import analysis.FileUtils._
import collection.JavaConversions._
import java.io.{StringReader, Reader}
import org.eobjects._
import org.eobjects.analyzer.beans.api.{Transformer, Renderer}
import org.eobjects.analyzer.configuration.AnalyzerBeansConfigurationImpl
import org.eobjects.analyzer.connection.{PojoDatastore, Datastore}
import org.eobjects.analyzer.data.MetaModelInputColumn
import org.eobjects.analyzer.descriptors.{Descriptors, SimpleDescriptorProvider}
import org.eobjects.analyzer.job.builder.{TransformerJobBuilder, AnalyzerJobBuilder, AnalysisJobBuilder}
import org.eobjects.metamodel.pojo.ArrayTableDataProvider
import org.eobjects.metamodel.schema.{MutableColumn, MutableTable, ColumnType}
import org.eobjects.metamodel.util.SimpleTableDef
import scala.Some
import scala.collection._
import utils.DateUtils._

/**
 * User: markmo
 * Date: 4/04/13
 * Time: 11:12 AM
 */
object Analyzer {

  val typeSuffixes = Map(
    ColumnType.INTEGER -> " (as int)",
    ColumnType.DECIMAL -> " (as number)",
    ColumnType.DATE -> " (as date)",
    ColumnType.BOOLEAN -> " (as boolean)")

  def createDataProvider(filename: String,
                         rows: List[List[String]],
                         columnNames: List[String],
                         columnTypes: List[ColumnType]): ArrayTableDataProvider = {
    var arrays: mutable.ArrayBuffer[Array[AnyRef]] = mutable.ArrayBuffer()
    rows.foreach(arrays += _.toArray)
    val tableDef = new SimpleTableDef(filename, columnNames.toArray, columnTypes.toArray)
    new ArrayTableDataProvider(tableDef, arrays)
  }

  def createPojoDatastore(filename: String,
                          rows: List[List[String]],
                          columnNames: List[String],
                          columnTypes: List[ColumnType]): Datastore = {
    val dataProvider = createDataProvider(filename, rows, columnNames, columnTypes)
    new PojoDatastore("file", filename, mutable.ArrayBuffer(dataProvider))
  }

  def createPojoDatastore(filename: String,
                          dataProvider: ArrayTableDataProvider): Datastore = {
    new PojoDatastore("file", filename, mutable.ArrayBuffer(dataProvider))
  }

  def createAnalysisJobBuilder(conf: AnalyzerBeansConfigurationImpl,
                               renderers: Class[_ <: Renderer[_, _]]*
                                ): AnalysisJobBuilder = {
    val descriptorProvider = new SimpleDescriptorProvider()
    for (renderer <- renderers) {
      descriptorProvider.addRendererBeanDescriptor(Descriptors.ofRenderer(renderer))
    }
    new AnalysisJobBuilder(conf.replace(descriptorProvider))
  }

  val statTypes = Set(ColumnType.INTEGER, ColumnType.DECIMAL, ColumnType.DATE)

  def createAnalyzerJobBuilderMap(analysisJobBuilder: AnalysisJobBuilder,
                                  columnTypes: Set[ColumnType],
                                  analyzers: (ColumnType, Class[_ <: analyzer.beans.api.Analyzer[_]])*
                                   ): Map[ColumnType, List[AnalyzerJobBuilder[_]]] = {
    val analyzerMap = analyzers groupBy(_._2) map {
      case (columnType, pairs) => (columnType, pairs.toList.map(_._1).toSet)
    }
    createAnalyzerJobBuilderMap(analysisJobBuilder, columnTypes,
      analyzerMap.toMap[Class[_ <: analyzer.beans.api.Analyzer[_]], Set[ColumnType]])
  }

  def createAnalyzerJobBuilderMap(analysisJobBuilder: AnalysisJobBuilder,
                                  columnTypes: Set[ColumnType],
                                  analyzerMap: Map[Class[_ <: analyzer.beans.api.Analyzer[_]], Set[ColumnType]]
                                   ): Map[ColumnType, List[AnalyzerJobBuilder[_]]] = {
    analyzerMap flatMap {
      case (analyzer, types) => {
        val intersect = columnTypes & types
        if (!intersect.isEmpty) {
          val analyzerJobBuilder = analysisJobBuilder.addAnalyzer(analyzer)
          if (!(types & statTypes).isEmpty) {
            analyzerJobBuilder.setConfiguredProperty("Descriptive statistics", true)
          }
          intersect.map((_, analyzerJobBuilder))
        } else {
          None
        }
      }
    } groupBy(_._1) map {
      case (columnType, pairs) => (columnType, pairs.toList.map(_._2))
    }
  }

  def addAnalyzers(analysisJobBuilder: AnalysisJobBuilder, filename: String,
                   columnNames: List[String], columnTypes: List[ColumnType],
                   analyzerJobBuilderMap: Map[ColumnType, List[AnalyzerJobBuilder[_]]]) {
    val table = new MutableTable(filename)
    for ((columnName, i) <- columnNames.zipWithIndex) {
      val columnType = columnTypes(i)
      for (analyzerJobBuilder <- analyzerJobBuilderMap(columnType)) {
        analyzerJobBuilder.addInputColumn(
          new MetaModelInputColumn(
            new MutableColumn(columnName, columnType, table, i, true)))
      }
    }
  }

  def createTransformerJobBuilderMap(analysisJobBuilder: AnalysisJobBuilder,
                                     columnTypes: Set[ColumnType],
                                     transformers: (ColumnType, Class[_ <: Transformer[_]])*
                                      ): Map[ColumnType, TransformerJobBuilder[_]] = {
    val transformerMap = transformers groupBy(_._2) map {
      case (columnType, pairs) => (columnType, pairs.toList.map(_._1).toSet)
    }
    createTransformerJobBuilderMap(analysisJobBuilder, columnTypes,
      transformerMap.toMap[Class[_ <: Transformer[_]], Set[ColumnType]])
  }

  def createTransformerJobBuilderMap(analysisJobBuilder: AnalysisJobBuilder,
                                     columnTypes: Set[ColumnType],
                                     transformerMap: Map[Class[_ <: Transformer[_]], Set[ColumnType]]
                                      ): Map[ColumnType, TransformerJobBuilder[_]] = {
    val pairs = transformerMap flatMap {
      case (transformer, types) => {
        val intersect = columnTypes & types
        if (!intersect.isEmpty) {
          val transformerJobBuilder = analysisJobBuilder.addTransformer(transformer)
          intersect.map((_, transformerJobBuilder))
        } else {
          None
        }
      }
    }
    pairs.toMap[ColumnType, TransformerJobBuilder[_]]
  }

  def addTransformers(analysisJobBuilder: AnalysisJobBuilder, filename: String,
                      columnNames: List[String], columnTypes: List[ColumnType],
                      analyzerJobBuilderMap: Map[ColumnType, List[AnalyzerJobBuilder[_]]],
                      transformerJobBuilderMap: Map[ColumnType, TransformerJobBuilder[_]]) {
    val table = new MutableTable(filename)
    for ((columnName, i) <- columnNames.zipWithIndex) {
      val columnType = columnTypes(i)
      if (transformerJobBuilderMap.contains(columnType)) {
        val transformerJobBuilder = transformerJobBuilderMap(columnType)
        transformerJobBuilder.addInputColumn(
          new MetaModelInputColumn(
            new MutableColumn(columnName, ColumnType.NVARCHAR, table, i, true)))
        for (analyzerJobBuilder <- analyzerJobBuilderMap(columnType)
             if (columnType != ColumnType.LONGNVARCHAR)) {
          val transformedColumnName = columnName + typeSuffixes(columnType)
          val inputColumn = transformerJobBuilder.getOutputColumnByName(transformedColumnName)
          analyzerJobBuilder.addInputColumn(inputColumn)
        }
      }
    }
  }

  def dsvReader(dialect: Dialect, reader: Reader): DSVReader = {
    DSVReader.open(reader, dialect.delimiter.get,
      dialect.quoteChar.getOrElse('\0'), dialect.escapeChar, 0, false,
      dialect.skipInitialSpace.get, dialect.quoteChar == None)
  }

  def deduceFileStructure(sample: List[String]): (Dialect, Int) = {
    deduceFileStructure(sample.mkString("\n"))
  }

  def deduceFileStructure(sample: String): (Dialect, Int) = {
    val dialect = sniff(sample)
    val reader: Reader = new StringReader(sample)
    val dsv = dsvReader(dialect, reader)
    val stream = dsv.toStream()
    val rows = stream.take(20)
    val numColumns = rows(0).length
    val header = hasHeader(rows.toList)
    dialect.hasHeader = Some(header)
    (dialect, numColumns)
  }

  def deduceColumns(columns: List[List[String]], headers: List[String]
                     ): Map[String, Set[String]] = {
    columns.zipWithIndex.flatMap { case (cells, columnIndex) =>
      for (cell <- cells) yield {
        val dataType = deduceDataType(cell) getOrElse "Unknown"
        val columnName =
          if (headers.isEmpty)
            dataType + "_" + columnIndex
          else
            headers(columnIndex)
        (columnName, dataType)
      }
    } groupBy(_._1) map {
      case (columnName, pairs) => (columnName, pairs.map(_._2).toSet)
    }
  }

  def deduceSqlTypesByColumn(dataTypesByColumn: Map[String, Set[String]],
                             headers: List[String]): (List[String], List[ColumnType]) = {
    val sqlTypesByColumn: List[(String, ColumnType)] =
      dataTypesByColumn.toList.map(entry => {
        val (columnName, dataTypes) = entry
        if (dataTypes.size == 1) {
          dataTypes.head match {
            case "Integer" => (columnName, ColumnType.INTEGER)
            case "Decimal" => (columnName, ColumnType.DECIMAL)
            case "Date" => (columnName, ColumnType.DATE)
            case "Boolean" | "Integer-Boolean" => (columnName, ColumnType.BOOLEAN)
            case "Text" => (columnName, ColumnType.LONGNVARCHAR)
            case _ => (columnName, ColumnType.NVARCHAR)
          }
        } else if (dataTypes.size == 2 && dataTypes.count(_.startsWith("Integer")) == 2) {
          (columnName, ColumnType.INTEGER)
        } else {
          (columnName, ColumnType.NVARCHAR)
        }
      })
    // return (columnNames, sqlTypes)
    sqlTypesByColumn
      // sort by original column order
      .sortWith((e1, e2) => headers.indexOf(e1._1) < headers.indexOf(e2._1))
      .unzip
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
