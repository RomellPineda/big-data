package io.romellpineda.movieapp

object Runner {

  def main(args: Array[String]) : Unit = {

    ConnectUtil.getConnection()

    val cli = new Cli()
    cli.start()
  }
}