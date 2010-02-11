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
	
	public static Date getUniversalDate(Long time) {
		Date date = new Date();
		date.setTime(time);
		return date;
	}

	public static String getRelativeLink(String href) {
		if(StringUtils.isNotEmpty(href)) {
			int index = -1;
			
			// normalize URL : convert back slashes to slashes
			href = org.apache.commons.lang.StringUtils.replaceChars(href, '\\', '/');
			index = href.indexOf(":///");
			if(index != -1) {
				href = href.substring(index + 4);
			} else {
				index = href.indexOf("://");
				if(index != -1) {
					href = href.substring(index + 3);
				}
			}
			
			// remove any extra slashes that may have been there in the protocol
			while(href != null && href.length() > 0 && "/".equals(href.charAt(0))) {
				href = href.substring(1);
			}
			
			// remove the domain part
			index = href.indexOf("/");
			if(index != -1) {
				return href.substring(index + 1);
			} else {
				// only domain part exists
				return StringUtils.EMPTY_STRING;
			}
		}
		return href;
	}

}
