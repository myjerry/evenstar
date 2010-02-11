/**
 * myJerry | Evenstar
 * Copyright (C) 2010 myJerry Development Team
 * http://www.myjerry.org
 * 
 * The file is licensed under the the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
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
		return MONTHLY;
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
				
			case NONE:
				value = "none";
				break;
			
			case MONTHLY:
			default:
				value = "monthly";
				break;
				
		}
		
		return value;
	}

}
