import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "mms"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "postgresql" % "postgresql" % "9.1-901.jdbc4"
      //,"chronostamp" % "chronostamp" % "0.2"
      //,"log4j" % "log4j" % "1.2.17"
      //,"org.hibernate" % "hibernate-entitymanager" % "4.1.8.Final"
//      ,javaCore
//      ,javaJdbc
//      ,javaJpa
      ,"org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final"
      ,"be.objectify" %% "deadbolt-2" % "1.1.3-SNAPSHOT"
      ,"com.feth" %% "play-authenticate" % "0.2.1-SNAPSHOT"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here
      //ebeanEnabled := false,
      resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns)
      //resolvers += Resolver.url("play-authenticate (release)", url("http://joscha.github.com/play-authenticate/repo/releases/"))(Resolver.ivyStylePatterns),
      //resolvers += Resolver.url("play-authenticate (snapshot)", url("http://joscha.github.com/play-authenticate/repo/snapshots/"))(Resolver.ivyStylePatterns)
    )

}
