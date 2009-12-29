package org.myjerry.evenstar.enums;

import org.myjerry.util.StringUtils;

public enum BlogArchiveFrequency {
	
	DAILY,
	
	WEEKLY,
	
	MONTHLY,
	
	NONE;
	
	public static BlogArchiveFrequency getFrequency(String string) {
		if("daily".equalsIgnoreCase(string)) {
			return DAILY;
		} else if("weekly".equalsIgnoreCase(string)) {
			return WEEKLY;
		} else if("monthly".equalsIgnoreCase(string)) {
			return MONTHLY;
		}
		return NONE;
	}
	
	@Override
	public String toString() {
		String value = StringUtils.EMPTY_STRING;
		
		switch(this) {
			case DAILY:
				value = "daily";
				break;
			
			case WEEKLY:
				value = "weekly";
				break;
				
			case MONTHLY:
				value = "monthly";
				break;
				
			case NONE:
				value = "none";
				break;
			
		}
		
		return value;
	}

}
