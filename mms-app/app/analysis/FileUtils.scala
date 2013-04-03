package analysis

import collection.mutable
import java.util.regex.{Matcher, Pattern}

import math._
import play.api.libs.iteratee._
import scala.annotation.tailrec
import scala.Some

object FileUtils {

  /**
   * Skip whitespace
   *
   * @param p
   * @return
   */
  def dropWhile(p: Char => Boolean): Iteratee[Char, Unit] = Cont {
    case in @ Input.El(char) if !p(char) => Done(Unit, in)
    case in @ Input.EOF => Done(Unit, in)
    case _ => dropWhile(p)
  }

  def dropSpaces = dropWhile(c => c == ' ' || c == '\t' || c == '\r')

  def takeWhile(p: Char => Boolean, data: Seq[Char] = IndexedSeq[Char]()): Iteratee[Char, Seq[Char]] = Cont {
    case in @ Input.El(char) =>
      if (p(char))
        takeWhile(p, data :+ char)
      else
        Done(data, in)
    case in @ Input.EOF => Done(data, in)
    case _ => takeWhile(p, data)
  }

  def peek: Iteratee[Char, Option[Char]] = Cont {
    case in @ Input.El(char) => Done(Some(char), in)
    case in @ Input.EOF => Done(None, in)
    case Input.Empty => peek
  }

  def takeOne: Iteratee[Char, Option[Char]] = Cont {
    case in @ Input.El(char) => Done(Some(char))
    case in @ Input.EOF => Done(None, in)
    case Input.Empty => takeOne
  }

  def expect(char: Char): Iteratee[Char, Unit] = takeOne.flatMap {
    case Some(c) if c == char => Done(Unit)
    case Some(c) => Error("Expected " + char + " but got " + c, Input.El(c))
    case None => Error("Premature end of input, expected: " + char, Input.EOF)
  }

  def unquoted = takeWhile(c => c != ',' && c!= '\n').map(v => v.mkString.trim)

  def quoted = for {
    _ <- expect('"')
    value <- takeWhile(_ != '"')
    _ <- expect('"')
  } yield value.mkString

  def quoted(value: Seq[Char] = IndexedSeq[Char]()): Iteratee[Char, String] = for {
    _ <- expect('"')
    maybeValue <- takeWhile(_ != '"')
    _ <- expect('"')
    nextChar <- peek
    value <- nextChar match {
      case Some('"') => quoted(value ++ maybeValue :+ '"')
      case _ => Done[Char, String]((value ++ maybeValue).mkString)
    }
  } yield value

  def value = for {
    char <- peek
    value <- char match {
      case Some('"') => quoted()
      case None => Error[Char]("Premature end of input, expected a value", Input.EOF)
      case _ => unquoted
    }
  } yield value

  def values(state: Seq[String] = IndexedSeq[String]()): Iteratee[Char, Seq[String]] = for {
    _ <- dropSpaces
    value <- value
    _ <- dropSpaces
    nextChar <- takeOne
    values <- nextChar match {
      case Some('\n') | None => Done[Char, Seq[String]](state :+ value)
      case Some(',') => values(state :+ value)
      case Some(other) => Error("Expected comma, newline or EOF, but found " + other, Input.El(other))
    }
  } yield values

  def csvLines = Enumeratee.grouped(values())

  val preferred = Array(',', '\t', ';', ' ', ':', '|')

  /**
   * Returns a dialect (or Exception) corresponding to the sample
   *
   * @param sample
   * @param delimiters
   * @return Dialect
   */
  def sniff(sample: String, delimiters: Seq[String] = Nil): Dialect = {
    val (quoteChar, doubleQuoted, delimiter, skipInitialSpace) = guessQuoteAndDelimiter(sample, delimiters)
    if (delimiter == None) {
      val (delimiter, skipInitialSpace) = findMultiCharSequences(sample, delimiters) //guessDelimiter(sample, delimiters)
      if (delimiter == None) {
        throw new Exception("Could not determine delimiter")
      }
      Dialect(delimiter = delimiter, skipInitialSpace = Some(skipInitialSpace))
    } else {
      Dialect(quoteChar = quoteChar, doubleQuoted = Some(doubleQuoted),
        delimiter = delimiter, skipInitialSpace = Some(skipInitialSpace))
    }
  }

  /**
   * Looks for text enclosed between two identical quotes
   * (the probable quotechar) which are preceded and followed
   * by the same character (the probable delimiter).
   * For example:
   *                  ,'some text',
   * The quote with the most wins, same with the delimiter.
   * If there is no quotechar the delimiter can't be determined
   * this way.
   *
   * @param data
   * @param delimiters
   * @return (quotechar, doublequote, delimiter, skipinitialspace)
   */
  def guessQuoteAndDelimiter(data: String, delimiters: Seq[String] = Nil): (Option[Char], Boolean, Option[String], Boolean) = {

    val d = data.split("\n").take(10).mkString("\n")

//    val del = """[^\w\n"\']""" \ "delim"
//    val spc = " ?" \ "space"
//    val qot = """["\']""" \ "quote"

    var matches: Matcher = null
    val regexes = Array(
//      del - spc - qot - ".*?" - qot.?= - del.?=,
      "(?<delim>[^\\w\\n\"\\']+)(?<space> ?)(?<quote>[\"\\']).*?(\\k<quote>)(\\k<delim>)",
      "(?:^|\\n)(?<quote>[\"\\']).*?(\\k<quote>)(?<delim>[^\\w\\n\"\\']+)(?<space> ?)",
//      "(?<delim>>[^\\w\\n\"\\']+)(?<space> ?)(?<quote>[\"\\']).*?(?=\\k<quote>)(?:$|\\n)",
      "(?:^|\\n)(?<quote>[\"\\']).*?(\\k<quote>)(?:$|\\n)"
    )

    // embedded construction flags        meanings
    // flags
    // (?i)     Pattern.CASE_INSENSITIVE	Enables case-insensitive matching.
    // (?d)     Pattern.UNIX_LINES        Enables Unix lines mode.
    // (?m)     Pattern.MULTILINE         Enables multi line mode.
    // (?s)     Pattern.DOTALL            Enables "." to match line terminators.
    // (?u)     Pattern.UNICODE_CASE      Enables Unicode-aware case folding.
    // (?x)     Pattern.COMMENTS          Permits white space and comments in the pattern.
    // ---      Pattern.CANON_EQ          Enables canonical equivalence.
    //
    def findMatches() {
      for (str <- regexes) {
        val p: Pattern = Pattern.compile(str, Pattern.MULTILINE | Pattern.DOTALL)
        matches = p.matcher(d)
        if (matches.find(0)) return
      }
    }
    findMatches()
    if (!matches.find(0)) {
      return (None, false, None, false)
    }
    val quotes = new mutable.HashMap[String, Int]()
    val delims = new mutable.HashMap[String, Int]()
    var spaces = 0
    var delim: String = null
//    for (m <- matches) {
      val quote = matches.group("quote")
      if (quote != null) {
        quotes(quote) = quotes.getOrElse(quote, 0) + 1
      }
      delim = matches.group("delim")
      if (delim != null && (delimiters.isEmpty || delimiters.contains(delim))) {
        delims(delim) = delims.getOrElse(delim, 0) + 1
        val space = matches.group("space")
        if (space != null) {
          spaces += 1
        }
      }
//    }
    val quoteChar = (quotes.toList maxBy (_._2))._1
    var skipInitialSpace = false
    if (!delims.isEmpty) {
      delim = (delims.toList maxBy (_._2))._1
      skipInitialSpace = delims(delim) == spaces
      if (delim == "\n") { // most likely a file with a single column
        delim = ""
      }
    } else {
      // there is *no* delimiter, it's a single column of quoted data
      delim = ""
      skipInitialSpace = false
    }
    // if we see an extra quote between delimiters, we've got a
    // double quoted format
    val del = Pattern.quote(delim)
    val delFirstChar = Pattern.quote(delim(0).toString)
    val qot = Pattern.quote(quoteChar)
    val dqr = (s"(?m)(($del)|^)\\W*$qot[^$delFirstChar\\n]*$qot[^$delFirstChar\\n]*$qot\\W*(($del)|$$)").r
    val doubleQuoted = (dqr findFirstIn d).nonEmpty

    (Some(quoteChar(0)), doubleQuoted, Some(delim), skipInitialSpace)
  }

  /**
   * The delimiter /should/ occur the same number of times on
   * each row. However, due to malformed data, it may not. We don't want
   * an all or nothing approach, so we allow for small variations in this
   * number.
   *   1) build a table of the frequency of each character on every line.
   *   2) build a table of frequencies of this frequency (meta-frequency?),
   *      e.g.  'x occurred 5 times in 10 rows, 6 times in 1000 rows,
   *      7 times in 2 rows'
   *   3) use the mode of the meta-frequency to determine the /expected/
   *      frequency for that character
   *   4) find out how often the character actually meets that goal
   *   5) the character that best meets its goal is the delimiter
   *      For performance reasons, the data is evaluated in chunks, so it can
   *      try and evaluate the smallest portion of the data possible, evaluating
   *      additional chunks as necessary.
   *
   * @param data
   * @param delimiters
   * @return
   */
  def guessDelimiter(data: String, delimiters: Seq[String] = Nil): (Option[Char], Boolean) = {
    val rows = data.split("\n")
    val ascii = (0 to 127).map(_.toChar)
    val chunkLength = min(10, rows.length)
    var iteration = 0
    val charFrequency = mutable.HashMap[Char, mutable.HashMap[Int, Int]]()
    val modes = new mutable.HashMap[Char, (Int, Int)]()
    val delims = new mutable.HashMap[Char, (Int, Int)]()
    var delim: Option[Char] = None
    var skipInitialSpace = false
    var start = 0
    var end = min(chunkLength, rows.length)
    while (start < rows.length) {
      iteration += 1
      for (line <- rows.slice(start, end)) {
        for (char <- ascii) {
          val metaFrequency = charFrequency.getOrElse(char, mutable.HashMap())
          val freq = line.count(_ == char)
          metaFrequency(freq) = metaFrequency.getOrElse(freq, 0) + 1
          charFrequency(char) = metaFrequency
        }
      }
      for ((char, metaFrequency) <- charFrequency) {
        val items = metaFrequency.toList
        if (!(items.length == 1 && items(0)._1 == 0)) {
          if (items.length > 0) {
            val maxItem = items maxBy (_._2)
            val otherItems = items.filterNot(_ == maxItem)
            val otherFreqs = otherItems.map(_._2)
            val total = otherFreqs.sum
            modes(char) = (maxItem._1, maxItem._2 - total)
          } else {
            modes(char) = items(0)
          }
        }
      }
      val modeList = modes.toList
      val total = chunkLength * iteration

      // (rows of consistent data) / (number of rows) = 100%
      var consistency = 1.0

      // minimum consistency threshold
      val threshold = 0.9
      while (delims.isEmpty && consistency >= threshold) {
        for ((k, v) <- modeList) {
          if (v._1 > 0 && v._2 > 0) {
            if ((v._2 / total) >= consistency &&
              (delimiters == Nil || delimiters.contains(k))) {
              delims(k) = v
            }
          }
        }
        consistency -= 0.01
      }
      if (delims.keySet.size == 1) {
        delim = Some(delims.keySet.head)
        skipInitialSpace = (rows(0).count(_ == delim.get) == (s"$delim ").r.findAllIn(rows(0)).length)
        return (delim, skipInitialSpace)
      }
      // analyze another chunkLength lines
      start = end
      end += chunkLength
    }
    if (delims.isEmpty) {
      return (None, false)
    }

    // if there's more than one, fall back to a 'preferred' list
    if (!delims.isEmpty) {
      for (d <- preferred) {
        if (delims.keySet.contains(d)) {
          skipInitialSpace = (rows(0).count(_ == d) == (s"$d ").r.findAllIn(rows(0)).length)
          return (Some(d), skipInitialSpace)
        }
      }
    }
    // nothing else indicates a preference, pick the character that
    // dominates(?)
    val items = delims.map(_.swap).toList.sorted
    delim = Some(items.last._2)
    skipInitialSpace = (rows(0).count(_ == delim.get) == (s"$delim ").r.findAllIn(rows(0)).length)
    (delim, skipInitialSpace)
  }

  /**
   * Creates a dictionary of types of data in each column. If any
   * column is of a single type (say, integers), *except* for the first
   * row, then the first row is presumed to be labels. If the type
   * can't be determined, it is assumed to be a string in which case
   * the length of the string is the determining factor: if all of the
   * rows except for the first are the same length, it's a header.
   * Finally, a 'vote' is taken at the end for each column, adding or
   * subtracting from the likelihood of the first row being a header.
   *
   * @param sample
   * @return
   */
  def hasHeader(sample: List[List[String]]): Boolean = {
    val header = sample.head
    val lenColumns = header.length
    val columnTypes = new mutable.HashMap[Int, Any]
    for (i <- 0 to lenColumns) {
      columnTypes(i) = None
    }
    sample.tail
      .take(20)
      .filter(_.length == lenColumns)
      .foreach((row) =>
      for (col <- 0 to lenColumns - 1) {
        def getType(str: String): Any = {
          try {
            str.toInt
            return 'int
          } catch {
            case e: NumberFormatException => () //pass
          }
          try {
            str.toFloat
            return 'float
          } catch {
            case e: NumberFormatException => () //pass
          }
          str.length
        }
        val thisType = getType(row(col))
        if (thisType != columnTypes(col)) {
          if (columnTypes(col) == None) {
            columnTypes(col) = thisType
          } else {
            columnTypes(col) = None
          }
        }
      }
    )
    var hasHeader = 0
    for ((col, colType) <- columnTypes) {
      def hasHeaderInc(colType: Any): Int = colType match {
        case _: Int =>
          if (header(col).length == colType) -1 else 1
        case 'int =>
          try {
            header(col).toInt
            -1
          } catch {
            case e: NumberFormatException => 1
          }
        case 'float =>
          try {
            header(col).toFloat
            -1
          } catch {
            case e: NumberFormatException => 1
          }
        case _ => 0
      }
      hasHeader += hasHeaderInc(colType)
    }
    hasHeader > 0
  }

  def findMultiCharSequences(data: String, delimiters: Seq[String] = Nil): (Option[String], Boolean) = {
    val rows = data.split("\n")
    val chunkLength = min(10, rows.length)
    var iteration = 0
    val seqFrequency = mutable.HashMap[String, mutable.HashMap[Int, Int]]()
    val modes = new mutable.HashMap[String, (Int, Int)]()
    val delims = new mutable.HashMap[String, (Int, Int)]()
    var delim: Option[String] = None
    var skipInitialSpace = false
    var start = 0
    var end = min(chunkLength, rows.length)
    val slidingWindow = 5
    while (start < rows.length) {
      iteration += 1
      for (row <- rows.slice(start, end)) {
        val counts = mutable.HashMap[String, Int]().withDefaultValue(0)
        for (w <- 2 to slidingWindow;
             i <- 0 to row.length - w) {
          val seq = row.substring(i, i + w)
          counts(seq) += 1
        }
        counts.filter(_._2 > 1).foreach((k) => {
          val metaFrequency = seqFrequency.getOrElse(k._1, mutable.HashMap())
          metaFrequency(k._2) = metaFrequency.getOrElse(k._2, 0) + 1
          seqFrequency(k._1) = metaFrequency
        })
      }
      for ((seq, metaFrequency) <- seqFrequency) {
        val items = metaFrequency.toList
        if (!(items.length == 1 && items(0)._1 == 0)) {
          if (items.length > 0) {
            val maxItem = items maxBy (_._2)
            val otherItems = items.filterNot(_ == maxItem)
            val otherFreqs = otherItems.map(_._2)
            val total = otherFreqs.sum
            modes(seq) = (maxItem._1, maxItem._2 - total)
          } else {
            modes(seq) = items(0)
          }
        }
      }
      val modeList = modes.toList
      val total = chunkLength * iteration

      // (rows of consistent data) / (number of rows) = 100%
      var consistency = 1.0

      // minimum consistency threshold
      val threshold = 0.9
      while (delims.isEmpty && consistency >= threshold) {
        for ((k, v) <- modeList) {
          if (v._1 > 0 && v._2 > 0) {
            if ((v._2 / total) >= consistency &&
              (delimiters == Nil || delimiters.contains(k))) {
              delims(k) = v
            }
          }
        }
        consistency -= 0.01
      }
      if (delims.keySet.size == 1) {
        delim = Some(delims.keySet.head)
        skipInitialSpace = (countSubstring(rows(0), delim.get) == (s"$delim ").r.findAllIn(rows(0)).length)
        return (delim, skipInitialSpace)
      }
      // analyze another chunkLength lines
      start = end
      end += chunkLength
    }
    if (delims.isEmpty) {
      return (None, false)
    }

    // if there's more than one, fall back to a 'preferred' list
    if (!delims.isEmpty) {
      for (d <- preferred) {
        val del = d.toString
        if (delims.keySet.contains(del)) {
          skipInitialSpace = (rows(0).count(_ == d) == (s"$d ").r.findAllIn(rows(0)).length)
          return (Some(del), skipInitialSpace)
        }
      }
    }
    // nothing else indicates a preference, pick the character that
    // dominates(?)
    val items = delims.toSeq sortWith {
      case ((s1, (f1, _)), (s2, (f2, _))) =>
        if (f1 == f2)
          s1.length > s2.length
        else
          f1 > f2
    }
    delim = Some(items.head._1)
    skipInitialSpace = (countSubstring(rows(0), delim.get) == (s"$delim ").r.findAllIn(rows(0)).length)
    (delim, skipInitialSpace)
  }

  def countSubstring(str: String, sub: String): Int = {
    @tailrec def count(pos: Int, c: Int): Int = {
      val i = str indexOf(sub, pos)
      if (i == -1) c else count(i + sub.length, c + 1)
    }
    count(0, 0)
  }
}

/**
 *
 * @param lineTerminator specifies the character sequence which should
 *   terminate rows.
 *
 * @param quoting controls when quotes should be generated by the writer.
 *   It can take on any of the following module constants:
 *
 *   QUOTE_MINIMAL means only when required, for example, when a
 *     field contains either the quotechar or the delimiter
 *   QUOTE_ALL means that quotes are always placed around fields.
 *   QUOTE_NONNUMERIC means that quotes are always placed around
 *     fields which do not parse as integers or floating point
 *     numbers.
 *   QUOTE_NONE means that quotes are never placed around fields.
 *
 * @param doubleQuoted controls the handling of quotes inside fields.  When
 *   True, two consecutive quotes are interpreted as one during read,
 *   and when writing, each quote character embedded in the data is
 *   written as two quotes
 *
 * @param delimiter specifies a string to use as the column separator.
 *
 * @param quoteChar specifies a one-character string to use as the
 *   quoting character.  It defaults to '"'.
 *
 * @param skipInitialSpace specifies how to interpret whitespace which
 *   immediately follows a delimiter.  It defaults to False, which
 *   means that whitespace immediately following a delimiter is part
 *   of the following field.
 *
 * @param escapeChar specifies a one-character string used to escape
 *   the delimiter when quoting is set to QUOTE_NONE.
 */
case class Dialect(
  lineTerminator: String = "\\r\\n",
  quoting: String = "minimal",
  doubleQuoted: Option[Boolean] = None,
  delimiter: Option[String] = None,
  quoteChar: Option[Char] = None,
  skipInitialSpace: Option[Boolean] = None,
  escapeChar: Char = '\\',
  var hasHeader: Option[Boolean] = None
  ) {
  override def toString = {
    val quot = if (quoteChar == None) "Unknown" else s"'${quoteChar.get}'"
    val dquoted = if (doubleQuoted == None) "Unknown" else doubleQuoted.get
    val delim = if (delimiter == None) "Unknown" else s"'${delimiter.get}'"
    val skip = if (skipInitialSpace == None) "Unknown" else skipInitialSpace.get
    s"Dialect(line-terminator: '$lineTerminator', quoting: $quoting, " +
      s"quote-char: $quot, double-quote: $dquoted, delimiter: $delim, " +
      s"skip-initial-space: $skip, escape-char: '$escapeChar')"
  }
}
