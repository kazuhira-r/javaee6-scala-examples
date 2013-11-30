package javaee6.web.jaxrs

import javax.ws.rs.{GET, Path, Produces}
import javax.ws.rs.core.MediaType

@Path("simple")
class SimpleResource {
  @GET
  @Produces(Array(MediaType.TEXT_PLAIN))
  def hello: String =
    "Hello World"
}

