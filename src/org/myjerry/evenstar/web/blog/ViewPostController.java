package org.myjerry.evenstar.web.blog;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.myjerry.evenstar.constants.BlogPreferenceConstants;
import org.myjerry.evenstar.helper.ViewPostHelper;
import org.myjerry.evenstar.model.Blog;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.model.Comment;
import org.myjerry.evenstar.service.BlogLayoutService;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.evenstar.service.BlogPreferenceService;
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.evenstar.service.BlogUserService;
import org.myjerry.evenstar.service.CommentService;
import org.myjerry.evenstar.view.BlogInfo;
import org.myjerry.evenstar.view.BlogPostInfo;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ViewPostController extends MultiActionController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private BlogPreferenceService blogPreferenceService;
	
	@Autowired
	private BlogPostService blogPostService;
	
	@Autowired
	private BlogUserService blogUserService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private BlogLayoutService blogLayoutService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		Long postID = StringUtils.getLong(request.getParameter("postID"));
		
		Blog blog = this.blogService.getBlog(blogID);
		BlogPost post = this.blogPostService.getPost(postID, blogID);
		
		String dateHeaderFormat = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.postDateHeaderFormat);
		String commentTimeStampFormat = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.commentTimeStampFormat);
		
		Collection<BlogPostInfo> list = new ArrayList<BlogPostInfo>();
		// get the post
		BlogPostInfo p = ViewPostHelper.getBlogPostInfo(post, dateHeaderFormat, blog.getBlogID(), blog.getRestrictedPostText(), this.blogUserService, this.commentService);
		if(p.isRestricted()) {
			// get the comments
			Collection<Comment> comments = this.commentService.getCommentsForPost(postID, blogID, 1000);
			p.setComments(ViewPostHelper.getCommentList(comments, blogID, commentTimeStampFormat, this.blogUserService));
		}
		list.add(p);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("blog", new BlogInfo(blog));
		model.put("title", blog.getTitle());
		model.put("description", blog.getDescription());
		model.put("posts", list);
		model.put("showComments", true);
		
		String template = this.blogLayoutService.getBlogTemplate(blogID);
		if(StringUtils.isEmpty(template)) {
			template = this.blogLayoutService.getDefaultBlogTemplate();
		}

		StringWriter result = new StringWriter();
		this.velocityEngine.evaluate(new VelocityContext(model), result, "log string", template);
		String generatedBlogPage = result.toString();
		
		mav.addAllObjects(model);

		mav.addObject("generatedBlogPage", generatedBlogPage);
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

	/**
	 * @return the blogPreferenceService
	 */
	public BlogPreferenceService getBlogPreferenceService() {
		return blogPreferenceService;
	}

	/**
	 * @param blogPreferenceService the blogPreferenceService to set
	 */
	public void setBlogPreferenceService(BlogPreferenceService blogPreferenceService) {
		this.blogPreferenceService = blogPreferenceService;
	}

}
