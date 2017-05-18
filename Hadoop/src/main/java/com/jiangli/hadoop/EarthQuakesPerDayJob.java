package com.jiangli.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.reduce.IntSumReducer;

public class EarthQuakesPerDayJob {

 public static void main(String[] args) throws Throwable {

// JobConf job = new JobConf();
//// Job job = new Job(conf);
//  job.setJarByClass(EarthQuakesPerDayJob.class);
//  FileInputFormat.addInputPath(job, new Path(args[0]));
//  FileOutputFormat.setOutputPath(job, new Path(args[1]));
//
//  job.setMapperClass(EarthQuakesPerDateMapper.class);
//  job.setReducerClass(EarthQuakesPerDateReducer.class);
//  job.setOutputKeyClass(Text.class);
//  job.setOutputValueClass(IntWritable.class);
//
//  System.exit(job.waitForCompletion(true) ? 0 : 1);

     Configuration conf = new Configuration();

     Job job = Job.getInstance(conf, "word count");
     job.setJarByClass(EarthQuakesPerDayJob.class);
     job.setMapperClass(EarthQuakesPerDateMapper.class);
     job.setReducerClass(IntSumReducer.class);
     job.setCombinerClass(IntSumReducer.class);
     job.setOutputKeyClass(Text.class);
     job.setOutputValueClass(IntWritable.class);

     FileInputFormat.addInputPath(job, new Path(args[0]));
     FileOutputFormat.setOutputPath(job, new Path(args[1]));

     System.exit(job.waitForCompletion(true) ? 0 : 1);
 }
}