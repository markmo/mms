package analysis

import au.com.bytecode.opencsv.{CSVReader => JCSVReader, CSVParser}
import java.io._
import scala.collection.JavaConversions._


/**
 * User: markmo
 * Date: 25/03/13
 * Time: 11:33 AM
 */
class DSVReader protected (reader: Reader, delimiter: String, quoteChar: Char,
                           escapeChar: Char, skipLines: Int,
                           strictQuotes: Boolean, skipInitialSpace: Boolean,
                           ignoreQuotes: Boolean) {

  private val parser = new CSVParser(delimiter, quoteChar, escapeChar, strictQuotes, skipInitialSpace, ignoreQuotes)
  private val underlying: JCSVReader = new JCSVReader(reader, skipLines, parser)

  def apply[A](f: Iterator[Seq[String]] => A): A = {
    try {
      f(this.iterator)
    } finally {
      this.close()
    }
  }

  def readNext(): Option[List[String]] = Option(underlying.readNext).map(_.toList)

  def foreach(f: Seq[String] => Unit): Unit = iterator.foreach(f)

  def iterator: Iterator[Seq[String]] = new Iterator[Seq[String]] {

    private var _next: Option[Seq[String]] = None

    def hasNext: Boolean = {
      _next match {
        case Some(row) => true
        case None => { _next = readNext;  _next.isDefined }
      }
    }

    def next(): Seq[String] = {
      _next match {
        case Some(row) => {
          val _row = row
          _next = None
          _row
        }
        case None => readNext.getOrElse(throw new NoSuchElementException("next on empty iterator"))
      }
    }

  }

  def toStream(): Stream[List[String]] =
    Stream.continually(readNext).takeWhile(_.isDefined).map(_.get)

  def all(): List[List[String]] =
    underlying.readAll().map(_.toList).toList

  def allWithHeaders(): List[Map[String, String]] = {
    readNext() map { headers =>
      val lines = all()
      lines.map(l => headers.zip(l).toMap)
    } getOrElse List()
  }

  def close(): Unit = underlying.close()

}

object DSVReader {

  @deprecated("Use #open instead", "0.5.0")
  def apply(file: File, encoding: String = "UTF-8",
            delimiter: String, quoteChar: Char,
            escapeChar: Char, skipLines: Int,
            strictQuotes: Boolean, skipInitialSpace: Boolean,
            ignoreQuotes: Boolean): DSVReader =
    open(file, encoding, delimiter: String, quoteChar: Char,
      escapeChar: Char, skipLines: Int, strictQuotes: Boolean,
      skipInitialSpace: Boolean, ignoreQuotes: Boolean)

  @deprecated("Use #open instead", "0.5.0")
  def apply(reader: Reader, delimiter: String, quoteChar: Char,
            escapeChar: Char, skipLines: Int,
            strictQuotes: Boolean, skipInitialSpace: Boolean,
            ignoreQuotes: Boolean): DSVReader =
    open(reader, delimiter: String, quoteChar: Char,
      escapeChar: Char, skipLines: Int, strictQuotes: Boolean,
      skipInitialSpace: Boolean, ignoreQuotes: Boolean)

  def open(reader: Reader, delimiter: String, quoteChar: Char,
           escapeChar: Char, skipLines: Int,
           strictQuotes: Boolean, skipInitialSpace: Boolean,
           ignoreQuotes: Boolean): DSVReader =
    new DSVReader(reader, delimiter: String, quoteChar: Char,
      escapeChar: Char, skipLines: Int, strictQuotes: Boolean,
      skipInitialSpace: Boolean, ignoreQuotes: Boolean)

  def open(file: File, delimiter: String, quoteChar: Char,
           escapeChar: Char, skipLines: Int,
           strictQuotes: Boolean, skipInitialSpace: Boolean,
           ignoreQuotes: Boolean): DSVReader =
    open(file, "UTF-8", delimiter: String, quoteChar: Char,
      escapeChar: Char, skipLines: Int, strictQuotes: Boolean,
      skipInitialSpace: Boolean, ignoreQuotes: Boolean)

  def open(file: File, encoding: String,
           delimiter: String, quoteChar: Char,
           escapeChar: Char, skipLines: Int,
           strictQuotes: Boolean, skipInitialSpace: Boolean,
           ignoreQuotes: Boolean): DSVReader = {
    val fin = new FileInputStream(file)
    val reader = new InputStreamReader(fin, encoding)
    open(reader, delimiter: String, quoteChar: Char,
      escapeChar: Char, skipLines: Int, strictQuotes: Boolean,
      skipInitialSpace: Boolean, ignoreQuotes: Boolean)
  }

  def open(file: String, delimiter: String, quoteChar: Char,
           escapeChar: Char, skipLines: Int,
           strictQuotes: Boolean, skipInitialSpace: Boolean,
           ignoreQuotes: Boolean): DSVReader =
    open(new File(file), "UTF-8", delimiter: String, quoteChar: Char,
      escapeChar: Char, skipLines: Int, strictQuotes: Boolean,
      skipInitialSpace: Boolean, ignoreQuotes: Boolean)

  def open(file: String, encoding: String,
           delimiter: String, quoteChar: Char,
           escapeChar: Char, skipLines: Int,
           strictQuotes: Boolean, skipInitialSpace: Boolean,
           ignoreQuotes: Boolean): DSVReader =
    open(new File(file), encoding, delimiter: String, quoteChar: Char,
      escapeChar: Char, skipLines: Int, strictQuotes: Boolean,
      skipInitialSpace: Boolean, ignoreQuotes: Boolean)

}
