package com.jiangli.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Random;

public class EarthQuakesPerDateMapper extends Mapper<LongWritable,
        Text, Text, IntWritable> {
 @Override
 protected void map(LongWritable key, Text value, Context context) throws IOException,
   InterruptedException {

     System.out.println("key:"+key);

  if (key.get() > 0) {
   try {
       System.out.println(value.toString());

       String dtstr="dtstr"+new Random().nextBoolean();

     context.write(new Text(dtstr), new IntWritable(1));
   } catch (Exception e) {}
  }
 }
}