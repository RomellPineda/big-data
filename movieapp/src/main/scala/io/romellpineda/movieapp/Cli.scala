package io.romellpineda.movieapp

import scala.io.StdIn
import scala.collection.mutable.ArrayBuffer

class Cli {

  def start() : Unit = {
    
    var cart = new ArrayBuffer[String]()
    var loopMenu = true
    var customerId = null

    while (loopMenu) {

      Display.menu()
      val userInput = StdIn.readLine()
  
      parseAction(userInput) match {
        case "all" => {
          Protocol.browseAll()
        }
        case "exit" => {
          loopMenu = false
        }
        case "highest" => {
          Protocol.browseRating()
        }
        case "pay" => {
          println(Protocol.pay())
        }
        case "rent" => {
          // userInput object will be further evaluated for movie title value
          // OR pass userInput object to rent func
          println(Protocol.rent())
        }
        case "subscribe" => {
          println(Protocol.subscribe())
        }
        case "search" => {
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

  def parseAction(userInput: String) : String = {
    val inputArray = userInput.trim().split(" ")

    if (inputArray.length >= 1) {
      return inputArray(0).toLowerCase()
    } else {
      return "?"
    }
  }
}