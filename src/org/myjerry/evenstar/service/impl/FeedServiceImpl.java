/**
 * myJerry | Evenstar
 * Copyright (C) 2010 myJerry Development Team
 * http://www.myjerry.org
 * 
 * The file is licensed under the the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.myjerry.evenstar.service.impl;

import java.util.Collection;

import org.myjerry.evenstar.enums.FeedFormat;
import org.myjerry.evenstar.helper.FeedGenerationHelper;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.evenstar.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

public class FeedServiceImpl implements FeedService {

	@Autowired
	private BlogPostService blogPostService;

	@Override
	public String getCommentsFeed(FeedFormat feedFormat, Long blogID) {
		if(blogID == null || feedFormat == null) {
			return null;
		}

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPostCommentFeed(FeedFormat feedFormat, Long blogID, Long postID) {
		if(blogID == null || postID == null || feedFormat == null) {
			return null;
		}

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPostFeed(FeedFormat feedFormat, Long blogID, Long postID) {
		if(blogID == null || postID == null || feedFormat == null) {
			return null;
		}
		
		BlogPost post = this.blogPostService.getPost(postID, blogID);
		SyndFeed feed = FeedGenerationHelper.getPostFeed(post);
		return feedPersist(feed, feedFormat);
	}

	@Override
	public String getPostsFeed(FeedFormat feedFormat, Long blogID) {
		if(blogID == null || feedFormat == null) {
			return null;
		}
		
		Collection<BlogPost> posts = this.blogPostService.getBlogPosts(blogID, 25);
		SyndFeed feed = FeedGenerationHelper.getPostsFeed(posts);
		return feedPersist(feed, feedFormat);
	}

	private String feedPersist(SyndFeed feed, FeedFormat feedFormat) {
		if(feedFormat != null) {
			feed.setFeedType(feedFormat.toString());
			SyndFeedOutput output = new SyndFeedOutput();
			try {
				return output.outputString(feed);
			} catch (FeedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void updatePostFeed(Long blogID) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the blogPostService
	 */
	public BlogPostService getBlogPostService() {
		return blogPostService;
	}

	/**
	 * @param blogPostService the blogPostService to set
	 */
	public void setBlogPostService(BlogPostService blogPostService) {
		this.blogPostService = blogPostService;
	}
}
