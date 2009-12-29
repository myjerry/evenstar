package org.myjerry.evenstar.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.myjerry.evenstar.view.LabelInfo;

public class LabelServiceHelper {
	
	public static String[] tokenizeLabels(String labels) {
		if(labels != null && labels.length() > 0) {
			return StringUtils.split(labels, " ;,");
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
			String tokens[] = StringUtils.split(labels, " ;,");
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
