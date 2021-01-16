package io.romellpineda.movieapp

import java.sql.DriverManager
import java.sql.Driver
import java.sql.Connection
import scala.util.Using

object ConnectUtil {

  val connectionUrl = "jdbc:postgresql://localhost:5432/roml"
  var connection : Connection = null

  def getConnection(): Unit = {

    classOf[org.postgresql.Driver].newInstance()

    Using.Manager { use =>
      connection = use(DriverManager.getConnection(connectionUrl))
      val dbQuery = use(connection.prepareStatement("SELECT * FROM player"))
      dbQuery.execute()

      // target for clean up
      val result = use(dbQuery.getResultSet())
      while (result.next()) {
        println(result.getString("alias"))
      }
    }
  }
}