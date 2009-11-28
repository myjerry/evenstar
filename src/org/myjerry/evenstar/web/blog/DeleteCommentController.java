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
