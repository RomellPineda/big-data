package io.romellpineda.movieapp

import java.sql.DriverManager
import java.sql.Driver

object ConnectUtil {

  val connectionUrl = "jdbc:postgresql://localhost:5432/roml"

  def getConnection(): Unit = {

    classOf[org.postgresql.Driver].newInstance()
    val conn = DriverManager.getConnection(connectionUrl)
    val statement = conn.prepareStatement("SELECT * FROM player")
    statement.execute()
    val res = statement.getResultSet()
    while (res.next()) {
      println(res.getString("alias"))
    }
    conn.close()
  }
}