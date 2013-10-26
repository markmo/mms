import java.io.File
import models.file.{FileUpload, FileContext}
import org.specs2.mutable._
import org.specs2.specification.Scope
import service.file.hdfs.HadoopFileRepoService
import service.FileRepoService

class HadoopRepoSpec extends Specification {

  "The FileRepoService " should {
    "store the file in the HDFS" in new WithHadoop {
      val file = new File("/Users/markmo/Downloads/test/issues.csv")
      val upload = new FileUpload()
      upload.setName("issues.csv")
      upload.setFile(file)
      repo.store(new FileContext("test", "1"), upload)
      val retfile = repo.index("mms/test/1").get(0)
      retfile.get("name") mustEqual "issues.csv"
    }
  }

}

trait WithHadoop extends Scope {

  lazy val repo: FileRepoService = new HadoopFileRepoService()

}
