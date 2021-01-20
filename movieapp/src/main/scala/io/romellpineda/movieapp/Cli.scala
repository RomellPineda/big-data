package io.romellpineda.movieapp

import scala.io.StdIn
import scala.collection.mutable.ArrayBuffer

class Cli {

  def start() : Unit = {
    
    var cart = new ArrayBuffer[String]()
    var customerId : Int = 5
    var loopMenu = true
    val paidBalance : Int = 0

    while (loopMenu) {

      Display.menu()
      val userInput = StdIn.readLine()
      val inputArr = userInput.trim().split(" ")
  
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
          customerId = Protocol.login(inputArr(1), inputArr(2))
          println("##### customer id = " + customerId)
        }
        case "pay" => {
          println(Protocol.pay(customerId, paidBalance))
        }
        case "rent" => {
          println(Protocol.rent(customerId, inputArr(1).toInt))
        }
        case "subscribe" => {
          println(Protocol.subscribe(inputArr(1), inputArr(2)))
        }
        case "search" => {
          println(Protocol.browseTitle(inputArr(1)))
        }
        case "unsubscribe" => {
          println(Protocol.unsubscribe(inputArr(1), inputArr(2)))
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