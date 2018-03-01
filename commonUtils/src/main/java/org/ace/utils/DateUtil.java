package org.ace.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

	/**
	 * 获取当前时间的后days天
	 * @param pattern
	 * @param days 为负数则为前几天，正数则为后几天
	 * @return
	 */
	public static String getDate(String pattern, int days){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, days);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(cal.getTime());
	}
	
	
	public static void main(String[] args) {
		System.out.println(getDate("yyyyMMdd", -40));
	}
}
