package org.myjerry.evenstar.web.author;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class EditPostsController extends MultiActionController {
	
	@Autowired
	private BlogPostService blogPostService;

	/**
	 * User action requesting to view all posts given in a blog.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = Long.parseLong(request.getParameter("blogID"));
		Collection<BlogPost> posts = this.blogPostService.getAllBlogPosts(blogID);
		
		mav.setViewName(".author.editposts");
		mav.addObject("blogID", String.valueOf(blogID));
		mav.addObject("posts", posts);
		return mav;
	}
	
	/**
	 * User action to edit the given post
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView editPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long postID = Long.parseLong(request.getParameter("postID"));
		Long blogID = Long.parseLong(request.getParameter("blogID"));
		BlogPost post = this.blogPostService.getPost(postID, blogID);
		mav.addObject("post", post);
		
		mav.setViewName(".author.newpost");
		return mav;
	}

	/**
	 * User action to delete the given post.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView deletePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
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
