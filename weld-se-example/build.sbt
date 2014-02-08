name := "weld-se-example"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.3"

organization := "org.littlewings"

scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked")

libraryDependencies ++= Seq(
  "org.jboss.weld.se" % "weld-se" % "2.1.2.Final",
  "org.scalatest" %% "scalatest" % "2.0" % "test"
)
