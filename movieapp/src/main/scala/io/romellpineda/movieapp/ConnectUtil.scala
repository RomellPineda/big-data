package io.romellpineda.movieapp

import java.sql.DriverManager
import java.sql.Driver
import java.sql.Connection
import scala.util.Using
import scala.collection.mutable.ArrayBuffer

object ConnectUtil {

  val connectionUrl = "jdbc:postgresql://localhost:5432/roml"
  var connection : Connection = null
  
  def callDb(context : String) : Unit = {

    classOf[org.postgresql.Driver].newInstance()
    var resultArray = new ArrayBuffer[String]()

    Using.Manager { use =>
      connection = use(DriverManager.getConnection(connectionUrl))
      // send to processing function
      val dbQuery = use(connection.prepareStatement(context))
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

  def insertMovie(title : String, rating : Int) : Unit = {

    classOf[org.postgresql.Driver].newInstance()
    // var resultArray = new ArrayBuffer[String]()

    Using.Manager { use =>
      connection = use(DriverManager.getConnection(connectionUrl))
      // send to processing function
      val dbQuery = use(connection.prepareStatement("INSERT INTO movie (title, rating) VALUES (?, ?);"))
      dbQuery.setString(1, title)
      dbQuery.setInt(2, rating)
      dbQuery.execute()
      
    }
  }
}