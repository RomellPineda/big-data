package io.romellpineda.movieapp

import java.sql.DriverManager
import java.sql.Driver
import java.sql.Connection
import scala.collection.mutable.ArrayBuffer
import scala.util.Using
import scala.util.Try

object ConnectUtil {

  val connectionUrl = "jdbc:postgresql://localhost:5432/roml"
  var connection : Connection = null
  
  def callDb(queryString : String) : Unit = {
    classOf[org.postgresql.Driver].newInstance()
    var resultArray = new ArrayBuffer[String]()

    Using.Manager { use =>
      connection = use(DriverManager.getConnection(connectionUrl))
      val dbQuery = use(connection.prepareStatement(queryString))
      dbQuery.execute()
      
    }
  }

  def insertMovie(title : String, rating : String) : Unit = {
    classOf[org.postgresql.Driver].newInstance()

    Using.Manager { use =>
      connection = use(DriverManager.getConnection(connectionUrl))
      val dbQuery = use(connection.prepareStatement("INSERT INTO movie (title, rating) VALUES (?, ?);"))
      dbQuery.setString(1, title)
      dbQuery.setString(2, rating)
      dbQuery.execute()
    }
  }

  def getMovies(context : String) : ArrayBuffer[String] = {
    var resultArray = new ArrayBuffer[String]()
    classOf[org.postgresql.Driver].newInstance()

    Using.Manager { use =>
      connection = use(DriverManager.getConnection(connectionUrl))
      val dbQuery = use(connection.prepareStatement(context))
      dbQuery.execute()

      val result = use(dbQuery.getResultSet())
      while (result.next()) {
        resultArray.+=(result.getInt("movie_id") + " | " + result.getString("title"))
      }
    }
    resultArray
  }

  def getTop100(context : String) : ArrayBuffer[String] = {
    var resultArray = new ArrayBuffer[String]()
    classOf[org.postgresql.Driver].newInstance()

    Using.Manager { use =>
      connection = use(DriverManager.getConnection(connectionUrl))
      val dbQuery = use(connection.prepareStatement(context))
      dbQuery.execute()

      val result = use(dbQuery.getResultSet())
      while (result.next()) {
        resultArray.+=(result.getInt("movie_id") + " | " + result.getString("title") + ", " + result.getString("rating"))
      }
    }

    resultArray
  }

  def login(login_name : String, password : String) : Int = {
    var id : Int = 0
    classOf[org.postgresql.Driver].newInstance()

    Using.Manager { use =>
      connection = use(DriverManager.getConnection(connectionUrl))
      val dbQuery = use(connection.prepareStatement("SELECT * FROM customer WHERE login_name = ? AND password = ?;"))
      dbQuery.setString(1, login_name)
      dbQuery.setString(2, password)
      dbQuery.execute()
      
      val result = use(dbQuery.getResultSet())
      id = result.getInt("customer_id")
    }
    id
  }

  def pay(customer_id : Int, balance : Int) : String = {
    var message = "payment unsuccessful"
    var queryString : String = "UPDATE customer SET balance = ? WHERE customer_id = ?;"
    classOf[org.postgresql.Driver].newInstance()

    Using.Manager { use =>
      connection = use(DriverManager.getConnection(connectionUrl))
      if (balance > 0) {
        queryString = "UPDATE customer SET balance = balance + ? WHERE customer_id = ?;"
      }
      val dbQuery = use(connection.prepareStatement(queryString))
      dbQuery.setInt(1, balance)
      dbQuery.setInt(2, customer_id)
      dbQuery.execute()
      
      if (dbQuery.getUpdateCount() > 0) {
        message ="payment successful"
      }
    }
    message
  }

  def rent(customer_id : Int, movie_id : Int) : String = {
    var message = "unsuccessful"
    classOf[org.postgresql.Driver].newInstance()

    Using.Manager { use =>
      connection = use(DriverManager.getConnection(connectionUrl))
      val dbQuery = use(connection.prepareStatement("INSERT INTO invoice (customer_id, movie_id) VALUES (?, ?);"))
      dbQuery.setInt(1, customer_id)
      dbQuery.setInt(2, movie_id)
      dbQuery.execute()

      if (dbQuery.getUpdateCount() > 0) {
        message ="successfully added to cart"
      }
      pay(customer_id, 1)
    }
    message
  }

  def searchTitle(searchString : String) : ArrayBuffer[String] = {
    var resultArray = new ArrayBuffer[String]()
    classOf[org.postgresql.Driver].newInstance()

    Using.Manager { use =>
      connection = use(DriverManager.getConnection(connectionUrl))
      val dbQuery = use(connection.prepareStatement("SELECT title FROM movie WHERE title LIKE CONCAT( '%',?,'%');"))

      // necessary structure '%Foo%'
      dbQuery.setString(1, searchString)
      dbQuery.execute()

      // this part works
      val result = use(dbQuery.getResultSet())
      while (result.next()) {
        resultArray.+=(result.getString("title"))
      }
    }
    resultArray
  }


  def subscribe(login_name : String, password : String) : String = {
    var message = "unsuccessful, try using a different login name"
    classOf[org.postgresql.Driver].newInstance()

    Using.Manager { use =>
      connection = use(DriverManager.getConnection(connectionUrl))
      val dbQuery = use(connection.prepareStatement("INSERT INTO customer (login_name, password) VALUES (?, ?);"))
      dbQuery.setString(1, login_name)
      dbQuery.setString(2, password)
      dbQuery.execute()
      
      if (dbQuery.getUpdateCount() > 0) {
        message ="successful"
      }
    }
    message
  }

  def unsub(login_name : String, password : String) : String = {
    var message = "login name and or password do not match"
    classOf[org.postgresql.Driver].newInstance()

    Using.Manager { use =>
      connection = use(DriverManager.getConnection(connectionUrl))
      val dbQuery = use(connection.prepareStatement("DELETE FROM customer WHERE login_name = ? AND password = ?;"))
      dbQuery.setString(1, login_name)
      dbQuery.setString(2, password)
      dbQuery.execute()
      
      if (dbQuery.getUpdateCount() > 0) {
        message ="you have successfully unsubscribed"
      }
    }
    message
  }
}