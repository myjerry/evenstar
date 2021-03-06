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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.myjerry.evenstar.constants.BlogPreferenceConstants;
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
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

public class ViewPostController extends EvenstarController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private BlogPostService blogPostService;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private BlogLayoutService blogLayoutService;
	
	@Autowired
	private ViewPostService viewPostService;
	
	@Autowired
	private BlogPreferenceService blogPreferenceService;
	
	@Autowired
	private UserService userService;
	
	@SuppressWarnings("unchecked")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = getModelAndView(request);

		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		Long postID = StringUtils.getLong(request.getParameter("postID"));
		
		Blog blog = this.blogService.getBlog(blogID);
		BlogPost post = this.blogPostService.getPost(postID, blogID);
		
		String olderPostUrl = this.blogPostService.getOlderPostUrl(blogID, post.getPostedDate());
		String newerPostUrl = this.blogPostService.getNewerPostUrl(blogID, post.getPostedDate());
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("olderPageUrl", olderPostUrl);
		model.put("newerPageUrl", newerPostUrl);
		
		boolean viewComments = StringUtils.getBoolean(this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.showPostComments), true);
		
		model.putAll(mav.getModel());
		model = this.viewPostService.getPostsViewModel(blog, Arrays.asList(post), viewComments, model);
		String generatedBlogPage = TemplateHelper.generateBlogPage(blogID, model, blogLayoutService, velocityEngine);
		
		mav.addAllObjects(model);

		mav.addObject("generatedBlogPage", generatedBlogPage);
		mav.setViewName(".view.blog.home");
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
