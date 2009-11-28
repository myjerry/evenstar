package org.myjerry.evenstar.service;

public interface BlogLayoutService {

	public String getBlogTemplate(Long blogID);
	
	public boolean saveBlogTemplate(Long blogID, String template);
	
	public String getDefaultBlogTemplate();
	
}
