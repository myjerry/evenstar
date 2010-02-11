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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtils {
	
	public final static String END_OF_LINE = System.getProperty("line.separator");
	
	public final static String EMPTY_STRING = "";
	
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
	
	public static String getString(InputStream stream) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(END_OF_LINE);
            }
            return builder.toString();
        } catch(Exception e) {
        	e.printStackTrace();
        } finally {
            if(reader != null) {
            	try {
            		reader.close();
            	} catch(Exception e) {
            		e.printStackTrace();
            	}
            }
        }
        return null;
    }
	
	public static Long getLong(String value) {
		if(isNotEmpty(value)) {
			try {
				Long l = Long.parseLong(value);
				return l;
			} catch(NumberFormatException e) {
				
			}
		}
		return null;
	}
	
	public static Boolean getBoolean(String value) {
		return getBoolean(value, false);
	}
	
	public static Boolean getBoolean(String value, boolean defaultValue) {
		if(isNotEmpty(value)) {
			if("true".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value)) {
				return true;
			}
			if("false".equalsIgnoreCase(value) || "no".equalsIgnoreCase(value)) {
				return false;
			}
		}
		return defaultValue;
	}
}
