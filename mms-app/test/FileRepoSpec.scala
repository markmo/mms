import javax.jcr.Binary
import org.specs2.mutable._
import service.file.jcr.ModeShapeFileRepoService
import service.FileRepoService

class FileRepoSpec extends Specification {

  "The FileRepoService " should {
    "find the file and return its binary" in new WithRepo {
      val binary: Binary = repo.getBinary("issues.csv")
    }
  }

}

trait WithRepo extends After {

  lazy val repo: FileRepoService = new ModeShapeFileRepoService()

  def after { repo.shutdown() }

}
