package org.myjerry.evenstar.service;

import java.util.Collection;

import org.myjerry.evenstar.model.BlogPost;

public interface BlogPostService {
	
	public boolean publishPost(BlogPost blogPost);
	
	public boolean schedulePost(BlogPost blogPost);
	
	public boolean saveDraftPost(BlogPost blogPost);

	public boolean deletePost(Long blogPostID, Long blogID);

	public boolean unPublishPost(Long blogPostID, Long blogID);
	
	public BlogPost getPost(Long blogPostID, Long blogID);
	
	public Collection<BlogPost> getAllBlogPosts(Long blogID);
	
	public Collection<BlogPost> getBlogPosts(Long blogID, int page, int count);
	
	public Long getTotalPosts(Long blogID);
}
