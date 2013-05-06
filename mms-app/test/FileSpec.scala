import com.google.inject.Guice
import module.Dependencies
import org.specs2.mutable._

import org.specs2.specification.Scope
import service.FileService

class FileSpec extends Specification {

//  "The FileService " should {
//    "read the first 5 lines" in new WithFileService {
//      fileService.parseFile("issues.csv")
//    }
//    "automatically discover the delimiters and other formatting parameters of the file" in new WithFileService {
//      val dialect = fileService.extractMetadata("issues.csv")
//      dialect.delimiter mustEqual ','
//    }
//  }

}

trait WithFileService extends Scope {

  val injector = Guice.createInjector(new Dependencies())
  val fileService: FileService = injector.getInstance(classOf[FileService])

}
