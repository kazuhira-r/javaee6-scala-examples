name := "javaee6-web"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.3"

organization := "littlewings"

seq(webSettings: _*)

artifactName := { (version: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  //artifact.name + "." + artifact.extension
  "javaee6-web." + artifact.extension
}

libraryDependencies ++= Seq(
  "org.eclipse.jetty" % "jetty-webapp" % "9.0.6.v20130930" % "container",
  "javax" % "javaee-web-api" % "6.0" % "provided",
  "mysql" % "mysql-connector-java" % "5.1.26" % "provided"
)
