package org.ace.secondarysort;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class Test {

	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		FileInputFormat.addInputPaths(job, "a,b");
		
		System.out.println(conf.get(FileInputFormat.INPUT_DIR));
		
		IntPair in = new IntPair();
		in.setFirst(2);
		in.setSecond(2);
		
		IntPair in2 = new IntPair();
		in2.setFirst(2);
		in2.setSecond(3);
		
		System.out.println(in.compareTo(in2));
		
		StringTokenizer st = new StringTokenizer("sdf|df".toString(), "|");
		System.out.println(st.nextToken());
		System.out.println(st.nextToken());
	}
}
