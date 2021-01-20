package io.romellpineda.movieapp

import scala.io.BufferedSource
import scala.io.Source

object FileUtil {

  def getFileContext(path: String): Unit = {

    var readFile : BufferedSource = null

    try {
      readFile = Source.fromFile(path)
      readFile.getLines().foreach(line => processLine(line))
    } catch {
      case e : Exception => s"FileUtil getFileContext() encountered exception ${e}"
    } finally {
      if (readFile != null) readFile.close()
    }
  }

  def processLine(data: String): Unit = {
    var title = data.split(",")(0).trim()
    var rating = data.split(",")(2).trim()
    ConnectUtil.insertMovie(title, rating)
  }
}