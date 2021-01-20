package io.romellpineda.movieapp

object Protocol {
  val browseAllQuery = "SELECT title, movie_id FROM movie ORDER BY movie_id;"
  val browseRatingQuery = "SELECT movie_id, title, rating FROM movie ORDER BY rating DESC LIMIT 100;"
  val createCustomerTable = "CREATE TABLE IF NOT EXISTS customer (customer_id SERIAL NOT NULL PRIMARY KEY UNIQUE, login_name VARCHAR(50) NOT NULL, password VARCHAR(50) NOT NULL, balance int DEFAULT 0);"
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
  
  def browseTitle(searchString : String) : Unit = {
    println("/// searching for : " + searchString)
    println("------- List of all movies ------")
    var dbResult = ConnectUtil.searchTitle(searchString)
    dbResult.foreach(println)
    println("---------- end of list ----------")
  }
  
  def pay(customer_id : Int, balance : Int) : String = {
    ConnectUtil.pay(customer_id, balance)
  }

  def loadData() : Unit = {
    ConnectUtil.callDb(createCustomerTable)
    ConnectUtil.callDb(createMovieTable)
    ConnectUtil.callDb(createInvoiceTable)
    FileUtil.getFileContext(sourcePath)
  }

  def login(login_name : String, password : String) : Int = {
    ConnectUtil.login(login_name, password)
  }

  def rent(customer_id : Int, movie_id : Int) : String = {
    ConnectUtil.rent(customer_id, movie_id)
  }

  def subscribe(login_name : String, password : String) : String = {
    ConnectUtil.subscribe(login_name, password)
  }

  def unsubscribe(login_name : String, password : String) : String = {
    ConnectUtil.unsub(login_name, password)
  }
}