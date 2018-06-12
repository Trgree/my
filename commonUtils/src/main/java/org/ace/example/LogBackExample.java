package org.ace.example;

import java.io.IOException;

import ch.qos.logback.classic.util.ContextInitializer;
import org.ace.example.debug.DebugTest;
import org.ace.utils.LogBackConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.joran.spi.JoranException;

/**
 * logback日志使用
 * @author Liangsj
 *
 */
public class LogBackExample {

	public static void main(String[] args) throws IOException, JoranException {
		// 设置系统变量，logback可使用此变量
		System.setProperty("WORKPATH", "E:\\work\\dev\\workspace_test\\commonUtils");
		// 使用指定logback配置文件
		// 1。也可以使用 java -Dlogback.configurationFile=/path/to/config.xml 指定
		// 2。
		 System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, "commonUtils/config/logback.xml");
		// 3。使用代码加载
	//	LogBackConfigLoader.load("commonUtils/config/logback.xml");
		// 在加载完logback配置后面初始化Logger
		Logger logger = LoggerFactory.getLogger(LogBackExample.class);
		logger.info("log...");
		DebugTest.test();
	}
}
