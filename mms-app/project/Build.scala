import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "mms"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here
      "postgresql" % "postgresql" % "9.1-901.jdbc4"
      //,"chronostamp" % "chronostamp" % "0.2"
      ,"log4j" % "log4j" % "1.2.17"
      //,"org.hibernate" % "hibernate-entitymanager" % "4.1.8.Final"
      //,javaCore
      //,javaJdbc
      //,javaJpa
      ,"org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final"
      ,"org.hibernate" % "hibernate-envers" % "3.6.9.Final"
      ,"be.objectify" % "deadbolt-2_2.9.1" % "1.1.3-SNAPSHOT"
      ,"com.feth" % "play-authenticate_2.9.1" % "0.2.1-SNAPSHOT"
      ,"com.fasterxml.jackson.core" % "jackson-core" % "2.1.2"
      ,"com.fasterxml.jackson.core" % "jackson-annotations" % "2.1.2"
      ,"com.fasterxml.jackson.core" % "jackson-databind" % "2.1.2"
      ,"com.fasterxml.jackson.datatype" % "jackson-datatype-hibernate3" % "2.1.2"
      ,"com.typesafe" % "play-plugins-guice" % "2.0.3"
      ,"com.github.cleverage" % "elasticsearch_2.9.1" % "0.4.2"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      // Add your own project settings here
      ebeanEnabled := false,

      resolvers += Resolver.url("Objectify Play Repository (release)", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("Objectify Play Repository (snapshot)", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns),

      resolvers += Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns),

      resolvers += Resolver.url("play-authenticate (release)", url("http://joscha.github.com/play-authenticate/repo/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("play-authenticate (snapshot)", url("http://joscha.github.com/play-authenticate/repo/snapshots/"))(Resolver.ivyStylePatterns),

      resolvers += Resolver.url("GitHub Play2-elasticsearch Repository", url("http://cleverage.github.com/play2-elasticsearch/releases/"))(Resolver.ivyStylePatterns)
    ).settings(
      net.virtualvoid.sbt.graph.Plugin.graphSettings: _*
    )

}
