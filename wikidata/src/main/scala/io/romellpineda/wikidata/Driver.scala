package io.romellpineda.wikidata

import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.Text
import org.apache.hadoop.conf.Configured
import org.apache.hadoop.conf.Configuration

object Driver {
  def main(args: Array[String]): Unit = {
    val conf = new Configuration()
    val job = Job.getInstance(conf)

    job.setJarByClass(Driver.getClass())
    job.setJobName("wikidata")
    job.setInputFormatClass(classOf[TextInputFormat])

    FileInputFormat.setInputPaths(job, new Path(args(0)))
    FileOutputFormat.setOutputPath(job, new Path(args(1)))

    // job.setMapperClass(classOf[LiteMapper])

    // job.setOutputKeyClass(classOf[Text])
    // job.setOutputValueClass(classOf[Text])

    val success = job.waitForCompletion(true)
    System.exit(if (success) 0 else 1)
  }
}