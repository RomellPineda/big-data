package io.romellpineda.movieapp

import scala.io.BufferedSource
import scala.io.Source

object FileUtil {

  def getFileContext(path: String): String = {

    var readFile : BufferedSource = null

    try {
      readFile = Source.fromFile(path)
      readFile.getLines().mkString
    } catch {
      case e : Exception => s"FileUtil getFileContext() encountered exception ${e}"
    } finally {
      if (readFile != null) readFile.close()
    }
  }
}