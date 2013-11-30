package javaee6.web.jaxrs

import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@ApplicationPath("/rest")
class SimpleApplication extends Application {
  override def getClasses: java.util.Set[Class[_]] =
    null
}
