package io.romellpineda.movieapp

object Protocol {
  val browseAllQuery = "SELECT DISTINCT title FROM movie;"
  val browseRatingQuery = "SELECT title, rating FROM movie ORDER BY rating DESC LIMIT 100;"
  val createCustomerTable = "CREATE TABLE IF NOT EXISTS customer (customer_id SERIAL NOT NULL PRIMARY KEY, first_name VARCHAR(50) NOT NULL, last_name VARCHAR(50) NOT NULL, balance int DEFAULT 0);"
  val createMovieTable = "DROP TABLE IF EXISTS movie CASCADE; CREATE TABLE movie (movie_id SERIAL NOT NULL PRIMARY KEY, title VARCHAR(255) NOT NULL, rating VARCHAR(50) DEFAULT 0.0);"
  val createInvoiceTable = "DROP TABLE IF EXISTS invoice CASCADE; CREATE TABLE invoice (customer_id int REFERENCES customer (customer_id) ON UPDATE CASCADE ON DELETE CASCADE, movie_id int REFERENCES movie (movie_id) ON UPDATE CASCADE ON DELETE CASCADE);"
  val sourcePath = "/Users/roml/WorkSpace/Rev/big-data/movieapp/src/main/scala/io/romellpineda/movieapp/movies.csv"

  def browseAll() : Unit = {
    println("------- List of all movies ------")
    var dbResult = ConnectUtil.getMovies(browseAllQuery)
    dbResult.foreach(println)
    println("---------- end of list ----------")
  }
  
  def browseRating() : Unit = {
    // val browseRatingQuery = "SELECT title, rating FROM (SELECT * FROM movie ORDER BY rating DESC LIMIT 100) AS x GROUP BY title, avg(rating);"
    println("------- List of top movies -------")
    var dbResult = ConnectUtil.getTop100(browseRatingQuery)
    dbResult.foreach(println)
    println("---------- end of list ----------")
  }
  
  def browseTitle() : String = {
    "running browseTitle protocol from Protocol file"
  }
  
  def pay() : String = {
    "running pay protocol from Protocol file"
  }

  def loadData() : Unit = {
    ConnectUtil.callDb(createCustomerTable)
    ConnectUtil.callDb(createMovieTable)
    ConnectUtil.callDb(createInvoiceTable)
    FileUtil.getFileContext(sourcePath)
  }

  def rent() : String = {
    "running rent protocol from Protocol file"
  }

  def subscribe() : String = {
    "running subscribe protocol from Protocol file"
  }

  def unsubscribe() : Unit = {
    println("You have unsubscribed from the Movie Rental App")
  }
}