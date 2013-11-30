package javaee6.web.service

import java.io.File
import javax.inject.Inject

import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.junit.Arquillian
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.jboss.shrinkwrap.resolver.api.maven.Maven

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert._
import org.hamcrest.CoreMatchers._

import javaee6.web.entity.User

@RunWith(classOf[Arquillian])
class UserServiceTest {
  @Inject
  var userService: UserService = _

  @Test
  def findUserTest: Unit = {
    val katsuo = userService.find(1)

    assertThat(katsuo.firstName, is("カツオ"))
    assertThat(katsuo.lastName, is("磯野"))
    assertThat(katsuo.age, is(11))
  }
}


object UserServiceTest {
  @Deployment
  def createDeployment: WebArchive =
    ShrinkWrap
      .create(classOf[WebArchive], "javaee6-web.war")
      //.addClasses(classOf[UserService], classOf[User]) // Class単位で指定
      .addPackages(true, "javaee6.web")
      .addAsResource("META-INF/persistence.xml")
      .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
      .addAsLibraries {
        Maven
          .resolver
          .resolve("org.scala-lang:scala-library:2.10.3")
          .withTransitivity
          .asFile: _*
      }
      //.addAsLibrary(toJarPathOfClass(classOf[ScalaObject]))  // ローカルファイルシステムから指定

  private def toJarPathOfClass(clazz: Class[_]): File = {
    val resource = clazz.getName.split('.').mkString("/", "/", ".class")
    val path = getClass.getResource(resource).getPath
    val indexOfFileScheme = path.indexOf("file:") + 5
    val indexOfSeparator = path.lastIndexOf('!')
    new File(path.substring(indexOfFileScheme, indexOfSeparator))
  }
}

