name := "jpql-string-interpolation"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.3"

organization := "littlewings"

fork in run := true

libraryDependencies ++= Seq(
  "org.hibernate" % "hibernate-entitymanager" % "4.2.8.Final",
  "mysql" % "mysql-connector-java" % "5.1.26" % "runtime",
  "org.scalatest" %% "scalatest" % "2.0" % "test"
)
