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
package org.myjerry.evenstar.web.author;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.model.Comment;
import org.myjerry.evenstar.service.CommentService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ModerateCommentsController extends MultiActionController {
	
	@Autowired
	private CommentService commentService;

	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		Collection<Comment> comments = this.commentService.getUnpublishedCommentsForBlog(blogID);
		
		mav.setViewName(".moderate.comments");
		mav.addObject("comments", comments);
		
		return mav;
	}
	
	public ModelAndView publish(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String commentID = request.getParameter("commentID");
		boolean result = false;
		
		if(StringUtils.isNotEmpty(commentID)) {
			String[] tokens = org.apache.commons.lang.StringUtils.split(commentID, ':');
			if(tokens.length == 3) {
				Long comment = StringUtils.getLong(tokens[0]);
				Long post = StringUtils.getLong(tokens[1]);
				Long blog = StringUtils.getLong(tokens[2]);
				
				result = this.commentService.publishComment(comment, post, blog);
			}
		}
		
		ModelAndView mav = view(request, response);

		if(result) {
			mav.addObject("resultString", "Comment was successfully moderated.");
		} else {
			mav.addObject("resultString", "Unable to moderate comment, please try again!");
		}

		return mav;
	}

	public ModelAndView reject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String commentID = request.getParameter("commentID");
		boolean result = false;
		
		if(StringUtils.isNotEmpty(commentID)) {
			String[] tokens = org.apache.commons.lang.StringUtils.split(commentID, ':');
			if(tokens.length == 3) {
				Long comment = StringUtils.getLong(tokens[0]);
				Long post = StringUtils.getLong(tokens[1]);
				Long blog = StringUtils.getLong(tokens[2]);
				
				result = this.commentService.rejectComment(comment, post, blog);
			}
		}
		
		ModelAndView mav = view(request, response);

		if(result) {
			mav.addObject("resultString", "Comment was successfully moderated.");
		} else {
			mav.addObject("resultString", "Unable to moderate comment, please try again!");
		}

		return mav;
	}
	
	public ModelAndView publishSelected(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return view(request, response);
	}

	public ModelAndView rejectSelected(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return view(request, response);
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
