package io.romellpineda.movieapp

object Runner {

  def main(args: Array[String]) : Unit = {
    Protocol.loadData()

    Display.banner()

    val cli = new Cli()
    cli.start()

    // select c.login_name, m.title from customer as c inner join invoice as i on c.customer_id = i.customer_id inner join movie as m on i.movie_id = m.movie_id;
  }
}