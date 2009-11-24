package org.myjerry.evenstar.service;

import java.util.Collection;

import org.myjerry.evenstar.model.Blog;

public interface BlogService {
	
	public boolean createBlog(String blogName, String blogAddress);
	
	public boolean deleteBlog(String blogName);

	public boolean existsBlogName(String blogTitle);

	public boolean existsBlogAddress(String blogAddress);
	
	public Collection<Blog> getAllBlogs();
	
	public Long getDefaultBlogID();

	public boolean setDefaultBlogID(Long blogID);
	
}
