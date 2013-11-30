package javaee6.web.entity

import scala.beans.BeanProperty
import scala.collection.JavaConverters._

import java.util.Date

import javax.persistence.{Column, Entity, GeneratedValue, GenerationType, Id}
import javax.persistence.{Table, Temporal, TemporalType}
import javax.persistence.{FetchType, JoinColumn, OneToMany}

object BookShelf {
  def apply(name: String): BookShelf = {
    val bs = new BookShelf
    bs.name = name
    bs
  }
}

@SerialVersionUID(1L)
@Entity
@Table(name = "book_shelf")
class BookShelf {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @BeanProperty
  var id: Int = _

  @Column
  @BeanProperty
  var name: String = _

  @OneToMany
  //@OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "book_shelf_id")
  @BeanProperty
  var booksAsJava: java.util.List[Book] = _

  def books: Iterable[Book] =
    booksAsJava.asScala.asInstanceOf[Iterable[Book]]
}

object Book {
  def apply(bookShelfId: Int, name: String, publishDate: Date): Book = {
    val book = new Book
    book.bookShelfId = bookShelfId
    book.name = name
    book.publishDate = publishDate
    book
  }
}

@SerialVersionUID(1L)
@Entity
@Table(name = "book")
class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @BeanProperty
  var id: Int = _

  @Column(name = "book_shelf_id")
  @BeanProperty
  var bookShelfId: Int = _

  @Column
  @BeanProperty
  var name: String = _

  @Column(name = "publish_date")
  @Temporal(TemporalType.DATE)
  @BeanProperty
  var publishDate: Date = _
}
