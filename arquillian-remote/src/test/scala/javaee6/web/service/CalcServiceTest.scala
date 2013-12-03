package javaee6.web.service

import javax.ejb.EJB
import javax.inject.Inject

import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.junit.Arquillian
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.asset.EmptyAsset
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.jboss.shrinkwrap.resolver.api.maven.Maven

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert._
import org.hamcrest.CoreMatchers._

@RunWith(classOf[Arquillian])
class CalcServiceTest {
  // CDIを使わず、LocalBeanとする場合はJNDI名が必要らしい
  //@EJB(mappedName = "java:global/arquillian-test/CalcService")
  @Inject
  private var calcService: CalcService = _

  @Test
  def addTest: Unit =
    assertThat(calcService.add(1, 2), is(3))

  @Test
  def multiplyTest: Unit =
    assertThat(calcService.multiply(2, 3), is(6))
}

object CalcServiceTest {
  @Deployment
  def createDeployment: WebArchive =
    ShrinkWrap
      .create(classOf[WebArchive], "arquillian-test.war")
      .addPackages(true, "javaee6.web.service")
      .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
      .addAsLibraries {
        Maven
          .resolver
          .resolve("org.scala-lang:scala-library:2.10.3")
          .withTransitivity
          .asFile: _*
      }
}
