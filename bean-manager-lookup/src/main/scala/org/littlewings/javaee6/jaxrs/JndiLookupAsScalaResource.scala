package org.littlewings.javaee6.jaxrs

import javax.enterprise.inject.spi.{Bean, BeanManager}
import javax.naming.InitialContext
import javax.ws.rs.{GET, Path, Produces, QueryParam}
import javax.ws.rs.core.MediaType

import org.littlewings.javaee6.service.{CalcService, DateService}

@Path("jndi-as-scala")
class JndiLookupResourceAsScala {
  @GET
  @Path("calc")
  @Produces(Array(MediaType.TEXT_PLAIN))
  def calc(@QueryParam("p1") p1: Int, @QueryParam("p2") p2: Int): String = {
    /*
    val initialContext = new InitialContext
    val bm: BeanManager =
      initialContext.lookup("java:comp/BeanManager").asInstanceOf[BeanManager]

    // このコードは、コンパイルできない  
    val beans: java.util.Set[Bean[_]] = bm.getBeans(classOf[CalcService])
    val bean: Bean[_] = bm.resolve(beans)
    val calcService =
      bm
        .getReference(bean, classOf[CalcService], bm.createCreationalContext(bean))
        .asInstanceOf[CalcService]

    calcService.add(p1, p2).toString
    */
    ""
  }
}
