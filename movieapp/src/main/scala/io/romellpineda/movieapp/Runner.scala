package io.romellpineda.movieapp

object Runner {

  def main(args: Array[String]) : Unit = {

    val createCustomerTable = "CREATE TABLE IF NOT EXISTS customer (customer_id SERIAL NOT NULL PRIMARY KEY, first_name VARCHAR(50) NOT NULL, last_name VARCHAR(50) NOT NULL, balance int DEFAULT 0);"
    val createMovieTable = "DROP TABLE IF EXISTS movie CASCADE; CREATE TABLE movie (movie_id SERIAL NOT NULL PRIMARY KEY, title VARCHAR(255) NOT NULL, rating VARCHAR(50) DEFAULT 0.0);"
    val createInvoiceTable = "DROP TABLE IF EXISTS invoice CASCADE; CREATE TABLE invoice (customer_id int REFERENCES customer (customer_id) ON UPDATE CASCADE ON DELETE CASCADE, movie_id int REFERENCES movie (movie_id) ON UPDATE CASCADE ON DELETE CASCADE);"
    val sourcePath = "/Users/roml/WorkSpace/Rev/big-data/movieapp/src/main/scala/io/romellpineda/movieapp/movies.csv"

    ConnectUtil.callDb(createCustomerTable)
    ConnectUtil.callDb(createMovieTable)
    ConnectUtil.callDb(createInvoiceTable)
    FileUtil.getFileContext(sourcePath)

    // to do : build banner class + def returns Unit but prints movie rentals banner
    println("starting command line interface")

    val cli = new Cli()
    cli.start()
  }
}