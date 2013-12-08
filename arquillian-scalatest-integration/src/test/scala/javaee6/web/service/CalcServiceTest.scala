package javaee6.web.service

import java.io.File
import javax.inject.Inject

import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.junit.Arquillian
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.asset.EmptyAsset
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.jboss.shrinkwrap.resolver.api.maven.Maven

import org.scalatest.Matchers._
import org.scalatest.junit.JUnitSuite

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(classOf[Arquillian])
class CalcServiceTest extends JUnitSuite {
  @Inject
  private var calcService: CalcService = _

  @Test
  def addTest: Unit = {
    calcService.add(1, 2) should be (3)
    calcService.add(5, 5) should be (10)
  }

  @Test
  def multiplyTest: Unit = {
    calcService.multiply(1, 2) should be (2)
    calcService.multiply(5, 5) should be (25)
  }
}

object CalcServiceTest {
  @Deployment
  def createDeployment: WebArchive = {
    val depLibs: (String*) => Seq[File] = artifacts =>
      artifacts.map { artifact =>
        Maven
          .resolver
          .resolve(artifact)
          .withTransitivity
          .asFile
      }.flatten

    ShrinkWrap
      .create(classOf[WebArchive], "arquillian-test.war")
      .addPackages(true, classOf[CalcService].getPackage)
      .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
      .addAsLibraries {
        depLibs("org.scala-lang:scala-library:2.10.3",
                "org.scalatest:scalatest_2.10:2.0"): _*
      }
  }
}
