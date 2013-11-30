package javaee6.web.jaxrs

import java.net.URI
import java.text.SimpleDateFormat
import java.util.Date

import javax.inject.Inject
import javax.ws.rs.{GET, Path, PathParam, Produces}
import javax.ws.rs.core.{CacheControl, MediaType, Response, UriBuilder}

import javaee6.web.entity.{Book, BookShelf}
import javaee6.web.service.{BookService, BookShelfService}

@Path("/bookshelf")
class BookShelfResource {
  @Inject
  var bookShelfService: BookShelfService = _

  @Inject
  var bookService: BookService = _

  @GET
  @Path("init")
  def init: Response = {
    val numbering = bookShelfService.count + 1
    val bookShelf = BookShelf("My本棚" + numbering)

    bookShelfService.create(bookShelf)

    val sdf = new SimpleDateFormat("yyyy/MM/dd")
    val date: String => Date = sdf.parse

    Array(
      Book(bookShelf.id,
           "Beginning Java EE 6 GlassFish 3で始めるエンタープライズJava",
           date("2013/3/9")),
      Book(bookShelf.id,
           "マスタリングJavaEE5 第2版",
           date("2009/11/28")),
      Book(bookShelf.id,
           "JavaによるRESTfulシステム構築",
           date("2010/8/23"))
    ).foreach(bookService.create)

    val cc = new CacheControl
    cc.setMaxAge(0)
    cc.setMustRevalidate(true)
    cc.setNoCache(true)
    cc.setNoStore(true)

    Response
      .status(Response.Status.MOVED_PERMANENTLY)
      .location(UriBuilder
                  .fromPath("/bookshelf/list/{id}")
                  .build(bookShelf.id.toString))
      .cacheControl(cc)
      .build
  }

  @GET
  @Path("list/{id}")
  def list(@PathParam("id") id: String): Response = {
    //val bookShelf = bookShelfService.find(id.toInt)
    val bookShelf = bookShelfService.findByFetchQuery(id.toInt)

    val sdf = new SimpleDateFormat("yyyy/MM/dd")
    val dformat: Date => String = sdf.format

    val responseHtml =
      <html>
        <head>
          <meta charset="UTF-8" />
          <title>{if (bookShelf != null) bookShelf.name
                  else "お探しの本棚はありません"}</title>
        </head>
        <body>
          <h1>{if (bookShelf != null) bookShelf.name
               else "お探しの本棚はありません"}</h1>
          {if (bookShelf!= null)
             <table border="1">
               <tr><th>書籍ID</th><th>書籍名</th><th>出版日</th></tr>
               {bookShelf.books.map { b =>
                 <tr><td>{b.id}</td><td>{b.name}</td><td>{dformat(b.publishDate)}</td></tr>}}
             </table>
           }
        </body>
      </html>

    Response
      .ok(responseHtml.toString)
      .build
  }
}
