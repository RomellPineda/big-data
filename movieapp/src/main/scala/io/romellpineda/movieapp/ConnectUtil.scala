package io.romellpineda.movieapp

import java.sql.DriverManager
import java.sql.Driver
import java.sql.Connection
import scala.util.Using
import scala.collection.mutable.ArrayBuffer

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

    // Using.Manager { use =>
    //   connection = use(DriverManager.getConnection(connectionUrl))
    //   // send to processing function
    //   val dbQuery = use(connection.prepareStatement("SELECT * FROM game"))
    //   dbQuery.execute()

    //   // target for clean up
    //   val result = use(dbQuery.getResultSet())
    //   while (result.next()) {
    //     println(result.getString("title"))
    //   }
    // }
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

  // refactor to callDB with 2 params statement str and title
  def getMovies(context : String) : ArrayBuffer[String] = {

    var resultArray = new ArrayBuffer[String]()
    classOf[org.postgresql.Driver].newInstance()

    Using.Manager { use =>
      connection = use(DriverManager.getConnection(connectionUrl))
      val dbQuery = use(connection.prepareStatement(context))
      dbQuery.execute()

      val result = use(dbQuery.getResultSet())
      while (result.next()) {
        resultArray.+=(result.getString("title"))
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
        resultArray.+=(result.getString("title") + ", " + result.getString("rating"))
      }
    }

    resultArray
  }
}