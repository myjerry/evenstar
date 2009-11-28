package org.myjerry.evenstar.web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class PageNotFoundController extends SimpleFormController {
	
	@Autowired
	private BlogPostService blogPostService;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// extract the request URI
		// and check to see if this is a blog page URL
		// if yes - go ahead and display the page
		String uri = request.getRequestURI();
		
		BlogPost post = this.blogPostService.getPostForURI(uri);
		if(post != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/viewPost.html?postID=" + post.getPostID() + "&blogID=" + post.getBlogID());
			dispatcher.forward(request, response);
			return null;
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName(".page.not.found");
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
	
}
