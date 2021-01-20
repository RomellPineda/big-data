package io.romellpineda.movieapp

import scala.io.StdIn
import scala.collection.mutable.ArrayBuffer

class Cli {

  def start() : Unit = {
    
    var loopMenu = true
    
    while (loopMenu) {

      val userInput = StdIn.readLine()
  
      parseAction(userInput) match {
        case "all" => {
          Protocol.browseAll()
        }
        case "exit" => {
          loopMenu = false
        }
        case "pay" => {
          println(Protocol.pay())
        }
        case "rating" => {
          Protocol.browseRating()
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
          Protocol.unsubscribe()
        }
        case _ => {
          println("Not sure what you mean. Please try again")
        }
      }
    }
    println("Thank you!")
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