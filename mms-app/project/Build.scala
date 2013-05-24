import sbt._
import Keys._
import play.Project._
import sbt.ExclusionRule

object ApplicationBuild extends Build {

  val appName = "mms"
  val appVersion = "1.0-SNAPSHOT"


  val appDependencies = Seq(
    // Add your project dependencies here
    "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
    ,"log4j" % "log4j" % "1.2.17"
    ,javaCore
    ,javaJdbc
    ,javaJpa
    ,"org.hibernate" % "hibernate-entitymanager" % "4.2.2.Final"
    ,"org.hibernate" % "hibernate-envers" % "4.2.2.Final"
    ,"be.objectify" %% "deadbolt-java" % "2.1-SNAPSHOT"
    ,"com.feth" %% "play-authenticate" % "0.2.5-SNAPSHOT"
    ,"com.fasterxml.jackson.core" % "jackson-core" % "2.2.1"
    ,"com.fasterxml.jackson.core" % "jackson-annotations" % "2.2.1"
    ,"com.fasterxml.jackson.core" % "jackson-databind" % "2.2.1"
    ,"com.fasterxml.jackson.datatype" % "jackson-datatype-hibernate4" % "2.2.1"
    ,"com.clever-age" % "play2-elasticsearch" % "0.5-SNAPSHOT" excludeAll(
      ExclusionRule(organization = "org.apache.lucene")
      )
    ,"org.apache.lucene" % "lucene-analyzers" % "3.6.2"
    ,"org.apache.lucene" % "lucene-highlighter" % "3.6.2"
    ,"org.apache.lucene" % "lucene-memory" % "3.6.2"
    ,"org.apache.lucene" % "lucene-queries" % "3.6.2"

    ,"com.typesafe" %% "play-plugins-mailer" % "2.1.0"
    ,"com.google.inject" % "guice" % "3.0"
    ,"net.sf.opencsv" % "opencsv" % "2.4-SNAPSHOT"
    ,"mms" % "mms-models" % "1.0-SNAPSHOT" changing()
//    ,"com.wordnik" %% "swagger-play2" % "1.2.1-SNAPSHOT"
    ,"org.eobjects.analyzerbeans" % "AnalyzerBeans-basic-analyzers" % "0.30"
    ,"org.eobjects.analyzerbeans" % "AnalyzerBeans-cli" % "0.30" excludeAll(
      ExclusionRule(organization = "berkeleydb")
      )
    ,"org.eobjects.metamodel" % "MetaModel-pojo" % "3.4.1"
    ,"nz.co.datascience" % "rel_2.9.1" % "0.3.1"

    // Hadoop
    ,"org.apache.hadoop" % "hadoop-core" % "1.1.2"
    ,"org.apache.hadoop" % "hadoop-hdfs" % "2.0.3-alpha" excludeAll(
      ExclusionRule(artifact = "commons-daemon")
      ) // bad organisation in 1.0.3.pom expected='commons-daemon' found='org.apache.commons'
    ,"commons-daemon" % "commons-daemon" % "1.0.13"

    // Mongo
    ,"org.mongodb" % "mongo-java-driver" % "2.11.0"

    // ModeShape
    // defined in https://repository.jboss.org/nexus/content/repositories/releases/org/modeshape/bom/modeshape-bom-embedded/3.1.3.Final/modeshape-bom-embedded-3.1.3.Final.pom
    ,"javax.jcr" % "jcr" % "2.0"
    ,"org.modeshape" % "modeshape-jcr-api" % "3.2-SNAPSHOT"
    ,"javax.transaction" % "jta" % "1.1"

    // causing "NoSuchMethodError: org.jboss.logging.Logger.getMessageLogger"
//    ,"org.jboss.jbossts" % "jbossjta" % "4.16.2.Final" excludeAll(
//      ExclusionRule(organization = "commons-httpclient", artifact = "commons-httpclient"),
//      ExclusionRule(organization = "org.jboss.ironjacamar", artifact = "ironjacamar-spec-api"),
//      ExclusionRule(organization = "org.jboss.logging", artifact = "jboss-logging-spi"),
//      ExclusionRule(organization = "org.jboss.logging", artifact = "jboss-logging"),
//      ExclusionRule(organization = "org.jboss.logging", artifact = "jboss-logging-processor"),
//      ExclusionRule(organization = "org.jboss.logging", artifact = "jboss-logging-generator"),
//      ExclusionRule(organization = "org.jboss.logging", artifact = "jboss-logging-tools"),
//      ExclusionRule(organization = "org.jboss.ws.native", artifact = "jbossws-native-core"),
//      ExclusionRule(organization = "emma", artifact = "emma"),
//      ExclusionRule(organization = "emma", artifact = "emma-ant"),
//      ExclusionRule(organization = "org.hornetq", artifact = "hornetq-core"),
//      ExclusionRule(organization = "org.jboss.netty", artifact = "netty"),
//      ExclusionRule(organization = "tanukisoft", artifact = "wrapper"),
//      ExclusionRule(organization = "jacorb", artifact = "jacorb"),
//      ExclusionRule(organization = "jacorb", artifact = "idl"),
//      ExclusionRule(organization = "jfree", artifact = "jfreechart"),
//      ExclusionRule(organization = "jfree", artifact = "jcommon"),
//      ExclusionRule(organization = "org.jboss.integration", artifact = "jboss-corba-ots-spi"),
//      ExclusionRule(organization = "org.jboss.integration", artifact = "jboss-transaction-spi"),
//      ExclusionRule(organization = "org.jboss.jbossas", artifact = "jboss-server-manager"),
//      ExclusionRule(organization = "org.jboss.spec.javax.ejb", artifact = "jboss-ejb-api_3.1_spec"),
//      ExclusionRule(organization = "org.jboss.naming", artifact = "jnp-client"),
//      ExclusionRule(organization = "org.jboss.spec.javax.servlet", artifact = "jboss-servlet-api_3.0_spec"),
//      ExclusionRule(organization = "org.jboss.ws", artifact = "jbossws-common"),
//      ExclusionRule(organization = "org.slf4j", artifact = "jcl-over-slf4j"),
//      ExclusionRule(organization = "org.slf4j", artifact = "slf4j-api"),
//      ExclusionRule(organization = "stax", artifact = "stax-api"),
//      ExclusionRule(organization = "org.jboss.spec.javax.resource", artifact = "jboss-connector-api_1.5_spec"),
//      ExclusionRule(organization = "org.jboss.remoting", artifact = "jboss-remoting"),
//      ExclusionRule(organization = "dom4j", artifact = "dom4j"),
//      ExclusionRule(organization = "commons-codec", artifact = "commons-codec"),
//      ExclusionRule(organization = "org.jboss.logmanager", artifact = "jboss-logmanager"),
//      ExclusionRule(organization = "org.hibernate.javax.persistence", artifact = "hibernate-jpa-2.0-api"),
//      ExclusionRule(organization = "commons-logging", artifact = "commons-logging"),
//      ExclusionRule(organization = "org.jboss.spec.javax.transaction", artifact = "jboss-transaction-api_1.1_spec")
//      )

    ,"org.modeshape" % "modeshape-common" % "3.2-SNAPSHOT"
    ,"org.modeshape" % "modeshape-jcr" % "3.2-SNAPSHOT"
    ,"org.modeshape" % "modeshape-schematic" % "3.2-SNAPSHOT"
    ,"org.modeshape" % "modeshape-sequencer-ddl" % "3.2-SNAPSHOT"
    ,"org.modeshape" % "modeshape-sequencer-msoffice" % "3.2-SNAPSHOT"
    ,"org.modeshape" % "modeshape-sequencer-text" % "3.2-SNAPSHOT"
    ,"org.modeshape" % "modeshape-sequencer-xml" % "3.2-SNAPSHOT"
    ,"org.modeshape" % "modeshape-sequencer-xsd" % "3.2-SNAPSHOT"
    ,"org.modeshape" % "modeshape-sequencer-wsdl" % "3.2-SNAPSHOT"
    ,"org.modeshape" % "modeshape-sequencer-zip" % "3.2-SNAPSHOT"
    ,"org.modeshape" % "modeshape-extractor-tika" % "3.2-SNAPSHOT"
    ,"org.infinispan" % "infinispan-core" % "5.3.0.Beta2" excludeAll(
      ExclusionRule(organization = "org.codehaus.woodstox", name = "woodstox-core-asl"),
      ExclusionRule(organization = "org.codehaus.woodstox", name = "stax2-api")
      )
    ,"org.infinispan" % "infinispan-lucene-directory" % "5.3.0.Beta2"
    ,"org.infinispan" % "infinispan-cachestore-bdbje" % "5.3.0.Beta2"
    ,"org.infinispan" % "infinispan-cachestore-jdbm" % "5.3.0.Beta2"
    ,"org.infinispan" % "infinispan-cachestore-jdbc" % "5.3.0.Beta2"
    ,"c3p0" % "c3p0" % "0.9.1.2" //same as the one used by Infinispan's JDBC cache store
    ,"org.hibernate" % "hibernate-search-engine" % "4.2.0.Final" excludeAll(
      ExclusionRule(organization = "org.hibernate")
      )
    ,"org.hibernate" % "hibernate-search-infinispan" % "4.2.0.Final" excludeAll(
      ExclusionRule(organization = "org.hibernate")
      )
    // MS Office sequencer
    ,"org.apache.poi" % "poi" % "3.9"
    ,"org.apache.poi" % "poi-scratchpad" % "3.9"
    // WSDL sequencer
    ,"wsdl4j" % "wsdl4j" % "1.6.2"
    // XSD sequencer
    ,"org.eclipse.xsd" % "xsd" % "2.2.3"
    ,"org.eclipse.emf" % "common" % "2.4.0"
    ,"org.eclipse.emf" % "ecore" % "2.4.2"
    ,"org.eclipse.emf" % "ecore-change" % "2.2.3"
    ,"org.eclipse.emf" % "ecore-xmi" % "2.4.1"
    // Tika
    ,"org.apache.tika" % "tika-parsers" % "1.3" excludeAll(
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
    scalaVersion := "2.10.1",
    ebeanEnabled := false,

    offline := true,

    resolvers += Resolver.url("Objectify Play Repository (release)", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
    resolvers += Resolver.url("Objectify Play Repository (snapshot)", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns),

    resolvers += Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns),
    resolvers += Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns),

    resolvers += Resolver.url("play-authenticate (release)", url("http://joscha.github.com/play-authenticate/repo/releases/"))(Resolver.ivyStylePatterns),
    resolvers += Resolver.url("play-authenticate (snapshot)", url("http://joscha.github.com/play-authenticate/repo/snapshots/"))(Resolver.ivyStylePatterns),

    resolvers += Resolver.url("play-plugin-releases", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns),
    //resolvers += Resolver.url("play-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns),
    resolvers += "JBoss Repository (release)" at "https://repository.jboss.org/nexus/content/repositories/releases/",
    resolvers += "Public JBoss Repository Group" at "https://repository.jboss.org/nexus/content/groups/public-jboss/",
    resolvers += "Developer Repository Group" at "https://repository.jboss.org/nexus/content/groups/developer/",
    resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
    resolvers += "sonatype-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases"
  ).settings(
    net.virtualvoid.sbt.graph.Plugin.graphSettings: _*
  )

}
