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
package org.myjerry.evenstar.web.feed;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.enums.FeedFormat;
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.evenstar.service.FeedService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * Feeds are supported in the following format
 * 
 * http://domain/feeds/posts/default
 * http://domain/feeds/posts/postID/default
 * http://domain/feeds/posts/postID/comments
 * http://domain/feeds/comments/default
 * 
 * http://domain/feeds/alias/posts/default
 * http://domain/feeds/alias/posts/postID/default
 * http://domain/feeds/alias/posts/postID/comments
 * http://domain/feeds/alias/comments/default
 * 
 * @author sangupta
 *
 */
public class FeedController extends MultiActionController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private FeedService feedService;

	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uri = request.getRequestURI();
		FeedFormat feedFormat = null;
		
		// strip off the 'feeds/' from the URI
		uri = uri.substring(7);
		int extIndex = uri.lastIndexOf(".");
		if(extIndex != -1) {
			String extension = uri.substring(extIndex + 1);
			uri = uri.substring(0, extIndex);
			feedFormat = FeedFormat.getFeedFormat(extension);
		}
		
		if(feedFormat == null) {
			// bad request
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
		Long blogID = this.blogService.getBlogIDForServerName(request.getServerName());
		if(blogID == null) {
			// try and see if the URI contains the blog ID
			String alias = uri;
			int index = uri.indexOf("/");
			if(index != -1) {
				alias = uri.substring(0, index);
			}
			Long id = this.blogService.getBlogIDForAlias(alias);
			if(id != null) {
				blogID = id;
				uri = uri.substring(index + 1);
			} else {
				// just can't do anything now
				// no feed found
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
		}
		
		// decipher the type of feed
		if("posts/default".equals(uri)) {
			return blogPostsFeed(feedFormat, blogID);
		} else if("comments/default".equals(uri)) {
			return blogCommentsFeed(feedFormat, blogID);
		} else if(uri.startsWith("posts/")) {
			// try and get the post ID from this
			uri = uri.substring(6);
			int index = uri.indexOf("/");
			if(index != -1) {
				Long postID = StringUtils.getLong(uri.substring(0, index));
				if(postID != null) {
					uri = uri.substring(index + 1);
					if("default".equals(uri)) {
						return blogPostFeed(feedFormat, blogID, postID);
					} else if("comments".equals(uri)) {
						return blogPostCommentFeed(feedFormat, blogID, postID);
					}
				}
			}
		}
		
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return null;
	}
	
	private ModelAndView blogPostsFeed(FeedFormat feedFormat, Long blogID) {
		String feedContents = this.feedService.getPostsFeed(feedFormat, blogID);
		return getModelAndView("Blog All Posts Feed");
	}

	private ModelAndView blogCommentsFeed(FeedFormat feedFormat, Long blogID) {
		String feedContents = this.feedService.getCommentsFeed(feedFormat, blogID);
		return getModelAndView("Blog All Comments Feed");
	}

	private ModelAndView blogPostFeed(FeedFormat feedFormat, Long blogID, Long postID) {
		String feedContents = this.feedService.getPostFeed(feedFormat, blogID, postID);
		return getModelAndView("Blog Post Feed");
	}

	private ModelAndView blogPostCommentFeed(FeedFormat feedFormat, Long blogID, Long postID) {
		String feedContents = this.feedService.getPostCommentFeed(feedFormat, blogID, postID);
		return getModelAndView("Blog Comment Feed");
	}
	
	private ModelAndView getModelAndView(String feedContents) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("contents", feedContents);
		mav.setViewName(".feed.output");
		return mav;
	}
	
	//---------------------------------------------
	// Usual accessor's follow
	//---------------------------------------------

	/**
	 * @return the blogService
	 */
	public BlogService getBlogService() {
		return blogService;
	}

	/**
	 * @param blogService the blogService to set
	 */
	public void setBlogService(BlogService blogService) {
		this.blogService = blogService;
	}

	/**
	 * @return the feedService
	 */
	public FeedService getFeedService() {
		return feedService;
	}

	/**
	 * @param feedService the feedService to set
	 */
	public void setFeedService(FeedService feedService) {
		this.feedService = feedService;
	}

}
