package org.myjerry.evenstar.service;

import java.util.Collection;

import org.myjerry.evenstar.model.Blog;

public interface BlogService {
	
	public boolean createBlog(String blogName, String blogAddress, String blogAlias);
	
	public boolean deleteBlog(Long blogID);

	public boolean existsBlogName(String blogTitle);

	public boolean existsBlogAddress(String blogAddress);
	
	public boolean existsBlogAlias(String alias);
	
	public Collection<Blog> getAllBlogs();
	
	public Long getDefaultBlogID();

	public boolean setDefaultBlogID(Long blogID);
	
	public Blog getBlog(Long blogID);

	public boolean updateBlog(Blog blog);

	public Long getBlogIDForServerName(String serverName);
	
	}
