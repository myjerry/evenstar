package org.myjerry.evenstar.service;

public interface FeedService {

	public void updatePostFeed();
	
	public String getPostFeed();
	
	public String getCommentsFeed();
	
	public String getPostCommentFeed(Long blogPostID);
}
