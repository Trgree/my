package org.ace.thread;

public class Example {

	public static void main(String[] args) {
		ThreadPool threadPool = new ThreadPool(64, 10000);
		for(int i=0; i < 10; i++){
			ExampleTask t = new ExampleTask(i);
			threadPool.execute(t);
		}
	}
}
