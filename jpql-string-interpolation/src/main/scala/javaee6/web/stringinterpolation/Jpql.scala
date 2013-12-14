package javaee6.web.stringinterpolation

import javax.persistence.{EntityManager, Query}

object Jpql {
  def bind(queryAsString: String)(params: (Int, Any)*): Jpql =
    new Jpql(queryAsString, params: _*)
}

class Jpql(queryAsString: String, params: (Int, Any)*) {
  def asQuery[T](resultClass: Class[T])(implicit entityManager: EntityManager): Query =
    params.foldLeft(entityManager.createQuery(queryAsString, resultClass)) {
      case (query, (index, value)) => query.setParameter(index, value)
    }

  def stripMargin: Jpql =
    Jpql.bind(queryAsString.stripMargin)(params: _*)

  def stripMargin(marginChar: Char): Jpql =
    Jpql.bind(queryAsString.stripMargin(marginChar))(params: _*)
}
