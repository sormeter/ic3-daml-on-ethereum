import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.1-SNAPSHOT"
ThisBuild / organization     := "com.daml"
ThisBuild / organizationName := "Digital Asset, LLC"

lazy val sdkVersion = "100.12.25"

// This task is used by the integration test to detect which version of Ledger API Test Tool to use.
val printSdkVersion= taskKey[Unit]("printSdkVersion")
printSdkVersion := println(sdkVersion)


assemblyMergeStrategy in assembly := {
  case "META-INF/io.netty.versions.properties" =>
    // Looks like multiple versions patch versions of of io.netty are getting
    // into dependency graph, choose one.
    MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
assemblyJarName in assembly := "damlonx-example.jar"

lazy val root = (project in file("."))
  .settings(
    name := "DAML-on-X Example Ledger Implementation",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "com.typesafe.akka" %% "akka-actor" % "2.5.22",
      "com.typesafe.akka" %% "akka-testkit" % "2.5.22" % Test,
      "com.typesafe.akka" %% "akka-stream" % "2.5.22",
      "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.22" % Test,
      "com.typesafe.akka" %% "akka-slf4j" % "2.5.22",
      "org.slf4j" % "slf4j-api" % "1.7.26",
      "org.slf4j" % "slf4j-simple" % "1.7.26",
      "com.digitalasset.ledger" %% "ledger-api-common" % sdkVersion,
      "com.daml.ledger" %% "api-server-damlonx" % sdkVersion,
      "com.daml.ledger" %% "participant-state-index" % sdkVersion,
      "com.daml.ledger" %% "reference-participant-state-index" % sdkVersion,
      "com.daml.ledger" %% "participant-state" % sdkVersion,
      "com.daml.ledger" %% "participant-state-kvutils" % sdkVersion,
      "com.github.scopt" %% "scopt" % "4.0.0-RC2",
    ),
    resolvers += "Digital Asset SDK" at "https://digitalassetsdk.bintray.com/DigitalAssetSDK"
  )

