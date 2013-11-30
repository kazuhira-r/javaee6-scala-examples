package javaee6.web.service

import scala.collection.JavaConverters._

import javax.ejb.{Local, LocalBean, Stateless}
import javax.persistence.{EntityManager, PersistenceContext}

import javaee6.web.entity.User

@Stateless
@LocalBean
class UserService {
  @PersistenceContext
  var entityManager: EntityManager = _

  def find(id: Int): User =
    entityManager
      .find(classOf[User], id)

  def findAll: Iterable[User] =
    entityManager
      .createQuery("SELECT u FROM User u")
      .getResultList
      .asScala
      .asInstanceOf[Iterable[User]]
}
