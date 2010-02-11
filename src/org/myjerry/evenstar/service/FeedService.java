package org.myjerry.evenstar.service;

public interface FeedService {

	public void updatePostFeed(Long blogID);
	
	public String getPostFeed(Long blogID);
	
	public String getCommentsFeed(Long blogID);
	
	public String getPostCommentFeed(Long postID, Long blogID);
}
