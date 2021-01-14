package io.romellpineda.movieapp

import scala.io.BufferedSource
import scala.io.Source

object FileUtil {

  def getFileContext(path: String): Unit = {

    var readFile : BufferedSource = null

    try {
      readFile = Source.fromFile(path)
      readFile.getLines().map(line => store(line))
    } catch {
      case e : Exception => s"FileUtil getFileContext() encountered exception ${e}"
    } finally {
      if (readFile != null) readFile.close()
    }
  }

  def store(data: String): String = {
    // to do: set return type to Unit
    // to do: insert processed data to db
    return data.split(",")(2).trim()
  }
}