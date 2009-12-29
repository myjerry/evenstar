package org.myjerry.evenstar.web.blog;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.myjerry.evenstar.helper.TemplateHelper;
import org.myjerry.evenstar.model.Blog;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.service.BlogLayoutService;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.evenstar.service.ViewPostService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ViewPostController extends MultiActionController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private BlogPostService blogPostService;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private BlogLayoutService blogLayoutService;
	
	@Autowired
	private ViewPostService viewPostService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		Long postID = StringUtils.getLong(request.getParameter("postID"));
		
		Blog blog = this.blogService.getBlog(blogID);
		BlogPost post = this.blogPostService.getPost(postID, blogID);
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		model = this.viewPostService.getPostsViewModel(blog, Arrays.asList(post), true, model);
		String generatedBlogPage = TemplateHelper.generateBlogPage(blogID, model, blogLayoutService, velocityEngine);
		
		mav.addAllObjects(model);

		mav.addObject("generatedBlogPage", generatedBlogPage);
		mav.setViewName(".view.blog.home");
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
	 * @return the viewPostService
	 */
	public ViewPostService getViewPostService() {
		return viewPostService;
	}

	/**
	 * @param viewPostService the viewPostService to set
	 */
	public void setViewPostService(ViewPostService viewPostService) {
		this.viewPostService = viewPostService;
	}

}
