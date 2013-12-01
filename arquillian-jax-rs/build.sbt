name := "arquillian-jax-rs"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.3"

organization := "littlewings"

resolvers += "Public JBoss Group" at "http://repository.jboss.org/nexus/content/groups/public-jboss"

fork in Test := true

envVars in Test += ("JBOSS_HOME", "/path/to/jboss-as-7.1.1.Final")

libraryDependencies ++= Seq(
  "org.jboss.spec" % "jboss-javaee-web-6.0" % "3.0.2.Final" % "provided",
  "org.jboss.as" % "jboss-as-arquillian-container-managed" % "7.1.1.Final" % "test" excludeAll(
    ExclusionRule(organization = "org.jboss.shrinkwrap"),
    ExclusionRule(organization = "org.jboss.shrinkwrap.descriptors")
  ),
  "org.jboss.arquillian.junit" % "arquillian-junit-container" % "1.1.1.Final" % "test" excludeAll(
    ExclusionRule(organization = "org.jboss.shrinkwrap"),
    ExclusionRule(organization = "org.jboss.shrinkwrap.descriptors")
  ),
  "org.jboss.shrinkwrap" % "shrinkwrap-api" % "1.0.0-cr-1" % "test",
  "org.jboss.shrinkwrap" % "shrinkwrap-impl-base" % "1.1.2" % "test",
  "org.jboss.shrinkwrap.descriptors" % "shrinkwrap-descriptors-spi" % "2.0.0-alpha-3" % "test",
  "org.jboss.shrinkwrap.resolver" % "shrinkwrap-resolver-depchain" % "2.0.1" % "test" excludeAll(
    ExclusionRule(organization = "org.jboss.shrinkwrap")
  ),
  "org.jboss.resteasy" % "resteasy-jaxrs" % "2.3.2.Final" % "test",
  "junit" % "junit" % "4.11" % "test",
  "com.novocode" % "junit-interface" % "0.10" % "test"
)
