package org.myjerry.evenstar.web.blog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.myjerry.evenstar.service.BlogLayoutService;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.evenstar.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ViewPostController extends MultiActionController {
	
	@Autowired
	private BlogPostService blogPostService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private BlogLayoutService blogLayoutService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("generatedBlogPage", "Viewing page");
		mav.setViewName(".view.blog.home");
		return mav;
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

}
