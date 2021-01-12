package io.romellpineda.movieapp

import scala.io.StdIn

class Cli {

  def start() : Unit = {
    println("starting command line interface")

    val userInput = StdIn.readLine()

    // .equals not neccessary for case matching -> source: https://docs.scala-lang.org/overviews/scala-book/match-expressions.html
    parseAction(userInput) match {
      case "browse" => {
        println("running browse protocol")
      }
      case "rent" => {
        println("running rent protocol")
      }
      case "pay" => {
        println("running pay protocol")
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