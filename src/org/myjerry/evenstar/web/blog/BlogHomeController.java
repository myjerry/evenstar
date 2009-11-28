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
import org.myjerry.evenstar.model.Blog;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.service.BlogLayoutService;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.evenstar.service.CommentService;
import org.myjerry.evenstar.view.BlogInfo;
import org.myjerry.evenstar.view.BlogPostInfo;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class BlogHomeController extends MultiActionController {
	
	@Autowired
	private BlogService blogService;
	
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
		Long defaultBlogID = this.blogService.getDefaultBlogID();
		if(defaultBlogID == null) {
			mav.setViewName(".evenstar");
		} else {
			Blog blog = this.blogService.getBlog(defaultBlogID);
			Collection<BlogPost> posts = this.blogPostService.getBlogPosts(defaultBlogID, 10);
			Collection<BlogPostInfo> list = new ArrayList<BlogPostInfo>();
			if(posts != null) {
				for(BlogPost post : posts) {
					BlogPostInfo p = new BlogPostInfo(post);
					p.setNumComments(this.commentService.getTotalCommentsOnPost(p.getId(), defaultBlogID));
					list.add(p);
				}
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("blog", new BlogInfo(blog));
			model.put("title", blog.getTitle());
			model.put("description", blog.getDescription());
			model.put("posts", list);
			
			String template = this.blogLayoutService.getBlogTemplate(defaultBlogID);
			if(StringUtils.isEmpty(template)) {
				template = this.blogLayoutService.getDefaultBlogTemplate();
			}

			StringWriter result = new StringWriter();
			this.velocityEngine.evaluate(new VelocityContext(model), result, "log string", template);
			String generatedBlogPage = result.toString();
			
			mav.addAllObjects(model);
			mav.addObject("generatedBlogPage", generatedBlogPage);
			mav.setViewName(".view.blog.home");
		}
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
