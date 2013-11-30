package javaee6.web.jaxrs

import java.net.URL

import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.{MediaType, Response}

import org.jboss.arquillian.container.test.api.{Deployment, RunAsClient}
import org.jboss.arquillian.junit.Arquillian
import org.jboss.arquillian.test.api.ArquillianResource
import org.jboss.resteasy.client.{ClientRequest, ClientResponse}
import org.jboss.resteasy.plugins.providers.RegisterBuiltin
import org.jboss.resteasy.spi.ResteasyProviderFactory
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.jboss.shrinkwrap.resolver.api.maven.Maven

import org.junit.{BeforeClass, Test}
import org.junit.runner.RunWith

import org.junit.Assert._
import org.hamcrest.CoreMatchers._

@RunWith(classOf[Arquillian])
@RunAsClient
class SimpleResourceTest {
  private val resourcePrefix =
    classOf[SimpleApplication]
      .getAnnotation(classOf[ApplicationPath])
      .value
      .substring(1)

  @ArquillianResource
  private var deploymentUrl: URL = _

  @Test
  def helloTest: Unit = {
    val request = new ClientRequest(deploymentUrl + resourcePrefix + "/simple")
    request.header("Accept", MediaType.TEXT_PLAIN)
    val response = request.get(classOf[String])
    assertThat(response.getStatus, is(Response.Status.OK.getStatusCode))
    assertThat(response.getEntity, is("Hello World"))
  }
}

object SimpleResourceTest {
  @Deployment
  def createDeployment: WebArchive =
    ShrinkWrap
      .create(classOf[WebArchive], "arquillian-test.war")
      .addPackages(true, "javaee6.web.jaxrs")
      .addAsLibraries {
        Maven
          .resolver
          .resolve("org.scala-lang:scala-library:2.10.3")
          .withTransitivity
          .asFile: _*
      }
}
