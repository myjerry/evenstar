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
