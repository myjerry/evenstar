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
package org.myjerry.evenstar.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.myjerry.evenstar.view.LabelInfo;

public class LabelServiceHelper {
	
	private static final String LABEL_DELIMITERS = ";,";
	
	public static String[] tokenizeLabels(String labels) {
		if(labels != null && labels.length() > 0) {
			return StringUtils.split(labels, LABEL_DELIMITERS);
		}
		return new String[] { labels };
	}
	
	public static List<String> tokenizeLabelsAsList(String labels) {
		String[] tokens = tokenizeLabels(labels);
		return Arrays.asList(tokens);
	}
	
	public static List<LabelInfo> getLabels(String labels) {
		List<LabelInfo> list = new ArrayList<LabelInfo>();
		if(labels != null && labels.length() > 0) {
			String tokens[] = StringUtils.split(labels, LABEL_DELIMITERS);
			int length = tokens.length;
			if(length > 0) {
				for(int i=0; i < length; i++) {
					String token = tokens[i];
					if(token != null && token.trim().length() > 0) {
						list.add(new LabelInfo(token));
					}
				}
				return list; 
			}
		}
		return null;
	}

}
