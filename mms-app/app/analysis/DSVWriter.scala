package analysis

import au.com.bytecode.opencsv.{ CSVWriter => JCSVWriter }
import java.io.{ File, Writer, FileOutputStream, OutputStreamWriter }
import scala.collection.JavaConversions._

/**
 * User: markmo
 * Date: 25/03/13
 * Time: 11:37 AM
 */
class DSVWriter protected (writer: Writer) {

  private val underlying: JCSVWriter = new JCSVWriter(writer)

  def apply[A](f: DSVWriter => A): A = {
    try {
      f(this)
    } finally {
      this.close()
    }
  }

  def close(): Unit = underlying.close()

  def flush(): Unit = underlying.close()

  def writeAll(allLines: Seq[Seq[Any]]): Unit = {
    underlying.writeAll(allLines.map(_.toArray.map(_.toString)))
    if (underlying.checkError) {
      throw new java.io.IOException("Failed to write")
    }
  }

  def writeRow(fields: Seq[Any]): Unit = {
    underlying.writeNext(fields.map(_.toString).toArray)
    if (underlying.checkError) {
      throw new java.io.IOException("Failed to write")
    }
  }
}

object DSVWriter {

  @deprecated("Use #open instead", "0.5.0")
  def apply(file: File, encoding: String = "UTF-8"): DSVWriter = open(file, false, encoding)

  @deprecated("Use #open instead", "0.5.0")
  def apply(writer: Writer): DSVWriter = open(writer)

  def open(writer: Writer): DSVWriter = new DSVWriter(writer)

  def open(file: File): DSVWriter = open(file, false, "UTF-8")

  def open(file: File, encoding: String): DSVWriter = open(file, false, encoding)

  def open(file: File, append: Boolean): DSVWriter = open(file, append, "UTF-8")

  def open(file: File, append: Boolean, encoding: String): DSVWriter = {
    val fos = new FileOutputStream(file, append)
    val writer = new OutputStreamWriter(fos, encoding)
    open(writer)
  }

  def open(file: String): DSVWriter = open(file, false, "UTF-8")

  def open(file: String, encoding: String): DSVWriter = open(file, false, encoding)

  def open(file: String, append: Boolean): DSVWriter = open(file, append, "UTF-8")

  def open(file: String, append: Boolean, encoding: String): DSVWriter = open(new File(file), append, encoding)

}
