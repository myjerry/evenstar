package org.myjerry.evenstar.service;

import org.myjerry.evenstar.enums.FeedFormat;

public interface FeedService {

	public void updatePostFeed(Long blogID);
	
	public String getPostsFeed(FeedFormat feedFormat, Long blogID);
	
	public String getCommentsFeed(FeedFormat feedFormat, Long blogID);
	
	public String getPostFeed(FeedFormat feedFormat, Long blogID, Long postID);
	
	public String getPostCommentFeed(FeedFormat feedFormat, Long blogID, Long postID);
}
