package org.ace.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 计数器
 * @author Liangsj
 *
 */
public class Counter {
	private static final Logger LOGGER = LoggerFactory.getLogger(Counter.class);
	
	public static Map<Enum<?>, Long> counts = new HashMap<Enum<?>, Long>();
	
	/**
	 * 计算增加
	 * @param counterName 要增加的counterName
	 * @param incr 增加的值
	 * @return
	 */
	public static synchronized long increment(Enum<?> counterName, long incr){
		Long c = counts.get(counterName);
		if(c == null){
			c = 0L;
		}
		c += incr;
		counts.put(counterName, c);
		return c;
	}
	
	/**
	 * 打印日志
	 * @param counterName 要打印的counter
	 * @param by 每多少打印counter
	 * @param printstr 打印的字符串,printstr里的{}替换为counter值
	 */
	public static void printby(Enum<?> counterName, int by, String printstr){
		long value = get(counterName);
		if(value % by == 0){
			LOGGER.info(printstr, value);
		}
	}
	
	/**
	 * 打印日志
	 * @param value 要打印的值
	 * @param by 每多少打印value
	 * @param printstr 打印的字符串,printstr里的{}替换为counter值
	 */
	public static void printby(long value, int by, String printstr){
		if(value % by == 0){
			LOGGER.info(printstr, value);
		}
	}
	
	/**
	 * 打印值
	 */
	public static void log(long count){
		LOGGER.debug(count + " counter=" + count);
	}
	
	/**
	 * 获取counter值
	 * @param counterName
	 * @return
	 */
	public static Long get(Enum<?> counterName ){
		Long result = counts.get(counterName) ;
		return result == null ? 0 : result;
	}
	
	public static void main(String[] args) {
		for(int i=0;i<100;i++){
			Counter.increment(TestCounter.ALL, 1);
			printby(TestCounter.ALL, 10, "记录数：{}");
		}
		System.out.println(Counter.get(TestCounter.ALL));
	}
	
	static enum TestCounter {
		ALL;
	};
}
