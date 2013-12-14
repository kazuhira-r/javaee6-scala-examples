package javaee6.web.entity

import scala.beans.BeanProperty

import java.util.Objects
import javax.persistence.{Column, Entity, Id, Table, Version}

object User {
  def apply(id: Int, firstName: String, lastName: String, age: Int): User = {
    val user = new User
    user.id = id
    user.firstName = firstName
    user.lastName = lastName
    user.age = age
    user
  }
}

@SerialVersionUID(1L)
@Entity
@Table(name = "user")
class User extends Serializable {
  @Id
  @BeanProperty
  var id: Int = _

  @Column(name = "first_name")
  @BeanProperty
  var firstName: String = _

  @Column(name = "last_name")
  @BeanProperty
  var lastName: String = _

  @Column(name = "age")
  @BeanProperty
  var age: Int = _

  @Column(name = "version_no")
  @BeanProperty
  var versionNo: Int = _

  override def toString(): String =
    s"id = $id, firstName = $firstName, lastName = $lastName, age = $age, versionNo = $versionNo"

  override def equals(other: Any): Boolean = other match {
    case ou: User => id == ou.id && firstName == ou.firstName && lastName == ou.lastName && age == ou.age
    case _ => false
  }

  override def hashCode: Int =
    Objects.hashCode(id, firstName, lastName, age)
}
