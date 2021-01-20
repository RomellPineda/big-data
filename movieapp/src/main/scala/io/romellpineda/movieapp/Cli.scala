package io.romellpineda.movieapp

import scala.io.StdIn
import scala.collection.mutable.ArrayBuffer

class Cli {

  def start() : Unit = {
    
    var cart = new ArrayBuffer[String]()
    var loopMenu = true
    var customerId : Int = 1

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
        case "login" => {
          customerId = Protocol.login()
        }
        case "pay" => {
          println(Protocol.pay(customerId))
        }
        case "rent" => {
          // userInput object will be further evaluated for movie title value
          // OR pass userInput object to rent func
          println(Protocol.rent())
        }
        case "subscribe" => {
          val input = userInput.trim().split(" ")
          println(Protocol.subscribe(input(1), input(2)))
        }
        case "search" => {
          val input = userInput.trim().split(" ")
          println(Protocol.browseTitle(input(1)))
        }
        case "unsubscribe" => {
          val input = userInput.trim().split(" ")
          println(Protocol.unsubscribe(input(1), input(2)))
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