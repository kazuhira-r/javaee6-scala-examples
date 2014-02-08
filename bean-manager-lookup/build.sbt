name := "bean-manager-lookup"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.3"

organization := "org.littlewings"

scalacOptions ++= Seq("-Xlint", "-deprecation")

seq(webSettings: _*)

artifactName := { (version: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  //artifact.name + "." + artifact.extension
  "javaee6-web." + artifact.extension
}

libraryDependencies ++= Seq(
  "org.eclipse.jetty" % "jetty-webapp" % "9.1.0.v20131115" % "container",
  "org.eclipse.jetty" % "jetty-plus"   % "9.1.0.v20131115" % "container",
  "javax" % "javaee-web-api" % "6.0" % "provided"
)
