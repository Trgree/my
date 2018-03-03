package org.ace.example;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordDriver extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();

		String input = conf.get("input");
		String output = conf.get("output");
		String date = conf.get("date");

		String testConf = conf.get("ace.test.conf");
		System.out.println("testconf:"+testConf);
		
		if (!check(input, "input") || !check(output, "output") || !check(date, "date")) {
			return 1;
		}

		// 如果输出目录已存在，需要删除
		FileSystem fsTarget = FileSystem.get(URI.create(output), conf);
		Path pathTarget = new Path(output);
		if (fsTarget.exists(pathTarget)) {
			fsTarget.delete(pathTarget, true);
		}

		Job job = Job.getInstance(conf);
		job.setJobName("Word Count[" + date + "]");
		job.setJarByClass(getClass());
		job.setMapperClass(WordMapper.class);
		job.setCombinerClass(WordReducer.class);
		job.setReducerClass(WordReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// 设置map输出压缩，提高性能 
		conf.setBoolean("mapreduce.map.output.compress", true);
		conf.setClass("mapreduce.map.output.compress.codec",GzipCodec.class, CompressionCodec.class);  
		
		// reduce输出压缩
//	    FileOutputFormat.setCompressOutput(job, true);  
//	    FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
		
//		FileInputFormat.addInputPath(job, new Path(input));
//		FileInputFormat.addInputPaths(job, input);// 以逗号分隔的多个路径
		String[] inputpaths = getPathStrings(input, conf);  // 以逗号分隔的多个路径
		if(inputpaths == null || inputpaths.length == 0) {
			System.out.println("输入路径没有，程序退出：" + input);
			return 1;
		}
		for(String path: inputpaths) {
			FileInputFormat.addInputPath(job, new Path(path));
			System.out.println("input:");
			System.out.println(path);
		}
		FileOutputFormat.setOutputPath(job, new Path(output));
		
		return job.waitForCompletion(true) ? 0 : 1;
	}

	private static boolean check(final String value, final String key) {
		System.out.println(key + ":" + value);
		if (value == null) {
			System.err.println("no " + key + " param, Usage: -D " + key + "=xxx");
			System.out.println("Usage: hadoop jar  <jarfile> WordDriver -D input=xxx -D output=xxx -D date=20170215 -D mapreduce.job.reduces=4");
			return false;
		}
		return true;
	}
	
	private  String[] getPathStrings(String commaSeparatedPaths,Configuration conf) {
		List<String> pathStrings = new ArrayList<String>();
		String[] arr = commaSeparatedPaths.split(",");
		for(String p : arr) {
			if(hdfsPathExists(p,conf)) {
				pathStrings.add(p);
			}
		}
		return pathStrings.toArray(new String[0]);
	}
	
	private  boolean hdfsPathExists(String path, Configuration conf){
		if(null==path || path.trim().equals("")) {
			return false;
		}
		try {
			Path pathTarget = new Path(path);
			FileSystem fsTarget = FileSystem.get(URI.create(path),conf);
			return (fsTarget.exists(pathTarget));
		} catch (IOException e) {
			return false;
		}
        
	}
	
    public static void main(String[] args)throws Exception{
        int exitcode = ToolRunner.run(new WordDriver(), args);
        System.exit(exitcode);                  
   }

}
