package org.myjerry.util;

public class WebUtils {
	
	public final static String getUrlStringFromPostTitle(String postTitle) {
		if(StringUtils.isEmpty(postTitle)) {
			return null;
		}
		String url = postTitle.toLowerCase();
		url = url.replace(' ', '-');
		return url + ".html";
	}

}
