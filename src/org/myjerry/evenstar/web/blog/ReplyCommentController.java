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
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ReplyCommentController extends MultiActionController {
	
	@Autowired
	private CommentService commentService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		String postID = request.getParameter("postID");
		String blogID = request.getParameter("blogID");
		String parentID = request.getParameter("commentID");

		Comment comment = this.commentService.getComment(StringUtils.getLong(parentID), StringUtils.getLong(postID), StringUtils.getLong(blogID));
		
		mav.addObject("postID", postID);
		mav.addObject("blogID", blogID);
		mav.addObject("parentID", parentID);
		mav.addObject("comment", comment);
		
		mav.setViewName(".reply.comment");
		return mav;
	}
	
	public ModelAndView postReply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String postID = request.getParameter("postID");
		String blogID = request.getParameter("blogID");
		String parentID = request.getParameter("parentID");
		String thoughts = request.getParameter("thoughts");
		
		Comment parentComment = this.commentService.getComment(StringUtils.getLong(parentID), StringUtils.getLong(postID), StringUtils.getLong(blogID));

		Comment comment = new Comment();
		comment.setAuthorID(StringUtils.getLong(GAEUserUtil.getUserID()));
		comment.setBlogID(StringUtils.getLong(blogID));
		comment.setContent(thoughts);
		comment.setPostID(StringUtils.getLong(postID));
		comment.setParentID(StringUtils.getLong(parentID));
		comment.setPermissions(parentComment.getPermissions());
		
		boolean result = this.commentService.postComment(comment);
		if(!result) {
			mav = view(request, response);
			mav.addObject("thoughts", thoughts);
			mav.addObject("parentID", parentID);
		}
		
		mav.addObject("result", new Boolean(result));
		mav.setViewName(".post.comments");
		return mav;
	}

	public ModelAndView preview(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(".post.comments");
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
