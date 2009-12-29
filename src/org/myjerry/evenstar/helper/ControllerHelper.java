package org.myjerry.evenstar.helper;

import javax.servlet.http.HttpServletRequest;

import org.myjerry.evenstar.service.BlogService;

public class ControllerHelper {
	
	public static final Long convertToBlogID(HttpServletRequest request, BlogService blogService) {
		String uri = request.getServerName();
		Long blogID = null;
		
		blogID = blogService.getBlogIDForServerName(uri);
		if(blogID == null) {
			blogID = blogService.getDefaultBlogID();
		}
		
		return blogID;
	}
}
