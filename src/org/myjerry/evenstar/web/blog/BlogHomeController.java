package org.myjerry.evenstar.web.blog;

import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.service.BlogLayoutService;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.evenstar.service.BlogService;
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
	private VelocityEngine velocityEngine;
	
	@Autowired
	private BlogLayoutService blogLayoutService;

	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		Long defaultBlogID = this.blogService.getDefaultBlogID();
		if(defaultBlogID == null) {
			mav.setViewName(".evenstar");
		} else {
			Collection<BlogPost> posts = this.blogPostService.getBlogPosts(defaultBlogID, 10);
			
			String template = this.blogLayoutService.getBlogTemplate(defaultBlogID);
			if(StringUtils.isEmpty(template)) {
				// error, but we can't do anything here
			}
			template = "#foreach ($post in $posts) $post.title <br /> #end";
			
			Map<String, Object> model = new HashMap<String, Object>();
			
			model.put("posts", posts);
			
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
}
