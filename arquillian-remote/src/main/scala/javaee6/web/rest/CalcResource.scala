package javaee6.web.rest

import javax.enterprise.context.RequestScoped
import javax.ws.rs.{GET, Path, Produces, QueryParam}
import javax.ws.rs.core.MediaType

@RequestScoped
@Path("calc")
class CalcResource {
  @GET
  @Path("add")
  @Produces(Array(MediaType.TEXT_PLAIN))
  def add(@QueryParam("p1") p1: Int, @QueryParam("p2") p2: Int): String =
    (p1 + p2).toString
}
