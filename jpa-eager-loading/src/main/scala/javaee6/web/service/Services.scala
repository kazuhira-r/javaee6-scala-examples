package javaee6.web.service

import javax.ejb.{LocalBean, Stateless}
import javax.persistence.{EntityManager, PersistenceContext}

import javaee6.web.entity.{BookShelf, Book}

trait PersistenceContextSupport[T] {
  @PersistenceContext(unitName = "javaee6.web.pu")
  var entityManager: EntityManager = _

  def create(entity: T): Unit =
    entityManager.persist(entity)

  def update(entity: T): Unit =
    entityManager.merge(entity)

  def remove(entity: T): Unit =
    entityManager.remove(entityManager.merge(entity))
}

@Stateless
@LocalBean
class BookShelfService extends PersistenceContextSupport[BookShelf] {
  def find(id: Int): BookShelf =
    entityManager.find(classOf[BookShelf], id)

  def findByFetchQuery(id: Int): BookShelf =
    entityManager
      .createQuery("""|SELECT bs
                      |  FROM BookShelf bs
                      |  LEFT JOIN FETCH bs.booksAsJava
                      | WHERE bs.id = :id""".stripMargin)
      .setParameter("id", id)
      .getSingleResult
      .asInstanceOf[BookShelf]

  def count: Int =
    entityManager
      .createQuery("SELECT bs FROM BookShelf bs")
      .getResultList
      .size
}

@Stateless
@LocalBean
class BookService extends PersistenceContextSupport[Book]
