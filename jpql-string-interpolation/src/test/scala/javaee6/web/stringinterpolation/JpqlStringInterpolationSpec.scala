package javaee6.web.stringinterpolation

import scala.collection.JavaConverters._

import javax.persistence.{EntityManager, EntityManagerFactory, Persistence}

import javaee6.web.entity.User
import javaee6.web.stringinterpolation.JpqlStringInterpolation._

import org.scalatest.{BeforeAndAfterAll, FunSpec}
import org.scalatest.Matchers._

class JpqlStringInterpolationSpec extends FunSpec with BeforeAndAfterAll {
  override def beforeAll(): Unit = {
    val emf = Persistence.createEntityManagerFactory("javaee6.web.pu")
    val em = emf.createEntityManager
    val tx = em.getTransaction

    tx.begin()
    em.createNativeQuery("TRUNCATE TABLE user").executeUpdate()
    em.flush()
    tx.commit()

    tx.begin()
    Array(User(1, "カツオ", "磯野", 11),
          User(2, "ワカメ", "磯野", 9),
          User(3, "タラオ", "フグ田", 3)).foreach(em.persist)
    tx.commit()
  }

  describe("Standard Jpql") {
    it("find One id equals & literal") {
      val emf = Persistence.createEntityManagerFactory("javaee6.web.pu")
      implicit val em = emf.createEntityManager

      em.createQuery("SELECT u FROM User u WHERE id = 1", classOf[User])
        .getSingleResult should be (User(1, "カツオ", "磯野", 11))
    }
  }

  describe("Jpql String Interpolation Spec") {
    describe("select") {
      it("find One id equals & numeric literal") {
        val emf = Persistence.createEntityManagerFactory("javaee6.web.pu")
        implicit val em = emf.createEntityManager

        jpql"SELECT u FROM User u WHERE id = ${1}"
          .asQuery(classOf[User])
          .getSingleResult should be (User(1, "カツオ", "磯野", 11))
      }

      it("find One id equals And age equals") {
        val emf = Persistence.createEntityManagerFactory("javaee6.web.pu")
        implicit val em = emf.createEntityManager

        val id = 2
        val age = 9

        jpql"SELECT u FROM User u WHERE id = $id AND age = $age"
          .asQuery(classOf[User])
          .getSingleResult should be (User(2, "ワカメ", "磯野", 9))
      }


      it("find One id,age & raw string literal, stripMargin") {
        val emf = Persistence.createEntityManagerFactory("javaee6.web.pu")
        implicit val em = emf.createEntityManager

        val id = 2
        val age = 9

        jpql"""|SELECT u
               | FROM User u
               | WHERE id = $id
               | AND age = $age"""
          .stripMargin
          .asQuery(classOf[User])
          .getSingleResult should be (User(2, "ワカメ", "磯野", 9))
      }

      it("find Collection age > 5") {
        val emf = Persistence.createEntityManagerFactory("javaee6.web.pu")
        implicit val em = emf.createEntityManager

        val age = 5

        jpql"SELECT u FROM User u WHERE age > $age"
          .asQuery(classOf[User])
          .getResultList should contain theSameElementsAs (Array(User(1, "カツオ", "磯野", 11),
                                                                 User(2, "ワカメ", "磯野", 9)))
      }
    }
  }
}
