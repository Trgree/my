package org.ace.example;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
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
		FileInputFormat.addInputPath(job, new Path(input));
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
	
    public static void main(String[] args)throws Exception{
        int exitcode = ToolRunner.run(new WordDriver(), args);
        System.exit(exitcode);                  
   }

}
