package org.ace.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置工具类
 * 读取properties文件，并缓存key value
 * @author Liangsj
 *
 */
public class PropertiesUtil {
	
	private Properties prop = new Properties();

	public PropertiesUtil(String file) {
		InputStreamReader in = null;
		try {
			in = new InputStreamReader(new FileInputStream(file), "utf-8");
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace(); 
			throw new RuntimeException("读取配置异常", e);
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public  String getString(String key) {
		return prop.getProperty(key);
	}
	
	public String getString(String key, String defaultValue) {
		return prop.getProperty(key, defaultValue);
	}
	
	public int getInt(String key, int defaultVal) {
		int result = defaultVal;
		try {
			result = Integer.parseInt(getString(key));
		} catch (Exception e) {
		}
		return result;
	}
	
	public float getFloat(String key, float defaultVal) {
		float result = defaultVal;
		try {
			result = Float.parseFloat(getString(key));
		} catch (Exception e) {
		}
		return result;
	}
	
	public double getDouble(String key, double defaultVal) {
		double result = defaultVal;
		try {
			result = Double.parseDouble(getString(key));
		} catch (Exception e) {
		}
		return result;
	}
	
	public double getLong(String key, long defaultVal) {
		long result = defaultVal;
		try {
			result = Long.parseLong(getString(key));
		} catch (Exception e) {
		}
		return result;
	}
	
	public void setProperty(String key, String value){
		prop.setProperty(key, value);
	}
	
	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public static void main(String[] args) {
		PropertiesUtil pro = new PropertiesUtil("config/config.properties");
		System.out.println(pro.getString("KEY1"));
	}
}
