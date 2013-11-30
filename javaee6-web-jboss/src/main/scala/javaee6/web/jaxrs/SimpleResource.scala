package javaee6.web.jaxrs

import javax.inject.Inject
import javax.ws.rs.{GET, Produces, Path}
import javax.ws.rs.core.{MediaType, Response}

import javaee6.web.service.UserService

@Path("/simple")
class SimpleResource {
  @Inject
  var userService: UserService = _

  @GET
  @Path("user")
  @Produces(Array(MediaType.TEXT_HTML))
  def users: Response = {
    val responseHtml =
      <html>
        <head>
          <meta charset="UTF-8" />
          <title>ユーザ一覧</title>
        </head>
      <body>
        <h1>ユーザ一覧</h1>
        <table border="1">
        <tr><th>ID</th><th>名前</th><th>年齢</th></tr>
        {userService.findAll.map { user =>
          <tr>
            <td>{user.id}</td>
            <td>{user.lastName + " " + user.firstName}</td>
            <td>{user.age}</td>
          </tr>
        }}
        </table>
      </body>
      </html>

    Response.ok(responseHtml.toString).build
  }
}
