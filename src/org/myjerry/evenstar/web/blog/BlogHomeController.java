package org.myjerry.evenstar.web.blog;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.evenstar.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class BlogHomeController extends MultiActionController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private BlogPostService blogPostService;

	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		Long defaultBlogID = this.blogService.getDefaultBlogID();
		if(defaultBlogID == null) {
			mav.setViewName(".evenstar");
		} else {
			Collection<BlogPost> posts = this.blogPostService.getBlogPosts(defaultBlogID, 10);
			mav.addObject("posts", posts);
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
}
