package org.myjerry.evenstar.web.blog;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.model.Blog;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.model.Comment;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.evenstar.service.CommentService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class PostCommentController extends MultiActionController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private BlogPostService blogPostService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		String postID = request.getParameter("postID");
		String blogID = request.getParameter("blogID");
		String parentID = request.getParameter("parentID");

		Blog blog = this.blogService.getBlog(StringUtils.getLong(blogID));
		BlogPost post = this.blogPostService.getPost(StringUtils.getLong(postID), StringUtils.getLong(blogID));
		Collection<Comment> comments = this.commentService.getCommentsForPost(StringUtils.getLong(postID), StringUtils.getLong(blogID), 20);
		
		mav.addObject("postID", postID);
		mav.addObject("blogID", blogID);
		mav.addObject("blog", blog);
		mav.addObject("post", post);
		mav.addObject("parentID", parentID);
		mav.addObject("comments", comments);
		mav.addObject("numComments", comments.size());
		
		mav.setViewName(".post.comments");
		return mav;
	}
	
	public ModelAndView postComment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String postID = request.getParameter("postID");
		String blogID = request.getParameter("blogID");
		String parentID = request.getParameter("parentID");
		String thoughts = request.getParameter("thoughts");
		String postURL = request.getParameter("postURL");
		String privacy = request.getParameter("privacy");
		
		Comment comment = new Comment();
		comment.setAuthorID(StringUtils.getLong(GAEUserUtil.getUserID()));
		comment.setBlogID(StringUtils.getLong(blogID));
		comment.setContent(thoughts);
		comment.setPostID(StringUtils.getLong(postID));
		comment.setParentID(StringUtils.getLong(parentID));
		
		if("private".equalsIgnoreCase(privacy)) {
			comment.setPermissions(Comment.PRIVACY_MODE_PRIVATE);
		} else {
			comment.setPermissions(Comment.PRIVACY_MODE_PUBLIC);
		}
		
		boolean result = this.commentService.postComment(comment);
		if(!result) {
			mav = view(request, response);
			mav.addObject("thoughts", thoughts);
		}
		
		mav.addObject("postURL", postURL);
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
	
}
