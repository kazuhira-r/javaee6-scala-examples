package javaee6.web.service

import scala.collection.JavaConverters._

import javax.ejb.{EJB, LocalBean, Stateless}

import javaee6.web.entity.{Opportunity, Quote}

@Stateless
@LocalBean
class TransactionService {
  @EJB
  var oppService: OpportunityService = _

  @EJB
  var qteService: QuoteService = _

  def saveOpportunityAndQuote(opp: Opportunity, qte: Quote): Unit = {
    qteService.create(qte)
    oppService.create(opp)
  }

  // saveQuoteメソッドの実装次第で、ロールバックするかどうかが決まる
  def unsafeOpportunityAndQuote(opp: Opportunity, qte: Quote): Unit = {
    try {
      saveQuote(qte)
    } catch {
      case e:Exception =>
        println(s"Exception Cause[$e]")
        e.printStackTrace
    }

    saveOpportunity(opp)
  }

  def saveOpportunity(opp: Opportunity): Unit =
    oppService.create(opp)

  def saveQuote(qte: Quote): Unit = {
    qteService.unsafeCreate(qte)
    // qteService.create(qte)
    // throw new IllegalStateException("Opps!")
  }
}

@Stateless
@LocalBean
class OpportunityService extends ServiceSupport[Opportunity]
                         with StandardPersistenceUnitSupport {
  def findAllOrderById: Iterable[Opportunity] =
   entityManager
     .createQuery("SELECT o From Opportunity o ORDER BY o.opportunityId")
     .getResultList
     .asScala
     .asInstanceOf[Iterable[Opportunity]]
}

@Stateless
@LocalBean
class QuoteService extends ServiceSupport[Quote]
                   with StandardPersistenceUnitSupport {
  def unsafeCreate(qte: Quote): Unit = {
    create(qte)
    throw new IllegalStateException("Opps!")
  }
}
