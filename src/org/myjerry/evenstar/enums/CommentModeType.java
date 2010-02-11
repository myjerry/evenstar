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
