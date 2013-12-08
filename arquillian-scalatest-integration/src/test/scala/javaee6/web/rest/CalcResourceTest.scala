package javaee6.web.rest

import java.io.File
import java.net.URL

import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.{MediaType, Response}

import org.jboss.arquillian.container.test.api.{Deployment, RunAsClient}
import org.jboss.arquillian.junit.Arquillian
import org.jboss.arquillian.test.api.ArquillianResource

import org.jboss.resteasy.client.ClientRequest

import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.asset.EmptyAsset
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.jboss.shrinkwrap.resolver.api.maven.Maven

import org.scalatest.Matchers._
import org.scalatest.junit.JUnitSuite

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(classOf[Arquillian])
@RunAsClient
class CalcResourceTest extends JUnitSuite {
  private val resourcePrefix: String =
    classOf[RestApplication]
      .getAnnotation(classOf[ApplicationPath])
      .value
      .substring(1)

  @ArquillianResource
  private var deploymentUrl: URL = _

  @Test
  def addTest: Unit = {
    val request =
      new ClientRequest(s"${deploymentUrl}${resourcePrefix}/calc/add?p1=1&p2=2")
    request.header("Accept", MediaType.TEXT_PLAIN)

    val response = request.get(classOf[String])

    response.getStatus should be (Response.Status.OK.getStatusCode)
    response.getEntity should be ("3")
  }

  @Test
  def multiplyTest: Unit = {
    val request =
      new ClientRequest(s"${deploymentUrl}${resourcePrefix}/calc/multiply?p1=1&p2=2")
    request.header("Accept", MediaType.TEXT_PLAIN)

    val response = request.get(classOf[String])

    response.getStatus should be (Response.Status.OK.getStatusCode)
    response.getEntity should be ("2")
  }
}

object CalcResourceTest {
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
      .addPackages(true, classOf[CalcResource].getPackage)
      .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
      .addAsLibraries {
        depLibs("org.scala-lang:scala-library:2.10.3",
                "org.scalatest:scalatest_2.10:2.0"): _*
      }
  }
}
