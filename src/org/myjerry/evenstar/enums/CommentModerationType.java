package org.myjerry.evenstar.enums;

import org.myjerry.util.StringUtils;

public enum CommentModerationType {
	
	NEVER,
	
	ALWAYS,
	
	OLDER;
	
	public static CommentModerationType getModeration(String string) {
		if("never".equalsIgnoreCase(string)) {
			return NEVER;
		} else if("always".equalsIgnoreCase(string)) {
			return ALWAYS;
		} else if("older".equalsIgnoreCase(string)) {
			return OLDER;
		}
		return OLDER;
	}
	
	@Override
	public String toString() {
		String value = StringUtils.EMPTY_STRING;
		
		switch(this) {
			case ALWAYS:
				value = "always";
				break;
				
			case OLDER:
				value = "older";
				break;
				
			case NEVER:
				value = "never";
				break;
		}
		
		return value;
	}

}
