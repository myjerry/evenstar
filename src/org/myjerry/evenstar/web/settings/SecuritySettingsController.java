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
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.evenstar.service.CommentService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class SecuritySettingsController extends MultiActionController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CommentService commentService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		Collection<AutoApprovedCommentor> autoApprovals = this.commentService.getAutoApproveCommentors(blogID);
		Collection<BannedCommentor> banned = this.commentService.getAutoRejectCommentors(blogID);
		
		mav.addObject("autoApprovals", autoApprovals);
		mav.addObject("banned", banned);
		mav.addObject("blogID", blogID);
		mav.setViewName(".admin.settings.security");
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
