package org.ace.MR_WordCount;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ace.example.WordMapper;
import org.ace.example.WordReducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

public class WordTest {
	MapDriver<Object, Text, Text, IntWritable> mapDriver;
	ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;
	MapReduceDriver<Object, Text, Text, IntWritable, Text, IntWritable> mapReduceDriver;

	@Before
	public void setUp() {
		WordMapper mapper = new WordMapper();
		WordReducer reducer = new WordReducer();
		mapDriver = MapDriver.newMapDriver(mapper);
		reduceDriver = ReduceDriver.newReduceDriver(reducer);
		mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
		mapDriver.getConfiguration().set("kvsplit", "a");
	}

	@Test
	public void testMapper() throws IOException {
		// map输入
		mapDriver.withInput(new LongWritable(), new Text(
				"World Bye World"));
		mapDriver.withInput(new LongWritable(), new Text(
				"Hadoop Goodbye Hadoop a"));
		// map预期输出
		mapDriver.withOutput(new Text("World"), new IntWritable(1));
		mapDriver.withOutput(new Text("Bye"), new IntWritable(1));
		mapDriver.withOutput(new Text("World"), new IntWritable(1));
		mapDriver.withOutput(new Text("Hadoop"), new IntWritable(1));
		mapDriver.withOutput(new Text("Goodbye"), new IntWritable(1));
		mapDriver.withOutput(new Text("Hadoop"), new IntWritable(1));
		mapDriver.withOutput(new Text("a"), new IntWritable(1));
		
		// 运行测试
		mapDriver.runTest();
	}

	@Test
	public void testReducer() throws IOException {
		// reduce输入
		List<IntWritable> values = new ArrayList<IntWritable>();
		values.add(new IntWritable(2));
		values.add(new IntWritable(1));
		reduceDriver.withInput(new Text("Hadoop"), values);
		
		// reduce预期输出
		reduceDriver.withOutput(new Text("Hadoop"), new IntWritable(3));
		
		// 运行测试
		reduceDriver.runTest();
	}
	
	@Test
	public void testMapperReducer() throws IOException {
		// mapreduce输入
		mapReduceDriver.withInput(new LongWritable(), new Text(
				"World Bye World"));
		mapReduceDriver.withInput(new LongWritable(), new Text(
				"Hadoop Goodbye Hadoop a"));
		
		// mapreduce预期输出
		mapReduceDriver.withOutput(new Text("Hadoop"), new IntWritable(2));
		mapReduceDriver.withOutput(new Text("Bye"), new IntWritable(1));
		mapReduceDriver.withOutput(new Text("Hadoop"), new IntWritable(2));
		mapReduceDriver.withOutput(new Text("Goodbye"), new IntWritable(1));
		mapReduceDriver.withOutput(new Text("a"), new IntWritable(1));
		
		// 运行测试
		mapReduceDriver.runTest();
	}

	@Test
	public void testMapperCount() throws IOException {
		mapDriver.withInput(new LongWritable(), new Text(
				"World Bye World"));
		mapDriver.withInput(new LongWritable(), new Text(
				"World Bye World"));
		
		mapDriver.withOutput(new Text("World"), new IntWritable(1));
		mapDriver.withOutput(new Text("Bye"), new IntWritable(1));
		mapDriver.withOutput(new Text("World"), new IntWritable(1));
		mapDriver.withOutput(new Text("World"), new IntWritable(1));
		mapDriver.withOutput(new Text("Bye"), new IntWritable(1));
		mapDriver.withOutput(new Text("World"), new IntWritable(1));
		mapDriver.runTest();
		assertEquals("Expected 1 counter increment", 2, mapDriver.getCounters()
				.findCounter(WordMapper.LineCounter.ALL).getValue());
	}
}
