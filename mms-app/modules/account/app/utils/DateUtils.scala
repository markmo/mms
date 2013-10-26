package utils.account;

import java.util.Date
import java.text.{ParseException, SimpleDateFormat}

/**
 * User: markmo
 * Date: 29/03/13
 * Time: 10:22 AM
 */
object DateUtils {

  val dateFormats = List(
    // pattern                      // example
    "yyyy-MM-dd'T'HH:mm:ss.SSSZ",   // 2001-07-04T12:08:56.235-0700
    "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", // 2001-07-04T12:08:56.235-07:00
    "EEE, MMM d, ''yy",             // Wed, Jul 4, '01
    "EEE, MMM d, yyyy",             // Wed, Jul 4, 2001
    "yyyy.MM.dd",                   // 2001.07.04
    "yyyy-MM-dd",                   // 2001-07-04
    "yyyy/MM/dd",                   // 2001/07/04
    "dd.MM.yyyy",                   // 04.07.2001
    "dd-MM-yyyy",                   // 04-07-2001
    "dd/MM/yyyy",                   // 04/07/2001
    "MM.dd.yyyy",                   // 07.04.2001
    "MM-dd-yyyy",                   // 07-04-2001
    "MM/dd/yyyy",                   // 07/04/2001
    "dd.MM.yy",                     // 04.07.01
    "dd-MM-yy",                     // 04-07-01
    "dd/MM/yy",                     // 04/07/01
    "MM.dd.yy",                     // 07.04.01
    "MM-dd-yy",                     // 07-04-01
    "MM/dd/yy",                     // 07/04/01
    "yyyy-MM-dd",
    "yyyy-MM-dd'T'HH",
    "yyyy-MM-dd HH",
    "yyyy-MM-dd'T'HH:mm",
    "yyyy-MM-dd HH:mm",
    "yyyy-MM-dd'T'HH:mm:ss",
    "yyyy-MM-dd HH:mm:ss",
    "yyyy-MM-dd'T'HH:mm:ss.SSS",
    "yyyy-MM-dd HH:mm:ss.SSS",
    "yyyy-MM-dd'T'HH:mm:ss Z",
    "yyyy-MM-dd HH:mm:ss Z")

  def parseDate(value: String) = {
    def parse(formats: List[String]): Option[Date] = {
      if (formats.isEmpty) {
        None
      } else {
        val format = formats.head
        try {
          val dateFormat = new SimpleDateFormat(format)
          Some(dateFormat.parse(value))
        } catch {
          case _: ParseException => parse(formats.tail)
          case e: IllegalArgumentException =>
            println(s"Illegal format: $format")
            throw e
        }
      }
    }
    parse(dateFormats)
  }

}
