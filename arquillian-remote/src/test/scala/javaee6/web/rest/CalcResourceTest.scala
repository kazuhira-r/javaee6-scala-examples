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

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert._
import org.hamcrest.CoreMatchers._

@RunWith(classOf[Arquillian])
@RunAsClient
class CalcResourceTest {
  private val resourcePrefix =
    classOf[JaxrsApplication]
      .getAnnotation(classOf[ApplicationPath])
      .value
      .substring(1)

  @ArquillianResource
  private var deploymentUrl: URL = _

  @Test
  def calcTest: Unit = {
    val request
      = new ClientRequest(s"${deploymentUrl}${resourcePrefix}/calc/add?p1=3&p2=4")
    request.header("Accept", MediaType.TEXT_PLAIN)
    val response = request.get(classOf[String])
    assertThat(response.getStatus, is(Response.Status.OK.getStatusCode))
    assertThat(response.getEntity, is("7"))
  }
}

object CalcResourceTest {
  @Deployment
  def createDeployment: WebArchive =
    ShrinkWrap
      .create(classOf[WebArchive], "arquillian-test.war")
      .addPackages(true, "javaee6.web.rest")
      .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
      .addAsLibraries {
        Maven
          .resolver
          .resolve("org.scala-lang:scala-library:2.10.3")
          .withTransitivity
          .asFile: _*
      }
}
