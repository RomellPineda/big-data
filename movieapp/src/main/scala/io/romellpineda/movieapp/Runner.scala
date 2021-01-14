package io.romellpineda.movieapp

object Runner {

  def main(args: Array[String]) : Unit = {

    val connectionUrl = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres"

    val cli = new Cli()
    cli.start()
  }
}