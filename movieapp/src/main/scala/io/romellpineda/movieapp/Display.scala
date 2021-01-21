package io.romellpineda.movieapp

object Display {

  def banner() : Unit = {
    println("")
    println("                    *************************************************************")
    println("                    **        ___  ___   _____   _______  __   _____          **")
    println("                    **       |   \\/   | |     | |   |   ||  | |  ___|        **")
    println("                    **       |  |\\/|  | |  |  |  \\  |  / |  | |  ___|        **")
    println("                    **       |__|  |__| |_____|   \\___/  |__| |_____|        **")
    println("                    **                ------- RENTALS -------                 **")
    println("                    *************************************************************")
  }

  def menu() : Unit = {
    println("")
    println("Commands Available")
    println("==================")
    println("all : to browse list all movies ")
    println("highest : to view list of highest rated movies")
    println("search [movie title] : to search for movie by title")
    println("subscribe [username] [password] : to sign up new customers")
    println("login [username] [password] : to login")
    println("rent [movie_id] : to rent a movie by movie id")
    println("pay : to pay your balance")
    println("exit : to exit store")
    println("unsubscribe [username] [password] : to cancel your account")
    println("")
  }
}