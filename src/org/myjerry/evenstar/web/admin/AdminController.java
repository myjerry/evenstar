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
package org.myjerry.evenstar.web.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.model.Blog;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.evenstar.service.CommentService;
import org.myjerry.evenstar.view.BlogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class AdminController extends MultiActionController {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private BlogPostService blogPostService;
	
	@Autowired
	private CommentService commentService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Collection<Blog> blogs = this.blogService.getAllBlogs();
		Long defaultBlogID = this.blogService.getDefaultBlogID();
		
		List<BlogInfo> list = new ArrayList<BlogInfo>();

		for(Blog blog : blogs) {
			// check for the default blog
			if(defaultBlogID != null && blog.getBlogID().equals(defaultBlogID)) {
				mav.addObject("defaultBlog", blog);
			}
			
			BlogInfo blogInfo = new BlogInfo(blog);

			// also populate the number of posts in the blog
			Long numPosts = this.blogPostService.getTotalPosts(blog.getBlogID());
			blogInfo.setNumPosts(numPosts);
			
			// get last published date
			Date lastPublishedDate = this.blogPostService.getLastPublishedPostDate(blog.getBlogID());
			blogInfo.setLastPublishedDate(lastPublishedDate);
			
			// the number of unpublished comments
			Long unpublishedComments = this.commentService.getNumUnpublishedCommentsForBlog(blog.getBlogID());
			blogInfo.setUnpublishedComments(unpublishedComments);
			
			list.add(blogInfo);
		}
		
		mav.setViewName(".admin");
		mav.addObject("blogs", list);
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
	 * @return the commentService
	 */
	public CommentService getCommentService() {
		return commentService;
	}

	/**
	 * @param commentService the commentService to set
	 */
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
}
