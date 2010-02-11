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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.model.Comment;
import org.myjerry.evenstar.service.CommentService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class DeleteCommentController extends MultiActionController {
	
	@Autowired
	private CommentService commentService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		String postID = request.getParameter("postID");
		String blogID = request.getParameter("blogID");
		String commentID = request.getParameter("commentID");

		Comment comment = this.commentService.getComment(StringUtils.getLong(commentID), StringUtils.getLong(postID), StringUtils.getLong(blogID));
		
		mav.addObject("postID", postID);
		mav.addObject("blogID", blogID);
		mav.addObject("commentID", commentID);
		mav.addObject("comment", comment);
		
		mav.setViewName(".delete.comment");
		return mav;
	}
	
	public ModelAndView deleteComment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String postID = request.getParameter("postID");
		String blogID = request.getParameter("blogID");
		String commentID = request.getParameter("commentID");
		String postURL = request.getParameter("postURL");
		
		boolean result = this.commentService.deleteComment(StringUtils.getLong(commentID), StringUtils.getLong(postID), StringUtils.getLong(blogID));
		if(!result) {
			mav = view(request, response);
		} else {
			mav.addObject("postURL", "/index.html");
		}
		
		mav.addObject("postURL", postURL);
		mav.addObject("result", new Boolean(result));
		mav.setViewName(".delete.comment");
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
	
}
