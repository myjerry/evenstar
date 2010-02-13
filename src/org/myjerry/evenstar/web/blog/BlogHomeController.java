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
package org.myjerry.evenstar.web.blog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.myjerry.evenstar.constants.BlogPreferenceConstants;
import org.myjerry.evenstar.helper.ControllerHelper;
import org.myjerry.evenstar.helper.TemplateHelper;
import org.myjerry.evenstar.model.Blog;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.service.BlogLayoutService;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.evenstar.service.BlogPreferenceService;
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.evenstar.service.UserService;
import org.myjerry.evenstar.service.ViewPostService;
import org.myjerry.evenstar.web.EvenstarController;
import org.myjerry.util.ServerUtils;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

public class BlogHomeController extends EvenstarController {
	
	private static final Logger log = Logger.getLogger(BlogHomeController.class.getName());
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private BlogPreferenceService blogPreferenceService;
	
	@Autowired
	private BlogPostService blogPostService;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private BlogLayoutService blogLayoutService;
	
	@Autowired
	private ViewPostService viewPostService;
	
	@Autowired
	private UserService userService;

	@SuppressWarnings("unchecked")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long older = StringUtils.getLong(request.getParameter("older"));
		Long newer = StringUtils.getLong(request.getParameter("newer"));
		
		ModelAndView mav = getModelAndView(request);
		Long blogID = ControllerHelper.convertToBlogID(request, this.blogService);
		
		if(blogID == null) {
			mav.setViewName(".evenstar");
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			Blog blog = this.blogService.getBlog(blogID);
			
			String numPostsString = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.showNumPosts);
			int numPosts = 10;
			if(StringUtils.isNotEmpty(numPostsString)) {
				numPosts = Integer.valueOf(numPostsString);
			}
			
			Collection<BlogPost> postsCollection = null;
			if(older == null && newer == null) {
				postsCollection = this.blogPostService.getBlogPosts(blogID, numPosts);
			} else {
				if(older != null && newer != null) {
					postsCollection = this.blogPostService.getBlogPosts(blogID, numPosts, older, newer);
				} else if(older != null) {
					Date lastUpdatedDate = ServerUtils.getUniversalDate(older);
					postsCollection = this.blogPostService.getOlderBlogPosts(blogID, numPosts, lastUpdatedDate);
				} else if(newer != null) {
					Date lastUpdatedDate = ServerUtils.getUniversalDate(newer);
					postsCollection = this.blogPostService.getNewerBlogPosts(blogID, numPosts, lastUpdatedDate);
				}
			}
			
			List<BlogPost> posts = null;
			if(postsCollection != null) {
				posts = new ArrayList<BlogPost>(postsCollection);
			} else {
				posts = new ArrayList<BlogPost>();
			}

			if(posts != null && posts.size() > 0) {
				if(posts.size() > numPosts) {
					int last = posts.size() - 1;
					BlogPost lastPost = posts.get(last);
					model.put("olderPageUrl", "/showPosts.html?older=" + ServerUtils.getUniversalDateString(lastPost.getPostedDate()));
					posts.remove(last);
				} else {
					model.put("olderPageUrl", "");
				}
				
				if(!this.blogPostService.isFirstPost(posts.get(0))) {
					model.put("newerPageUrl", "/showPosts.html?newer=" + ServerUtils.getUniversalDateString(posts.get(0).getPostedDate()));
				} else {
					model.put("newerPageUrl", "");
				}
			}
			
			model.putAll(mav.getModel());
			model = this.viewPostService.getPostsViewModel(blog, posts, false, model);
			String generatedBlogPage = TemplateHelper.generateBlogPage(blogID, model, blogLayoutService, velocityEngine);
			
			mav.addAllObjects(model);
			mav.addObject("generatedBlogPage", generatedBlogPage);
			mav.setViewName(".view.blog.home");
		}
		return mav;
	}

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
	 * @return the blogPreferenceService
	 */
	public BlogPreferenceService getBlogPreferenceService() {
		return blogPreferenceService;
	}

	/**
	 * @param blogPreferenceService the blogPreferenceService to set
	 */
	public void setBlogPreferenceService(BlogPreferenceService blogPreferenceService) {
		this.blogPreferenceService = blogPreferenceService;
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

	/**
	 * @return the velocityEngine
	 */
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	/**
	 * @param velocityEngine the velocityEngine to set
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	/**
	 * @return the blogLayoutService
	 */
	public BlogLayoutService getBlogLayoutService() {
		return blogLayoutService;
	}

	/**
	 * @param blogLayoutService the blogLayoutService to set
	 */
	public void setBlogLayoutService(BlogLayoutService blogLayoutService) {
		this.blogLayoutService = blogLayoutService;
	}

	/**
	 * @return the viewPostService
	 */
	public ViewPostService getViewPostService() {
		return viewPostService;
	}

	/**
	 * @param viewPostService the viewPostService to set
	 */
	public void setViewPostService(ViewPostService viewPostService) {
		this.viewPostService = viewPostService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
