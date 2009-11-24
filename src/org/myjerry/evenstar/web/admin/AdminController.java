package org.myjerry.evenstar.web.admin;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.model.Blog;
import org.myjerry.evenstar.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class AdminController extends MultiActionController {

	@Autowired
	private BlogService blogService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Collection<Blog> blogs = this.blogService.getAllBlogs();
		Long defaultBlogID = this.blogService.getDefaultBlogID();
		if(defaultBlogID != null) {
			for(Blog blog : blogs) {
				if(blog.getBlogID().equals(defaultBlogID)) {
					mav.addObject("defaultBlog", blog);
				}
			}
		}		
		
		mav.setViewName(".admin");
		mav.addObject("blogs", blogs);
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
}
