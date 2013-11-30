package javaee6.web.jaxrs

import java.net.URI

import javax.inject.Inject
import javax.ws.rs.{Consumes, Encoded, GET, Path, POST, Produces}
import javax.ws.rs.core.{Context,  MediaType, MultivaluedMap, Response, UriBuilder, UriInfo}

import javaee6.web.entity.{Opportunity, Quote}
import javaee6.web.service.{OpportunityService, QuoteService, TransactionService}

@Path("/trans")
class TransactionSubmitResource {
  @Inject
  var transactionService: TransactionService = _

  @Inject
  var oppService: OpportunityService = _

  @Inject
  var qteService: QuoteService = _

  @GET
  @Path("index")
  @Produces(Array(MediaType.TEXT_HTML))
  def index(@Context uriInfo: UriInfo): Response = {
    val responseHtml =
      <html>
        <head>
          <meta charset="UTF-8" />
          <title>Jaxrs &amp; CMT</title>
        </head>
        <body>
          <h1>商談一覧</h1>
          <a href={uriInfo.getBaseUriBuilder.path("/trans/add").build().toASCIIString}>新規登録へ</a><br />
          <table border="1">
          <tr><th>商談ID</th><th>商談名</th><th>見積ID</th><th>見積名</th></tr>
          {oppService.findAllOrderById.map { o =>
            <tr>
              <td>{o.opportunityId}</td>
              <td>{o.opportunityName}</td>
              <td>{o.quote.quoteId}</td>
              <td>{o.quote.quoteName}</td>
            </tr>
          }}
          </table>
        </body>
      </html>

    Response.ok(responseHtml.toString).build
  }

  @GET
  @Path("add")
  @Produces(Array(MediaType.TEXT_HTML))
  def add(@Context uriInfo: UriInfo): Response = {
    val responseHtml =
      <html>
        <head>
          <meta charset="UTF-8" />
          <title>Jaxrs &amp; CMT</title>
        </head>
        <body>
          <form action={uriInfo.getBaseUriBuilder.path("/trans/submit").build().toASCIIString} method="post">
            商談ID： <input name="oppId" type="text" /><br />
            商談名： <input name="oppName" type="text" /><br />
            見積ID： <input name="qteId" type="text" /><br />
            見積名： <input name="qteName" type="text" /><br />
            <input type="submit" value="保存" />
          </form>
        </body>
      </html>

    Response.ok(responseHtml.toString).build
  }

  @POST
  @Path("submit")
  @Consumes(Array(MediaType.APPLICATION_FORM_URLENCODED))
  def submit(form: MultivaluedMap[String, String]): Response = {
    val opp = Opportunity(form.get("oppId").get(0),
                          form.get("oppName").get(0))
    val qte= Quote(form.get("qteId").get(0),
                   opp.opportunityId,
                   form.get("qteName").get(0))

    transactionService.saveOpportunityAndQuote(opp, qte)
    // transactionService.unsafeOpportunityAndQuote(opp, qte)

    Response
      .status(Response.Status.MOVED_PERMANENTLY)
      .location(new URI("/trans/index"))
      .build
  }
}

