# Wikipedia Data Analysis

## Project Description

Analysis of publically accessible Wikipedia datasets to provide insight into the following questions:
- Which English Wikipedia article got the most traffic on October 20, 2020?
- What English Wikipedia article has the largest fraction of its readers follow an internal link to another Wikipedia article?
- What series of Wikipedia articles, starting with Hotel California, keeps the largest fraction of its readers clicking on internal links?
- Find an example of an English Wikipedia article that is relatively more popular in the UK, then find the same for the US and Australia.
- How many users will see the average vandalized Wikipedia page before the offending edit is reversed?
- Which U.S. National Park receive the most traffic on the U.S. National Park Service birthday?

## Technologies Used

* Scala version 2.13.4
* Hadoop version 2.7.7
* Hadoop MapReduce version 2.7.7
* DBeaver version 7.3.5

## Features

* Analyzes high volume Wikipedia dataset library.
* Provides queries to answer patterns or events.

## Getting Started
   
1. Install all required dependencies.
2. Ssh into localhost.
3. Start HDFS.
4. Start Yarn.
5. Run `hiveserver2`
6. Open DBeaver and configure it to your local Hive server.
7. Run the provided HIVEQL queries.

## Usage

Can be used to demonstrate the ease at which large datasets can be queried to find solutions for relevant inquiries pertaining to the publically available Wikipedia data library.
Can also be used as a template for similar inquires of other datasets.

## Contributors

Romell Pineda

## License

This project uses the following license: [MIT License]("https://opensource.org/licenses/MIT").
