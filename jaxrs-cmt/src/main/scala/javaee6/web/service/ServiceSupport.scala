package javaee6.web.service

import scala.annotation.tailrec
import scala.collection.JavaConverters._

import java.lang.reflect.ParameterizedType

import javax.persistence.{EntityManager, PersistenceContext}

trait PersistenceUnitSupport {
  var entityManager: EntityManager
}

trait StandardPersistenceUnitSupport extends PersistenceUnitSupport {
  @PersistenceContext(unitName = "javaee6.web.pu")
  var entityManager: EntityManager = _
}

abstract class ServiceSupport[T] {
  self: PersistenceUnitSupport =>

  val entityClass: Class[T] = findClass(getClass)

  @tailrec
  private def findClass[A](clazz: Class[_]): Class[A] =
    if (clazz.getSuperclass == classOf[ServiceSupport[_]])
      clazz
        .getGenericSuperclass
        .asInstanceOf[ParameterizedType]
        .getActualTypeArguments()
        .apply(0)
        .asInstanceOf[Class[A]]
    else
      findClass(clazz.getSuperclass)

  def create(entity: T): Unit =
    self.entityManager.persist(entity)

  def edit(entity: T): Unit =
    self.entityManager.merge(entity)

  def remove(entity: T): Unit =
    self.entityManager.remove(entityManager.merge(entity))

  def find(id: Any): T =
    self.entityManager.find(entityClass, id)

  def findAll: Iterable[T] =
    self.entityManager
      .createQuery(s"SELECT e FROM ${entityClass.getSimpleName} e")
      .getResultList
      .asScala
      .asInstanceOf[Iterable[T]]
}
