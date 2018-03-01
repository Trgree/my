package org.ace.example;

import java.io.IOException;

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
		LogBackConfigLoader.load("commonUtils/config/logback.xml");
		// 在加载完logback配置后面初始化Logger
		Logger logger = LoggerFactory.getLogger(LogBackExample.class);
		logger.info("log....");
	}
}
