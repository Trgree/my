package org.ace.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * @author Liangsj
 *
 */
public class ThreadPool {

	private ExecutorService executor;
	
	/**
	 * 初始化线程池
	 * @param maximumPoolSize
	 * @param queueSize
	 */
	public ThreadPool(int maximumPoolSize, int queueSize) {
		executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2, maximumPoolSize, 120L, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(queueSize));
	}
	
	/**
	 * 任务加入执行队列
	 * @param task
	 */
	public void execute(Runnable task) {
		executor.execute(task);
	}
	
	public void shutdown(){
		executor.shutdown();
	}
}
