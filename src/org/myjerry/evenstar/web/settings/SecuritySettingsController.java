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
package org.myjerry.evenstar.web.settings;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.model.AutoApprovedCommentor;
import org.myjerry.evenstar.model.BannedCommentor;
import org.myjerry.evenstar.model.BlogAuthor;
import org.myjerry.evenstar.model.BlogReader;
import org.myjerry.evenstar.service.BlogUserService;
import org.myjerry.evenstar.service.CommentService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class SecuritySettingsController extends MultiActionController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private BlogUserService blogUserService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		
		Collection<BlogAuthor> authors = this.blogUserService.getBlogAuthors(blogID);
		Collection<BlogReader> readers = this.blogUserService.getBlogReaders(blogID);
		Collection<AutoApprovedCommentor> autoApprovals = this.commentService.getAutoApproveCommentors(blogID);
		Collection<BannedCommentor> banned = this.commentService.getAutoRejectCommentors(blogID);
		
		mav.addObject("authors", authors);
		mav.addObject("readers", readers);
		mav.addObject("autoApprovals", autoApprovals);
		mav.addObject("banned", banned);
		
		mav.addObject("blogID", blogID);
		mav.setViewName(".admin.settings.security");
		return mav;
	}
	
	public ModelAndView addAuthors(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String blogAuthors = request.getParameter("blogAuthor");
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		
		if(StringUtils.isNotEmpty(blogAuthors)) {
			String[] authors = StringUtils.split(blogAuthors, ",;");
			if(authors != null && authors.length > 0) {
				for(String author : authors) {
					this.blogUserService.addBlogAuthor(author, blogID);
				}
			}
		}
		
		ModelAndView mav = view(request, response);
		return mav;
	}

	public ModelAndView addReaders(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String blogReaders = request.getParameter("blogReader");
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		
		if(StringUtils.isNotEmpty(blogReaders)) {
			String[] readers = StringUtils.split(blogReaders, ",;");
			if(readers != null && readers.length > 0) {
				for(String reader : readers) {
					this.blogUserService.addBlogReader(reader, blogID);
				}
			}
		}
		
		ModelAndView mav = view(request, response);
		return mav;
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

	/**
	 * @return the blogUserService
	 */
	public BlogUserService getBlogUserService() {
		return blogUserService;
	}

	/**
	 * @param blogUserService the blogUserService to set
	 */
	public void setBlogUserService(BlogUserService blogUserService) {
		this.blogUserService = blogUserService;
	}

}
