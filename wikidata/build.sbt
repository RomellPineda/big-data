import Dependencies._

ThisBuild / scalaVersion     := "2.13.4"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "io.romellpineda"
ThisBuild / organizationName := "romellpineda"

lazy val root = (project in file("."))
  .settings(
    name := "wikidata",
    libraryDependencies += scalaTest % Test,
    // https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-common
    libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "2.7.7",
    // https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-mapreduce-client-core
    libraryDependencies += "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "2.7.7"
  )

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             => MergeStrategy.first
}
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
