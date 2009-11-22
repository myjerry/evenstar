package org.myjerry.evenstar.web.author;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class DeletePostController extends MultiActionController {
	
	@Autowired
	private BlogPostService blogPostService;

	/**
	 * User action to delete the given post.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(".author.deletePost");
		
		String blogID = request.getParameter("blogID");
		String postID = request.getParameter("postID");
		
		BlogPost post = this.blogPostService.getPost(new Long(postID), new Long(blogID));
		
		mav.addObject("blogID", blogID);
		mav.addObject("postID", postID);
		mav.addObject("post", post);
		
		return mav;
	}
	
	public ModelAndView confirmedDeletePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;

		String blogID = request.getParameter("blogID");
		String postID = request.getParameter("postID");
		boolean result = this.blogPostService.deletePost(new Long(postID), new Long(blogID));
		if(result) {
			response.sendRedirect("/author/editPosts.html?blogID=" + blogID);
		} else {
			mav = new ModelAndView();
			mav.addObject("errorString", "Unable to delete the post");
			mav.setViewName(".author.deletePost");
		}
		// delete the post
		// if successful redirect to editPosts.html
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
