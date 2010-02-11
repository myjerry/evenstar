package org.myjerry.evenstar.enums;

public enum FeedFormat {
	
	RSS,
	
	ATOM;

	public static FeedFormat getFeedFormat(String type) {
		if("atom".equalsIgnoreCase(type)) {
			return ATOM;
		} else if("rss".equalsIgnoreCase(type)) {
			return RSS;
		}
		// this should ideally never happen 
		return null;
	}
}
