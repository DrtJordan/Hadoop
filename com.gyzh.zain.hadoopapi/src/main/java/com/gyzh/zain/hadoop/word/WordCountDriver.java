package com.gyzh.zain.hadoop.word;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * 执行MR任务
 * 
 * 出错原因：
 * 1.包倒错了
 * 2.输入、输出路径写错了
 * 3.输出路径的result已存在
 * 
 * 
 * @author zain
 * 17/04/04
 */
public class WordCountDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        
        // 设置job工作的运行类
        job.setJarByClass(WordCountDriver.class);
        // 设置Mapper组件运行类
        job.setMapperClass(WordCountMapper.class);
        // 设置Reducer组件运行类
        job.setReducerClass(WordCountReducer.class);
        
        // 设置Mapper组件输出的key的类型
        job.setMapOutputKeyClass(Text.class);
        // 设置Mapper组件输出的value的类型
        job.setMapOutputValueClass(IntWritable.class);
        // 设置Reducer组件输出的key的类型
        job.setOutputKeyClass(Text.class);
        // 设置Reducer组件输出的value的类型
        job.setOutputValueClass(Text.class);
        
        // 设置reduce任务数量，根据key值的hashcode进行分区
        // job.setNumReduceTasks(1);
        // 设置自定义分区类，如果设置了，则Hadoop就不用默认的分区方式了
        // job.setPartitionerClass(WordPartitioner.class);
        
        // 设置job处理文件所在的HDFS路径
        // 输入路径可以指到目录级别，它会把目录的所有文件都进行处理，不递归处理其他子目录的文件
        FileInputFormat.setInputPaths(job, new Path("hdfs://106.75.108.147:9000/words.txt"));
        // MR处理生成的结果是以文件形式来存储的，这个结果文件也必须放在HDFS上，这个输出路径也需要指定
        FileOutputFormat.setOutputPath(job, new Path("hdfs://106.75.108.147:9000/result"));
        
        // 提交任务
        job.waitForCompletion(true);
        
    }
}
