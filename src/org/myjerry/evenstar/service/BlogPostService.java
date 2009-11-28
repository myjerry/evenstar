package org.myjerry.evenstar.service;

import java.util.Collection;

import org.myjerry.evenstar.model.BlogPost;

public interface BlogPostService {
	
	public boolean publishPost(BlogPost blogPost);
	
	public boolean schedulePost(BlogPost blogPost);
	
	public boolean saveDraftPost(BlogPost blogPost);

	public boolean deletePost(Long postID, Long blogID);

	public boolean unPublishPost(Long postID, Long blogID);
	
	public BlogPost getPost(Long postID, Long blogID);
	
	public Collection<BlogPost> getAllBlogPosts(Long blogID);
	
	public Collection<BlogPost> getBlogPosts(Long blogID, int count);
	
	public Long getTotalPosts(Long blogID);
	
	public boolean existsPost(Long postID, Long blogID);
	
	public BlogPost getPostForURI(String uri);

}
