package org.myjerry.evenstar.service.impl;

import java.util.Collection;

import org.myjerry.evenstar.helper.FeedGenerationHelper;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.evenstar.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;

public class FeedServiceImpl implements FeedService {

	@Autowired
	private BlogPostService blogPostService;

	@Override
	public String getCommentsFeed(Long blogID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPostCommentFeed(Long postID, Long blogID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPostFeed(Long blogID) {
		Collection<BlogPost> posts = this.blogPostService.getBlogPosts(blogID, 10);
		if(posts != null && posts.size() > 0) {
			return FeedGenerationHelper.getPostsFeed(posts);
		}
		return null;
	}

	@Override
	public void updatePostFeed(Long blogID) {
		// TODO Auto-generated method stub
		
	}

}
