import sbt._
import Keys._
import play.Project._
import sbt.ExclusionRule

object ApplicationBuild extends Build {

  val appName = "mms"
  val appVersion = "1.0-SNAPSHOT"


  val appDependencies = Seq(
    // Add your project dependencies here
    "postgresql" % "postgresql" % "9.1-901.jdbc4"
    //,"chronostamp" % "chronostamp" % "0.2"
    ,"log4j" % "log4j" % "1.2.17"
    ,javaCore
    ,javaJdbc
    ,javaJpa
    ,"org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final"
    ,"org.hibernate" % "hibernate-envers" % "3.6.9.Final"
    ,"be.objectify" %% "deadbolt-java" % "2.1-SNAPSHOT"
    ,"com.feth" %% "play-authenticate" % "0.2.0-SNAPSHOT"
    ,"com.fasterxml.jackson.core" % "jackson-core" % "2.1.2"
    ,"com.fasterxml.jackson.core" % "jackson-annotations" % "2.1.2"
    ,"com.fasterxml.jackson.core" % "jackson-databind" % "2.1.2"
    ,"com.fasterxml.jackson.datatype" % "jackson-datatype-hibernate3" % "2.1.2"
    ,"com.github.cleverage" %% "elasticsearch" % "0.5.0"
    ,"com.typesafe" %% "play-plugins-mailer" % "2.1-RC2"
    ,"com.google.inject" % "guice" % "3.0"

    // ModeShape
    // defined in https://repository.jboss.org/nexus/content/repositories/releases/org/modeshape/bom/modeshape-bom-embedded/3.1.1.Final/modeshape-bom-embedded-3.1.1.Final.pom
    ,"javax.jcr" % "jcr" % "2.0"
    ,"org.modeshape" % "modeshape-jcr-api" % "3.1.1.Final"
    ,"javax.transaction" % "jta" % "1.1"
    ,"org.modeshape" % "modeshape-common" % "3.1.1.Final"
    ,"org.modeshape" % "modeshape-jcr" % "3.1.1.Final"
    ,"org.modeshape" % "modeshape-schematic" % "3.1.1.Final"
    //,"org.modeshape" % "modeshape-jcr-tck" % "3.1.1.Final" //modeshape-jcr-tck provides a separate testing project that executes all reference implementation's JCR TCK tests on a nightly basis to track implementation progress against the JCR 1.0 specification. This module will likely be retired when the ModeShape JCR implementation is complete, since modeshape-jcr and modeshape-integration-tests will be running the full suite of JCR TCK unit tests.
    ,"org.modeshape" % "modeshape-sequencer-ddl" % "3.1.1.Final"
    ,"org.modeshape" % "modeshape-sequencer-msoffice" % "3.1.1.Final"
    ,"org.modeshape" % "modeshape-sequencer-text" % "3.1.1.Final"
    ,"org.modeshape" % "modeshape-sequencer-xml" % "3.1.1.Final"
    ,"org.modeshape" % "modeshape-sequencer-xsd" % "3.1.1.Final"
    ,"org.modeshape" % "modeshape-sequencer-wsdl" % "3.1.1.Final"
    ,"org.modeshape" % "modeshape-sequencer-zip" % "3.1.1.Final"
    ,"org.modeshape" % "modeshape-extractor-tika" % "3.1.1.Final"
    ,"org.infinispan" % "infinispan-core" % "5.1.2.FINAL" excludeAll(
      ExclusionRule(organization = "org.codehaus.woodstox", name = "woodstox-core-asl"),
      ExclusionRule(organization = "org.codehaus.woodstox", name = "stax2-api")
      )
    ,"org.infinispan" % "infinispan-lucene-directory" % "5.1.2.FINAL"
    ,"org.infinispan" % "infinispan-cachestore-bdbje" % "5.1.2.FINAL"
    ,"org.infinispan" % "infinispan-cachestore-jdbm" % "5.1.2.FINAL"
    ,"org.infinispan" % "infinispan-cachestore-jdbc" % "5.1.2.FINAL"
    ,"c3p0" % "c3p0" % "0.9.1.2"
    ,"org.hibernate" % "hibernate-search-engine" % "4.1.1.Final" excludeAll(
      ExclusionRule(organization = "org.hibernate")
      ) //exclude("org.hibernate", "hibernate-search-analyzers")
    ,"org.hibernate" % "hibernate-search-infinispan" % "4.1.1.Final" excludeAll(
      ExclusionRule(organization = "org.hibernate")
      )
    // MS Office sequencer
    ,"org.apache.poi" % "poi" % "3.8"
    ,"org.apache.poi" % "poi-scratchpad" % "3.8"
    // WSDL sequencer
    ,"wsdl4j" % "wsdl4j" % "1.6.2"
    // XSD sequencer
    ,"org.eclipse.xsd" % "xsd" % "2.2.3"
    ,"org.eclipse.emf" % "common" % "2.4.0"
    ,"org.eclipse.emf" % "ecore" % "2.4.2"
    ,"org.eclipse.emf" % "ecore-change" % "2.2.3"
    ,"org.eclipse.emf" % "ecore-xmi" % "2.4.1"
    // Tika
    ,"org.apache.tika" % "tika-parsers" % "1.2" excludeAll(
      ExclusionRule(organization = "edu.ucar", name = "netcdf"),
      ExclusionRule(organization = "commons-httpclient", name = "commons-httpclient"),
      ExclusionRule(organization = "com.drewnoakes", name = "metadata-extractor"),
      ExclusionRule(organization = "rome", name = "rome"),
      ExclusionRule(organization = "de.l3s.boilerpipe", name = "boilerpipe"),
      ExclusionRule(organization = "org.bouncycastle", name = "bcmail-jdk15"),
      ExclusionRule(organization = "org.bouncycastle", name = "bcprov-jdk15")
      )
    // transitive dependency failing to download from (it appears) http://repo.typesafe.com/typesafe/releases/jakarta-regexp/jakarta-regexp/1.4/jakarta-regexp-1.4.jar, where it accepts the URL but has no content
    ,"jakarta-regexp" % "jakarta-regexp" % "1.4" from "https://repository.jboss.org/nexus/content/groups/developer/jakarta-regexp/jakarta-regexp/1.4/jakarta-regexp-1.4.jar"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    scalaVersion := "2.10.0",
    ebeanEnabled := false,

    //resolvers += Resolver.url("Objectify Play Repository (release)", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
    //resolvers += Resolver.url("Objectify Play Repository (snapshot)", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns),

    //resolvers += Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns),
    //resolvers += Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns),

    //resolvers += Resolver.url("play-authenticate (release)", url("http://joscha.github.com/play-authenticate/repo/releases/"))(Resolver.ivyStylePatterns),
    //resolvers += Resolver.url("play-authenticate (snapshot)", url("http://joscha.github.com/play-authenticate/repo/snapshots/"))(Resolver.ivyStylePatterns),

    resolvers += Resolver.url("GitHub Play2-elasticsearch Repository", url("http://cleverage.github.com/play2-elasticsearch/releases/"))(Resolver.ivyStylePatterns),
    resolvers += "JBoss Repository (release)" at "https://repository.jboss.org/nexus/content/repositories/releases/",
    resolvers += "Public JBoss Repository Group" at "https://repository.jboss.org/nexus/content/groups/public-jboss/",
    resolvers += "Developer Repository Group" at "https://repository.jboss.org/nexus/content/groups/developer/"
//  ).settings(
//    net.virtualvoid.sbt.graph.Plugin.graphSettings: _*
  )

}
