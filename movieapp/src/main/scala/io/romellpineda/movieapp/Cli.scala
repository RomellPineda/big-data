package io.romellpineda.movieapp

import scala.io.StdIn
import scala.collection.mutable.ArrayBuffer

class Cli {

  def start() : Unit = {
    val sourcePath = "/Users/roml/WorkSpace/Rev/big-data/movieapp/src/main/scala/io/romellpineda/movieapp/movies.csv"
    FileUtil.getFileContext(sourcePath)
    var str = "movie title, rating, number of reviews, year"
    println("----------" + FileUtil.store(str) + "----------")
    // val arr : ArrayBuffer[String] = FileUtil.getFileContext(sourcePath)
    // try {
    //   println(FileUtil.getFileContext(sourcePath))
    // } catch {
    //   case e : Exception => println(s"Cli start() encountered exception: ${e}")
    // } finally {
    //   println("read file operation clear")
    // }
    
    // Banner
    println("starting command line interface")

    val userInput = StdIn.readLine()

    // .equals not neccessary for case matching -> source: https://docs.scala-lang.org/overviews/scala-book/match-expressions.html
    parseAction(userInput) match {
      case "browse" => {
        println(Protocol.browse())
      }
      case "exit" => {
        println(Protocol.exit())
      }
      case "pay" => {
        println(Protocol.pay())
      }
      case "rent" => {
        println(Protocol.rent())
      }
      case "subscribe" => {
        println(Protocol.subscribe())
      }
      case "unsubscribe" => {
        println(Protocol.unsubscribe())
      }
      case _ => {
        println("Not sure what you mean. Please try again")
      }
    }
  }

  def parseAction(userInput: String): String = {
    val inputArray = userInput.trim().split(" ")

    if (inputArray.length >= 2) {
      return inputArray(1).toLowerCase()
    } else {
      return "?"
    }
  }
}