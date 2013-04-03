// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
//addSbtPlugin("play" % "sbt-plugin" % Option(System.getProperty("play.version")).getOrElse("2.1-SNAPSHOT"))
addSbtPlugin("play" % "sbt-plugin" % "2.1.0")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.1")
