package org.ace.version;

/**
 * 查看版本信息
 * @author Liangsj
 *
 */
public class Version {
	public static final String PROJECT = "commonUtils";
	public static final String VERSION = "1.0.1";
	public static final String AUTHOR = "Liangsj";
	public static final String UPDATE_DATE = "2017-09-28 09:00:00";
	
	public static void main(String[] args) {
		System.out.println(Version.getVersion());
	}
	
	public static String getVersion(){
		return  "\n" + PROJECT + " version:" + VERSION 
				+ "\nauthor:" + AUTHOR
				+ "\nupdate date: " + UPDATE_DATE;
	}
}

