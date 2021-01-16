package io.romellpineda.movieapp

import scala.io.StdIn
import scala.collection.mutable.ArrayBuffer

class Cli {

  def start() : Unit = {
    val sourcePath = "/Users/roml/WorkSpace/Rev/big-data/movieapp/src/main/scala/io/romellpineda/movieapp/movies.csv"
    val userInput = StdIn.readLine()

    FileUtil.getFileContext(sourcePath)

    parseAction(userInput) match {
      case "all" => {
        println(Protocol.browseAll())
      }
      case "exit" => {
        println(Protocol.exit())
      }
      case "pay" => {
        println(Protocol.pay())
      }
      case "rating" => {
        println(Protocol.browseRating())
      }
      case "rent" => {
        // userInput object will be further evaluated for movie title value
        // OR pass userInput object to rent func
        println(Protocol.rent())
      }
      case "subscribe" => {
        println(Protocol.subscribe())
      }
      case "title" => {
        // userInput object will be further evaluated for movie title value
        // OR pass userInput object to rent func
        println(Protocol.browseTitle())
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