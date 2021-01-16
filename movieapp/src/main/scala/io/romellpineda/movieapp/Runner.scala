package io.romellpineda.movieapp

object Runner {

  def main(args: Array[String]) : Unit = {

    ConnectUtil.getConnection()

    // to do : build banner class + def returns Unit but prints movie rentals banner
    println("starting command line interface")

    val cli = new Cli()
    cli.start()
  }
}