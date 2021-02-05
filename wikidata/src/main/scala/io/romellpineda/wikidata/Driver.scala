package io.romellpineda.wikidata

import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.Text
import org.apache.hadoop.conf.Configured
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.io.IntWritable

object Driver {
  def main(args: Array[String]): Unit = {
    println("script for PJ1 can be found in hive.hql")
  }
}