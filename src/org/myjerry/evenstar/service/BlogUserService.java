package org.myjerry.evenstar.service;

import java.util.Collection;

import org.myjerry.evenstar.model.BlogAuthor;
import org.myjerry.evenstar.model.BlogReader;


public interface BlogUserService {
	
	public boolean isUserBlogAdmin(Long blogID);
	
	public boolean isUserBlogReader(Long blogID);
	
	public boolean isPostAllowedForUser(Long postID, Long blogID);
	

	public boolean addBlogAuthor(String emailAddress, Long blogID);
	
	public boolean deleteBlogAuthor(String emailAddress, Long blogID);
	
	public boolean existsBlogAuthor(String emailAddress, Long blogID);
	
	public Collection<BlogAuthor> getBlogAuthors(Long blogID);
	
	
	public boolean addBlogReader(String emailAddress, Long blogID);

	public boolean deleteBlogReader(String emailAddress, Long blogID);
	
	public boolean existsBlogReader(String emailAddress, Long blogID);
	
	public Collection<BlogReader> getBlogReaders(Long blogID);
	
}
