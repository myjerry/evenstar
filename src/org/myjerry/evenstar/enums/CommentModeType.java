package org.myjerry.evenstar.enums;

import org.myjerry.util.StringUtils;

public enum CommentModeType {
	
	ANYONE,
	
	OPENID,
	
	GOOGLE,
	
	READERS,
	
	NAMEDURL;
	
	public static CommentModeType getCommentMode(String string) {
		if("anyone".equalsIgnoreCase(string)) {
			return ANYONE;
		} else if("openid".equalsIgnoreCase(string)) {
			return OPENID;
		} else if("google".equalsIgnoreCase(string)) {
			return GOOGLE;
		} else if("readers".equalsIgnoreCase(string)) {
			return READERS;
		} else if("namedurl".equalsIgnoreCase(string)) {
			return NAMEDURL;
		}
		return OPENID;
	}
	
	@Override
	public String toString() {
		String value = StringUtils.EMPTY_STRING;
		
		switch(this) {
			case ANYONE:
				value = "anyone";
				break;
				
			case OPENID:
				value = "openid";
				break;
				
			case GOOGLE:
				value = "google";
				break;
				
			case READERS:
				value = "readers";
				break;
				
			case NAMEDURL:
				value = "namedurl";
				break;
		}
		
		return value;
	}

}
