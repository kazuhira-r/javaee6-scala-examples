package javaee6.web.entity

import scala.beans.BeanProperty

import javax.persistence.{Column, Entity, Id, JoinColumn, OneToOne, Table}

object Opportunity {
  def apply(opportunityId: String, opportunityName: String): Opportunity = {
    val opp = new Opportunity
    opp.opportunityId = opportunityId
    opp.opportunityName = opportunityName
    opp
  }
}

@SerialVersionUID(1L)
@Entity
@Table(name = "opportunity")
class Opportunity extends Serializable {
  @Id
  @Column(name = "opportunity_id")
  @BeanProperty
  var opportunityId: String = _

  @Column(name = "opportunity_name")
  @BeanProperty
  var opportunityName: String = _

  @OneToOne
  @JoinColumn(name = "opportunity_id")
  var quote: Quote = _
}

object Quote {
  def apply(quoteId: String, opportunityId: String, quoteName: String): Quote = {
    val qte = new Quote
    qte.quoteId = quoteId
    qte.opportunityId = opportunityId
    qte.quoteName = quoteName
    qte
  }
}

@SerialVersionUID(1L)
@Entity
@Table(name = "quote")
class Quote extends Serializable {
  @Id
  @Column(name = "quote_id")
  @BeanProperty
  var quoteId: String = _

  @Column(name = "opportunity_id")
  @BeanProperty
  var opportunityId: String = _

  @Column(name = "quote_name")
  @BeanProperty
  var quoteName: String = _
}
