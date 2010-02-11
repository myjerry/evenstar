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
