package org.myjerry.util;

public class StringUtils {
	
	public static boolean isEmpty(String string) {
		if(string == null || string.length() == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty(String string) {
		return !isEmpty(string);
	}

	public static boolean isBlank(String string) {
		if(isEmpty(string) || string.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotBlank(String string) {
		return !isBlank(string);
	}
}
