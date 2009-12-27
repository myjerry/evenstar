package org.myjerry.util;

import java.util.Date;

public class ServerUtils {
	
	public static Date getServerDate() {
		return new Date();
	}
	
	public static String getUniversalDateString(Date date) {
		if(date != null) {
			return String.valueOf(date.getTime());
		}
		return StringUtils.EMPTY_STRING;
	}
	
	public static Date getUniversalDate(String string) {
		Date date = new Date();
		date.setTime(Long.valueOf(string));
		return date;
	}

}
