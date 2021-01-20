package io.romellpineda.movieapp

object Runner {

  def main(args: Array[String]) : Unit = {
    // Protocol.loadData()

    // to do : build banner class + def returns Unit but prints movie rentals banner
    // println("starting command line interface")
    Display.banner()

    val cli = new Cli()
    cli.start()
  }
}