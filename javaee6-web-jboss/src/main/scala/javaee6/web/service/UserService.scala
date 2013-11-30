package javaee6.web.service

import scala.collection.JavaConverters._

import javax.ejb.{Local, LocalBean, Stateless}
import javax.persistence.{EntityManager, PersistenceContext}

import javaee6.web.entity.User

@Stateless
@LocalBean
class UserService {
  @PersistenceContext(unitName = "javaee6.web.pu")
  var entityManager: EntityManager = _

  def findAll: List[User] =
    entityManager
      .createQuery("SELECT u FROM User u")
      .getResultList
      .asScala
      .toList
      .asInstanceOf[List[User]]
}
