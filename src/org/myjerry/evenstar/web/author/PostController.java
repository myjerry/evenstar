package org.myjerry.evenstar.web.author;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class PostController extends MultiActionController {

	@Autowired
	private BlogPostService blogPostService;
	
	/**
	 * User action to edit the given post
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long postID = Long.parseLong(request.getParameter("postID"));
		Long blogID = Long.parseLong(request.getParameter("blogID"));
		BlogPost post = this.blogPostService.getPost(postID, blogID);

		mav.addObject("post", post);
		mav.addObject("blogID", blogID);
		mav.addObject("postID", postID);
		mav.addObject("privacyMode", post.getPrivacyMode());
		mav.setViewName(".author.newpost");
		
		return mav;
	}

	public ModelAndView saveAsDraft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> validationErrors = validateRequest(request);

		ModelAndView mav = new ModelAndView();
		if(validationErrors.size() == 0) {
			BlogPost post = contructBlogPostFromRequest(request);
			
			boolean result = this.blogPostService.saveDraftPost(post);
			if(!result) {
				mav.addObject("errorString", "Unable to save the post to draft mode");
			}
			mav.addObject("post", post);
			mav.addObject("blogID", post.getBlogID());
			mav.addObject("postID", post.getPostID());
			mav.addObject("privacyMode", post.getPrivacyMode());
		}
		mav.setViewName(".author.newpost");	
		return mav;
	}
	
	public ModelAndView publishPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> validationErrors = validateRequest(request);

		ModelAndView mav = new ModelAndView();
		if(validationErrors.size() == 0) {
			BlogPost post = contructBlogPostFromRequest(request);

			boolean result = this.blogPostService.publishPost(post);

			mav.addObject("blogID", post.getBlogID());
			mav.addObject("postID", post.getPostID());
			mav.addObject("privacyMode", post.getPrivacyMode());
			
			if(result) {
				mav.setViewName(".author.newpost.published");	
			} else {
				mav.setViewName(".author.newpost");
			}
		} else {
			mav.setViewName(".author.newpost");
		}
		return mav;
	}

	private List<String> validateRequest(HttpServletRequest request) {
		return new ArrayList<String>();
	}
	
	private BlogPost contructBlogPostFromRequest(HttpServletRequest request) {
		BlogPost post = new BlogPost();
		
		String postID = request.getParameter("postID");
		if(StringUtils.isNotEmpty(postID)) {
			post.setPostID(new Long(postID));
		}
		post.setBlogID(new Long(request.getParameter("blogID")));
		post.setTitle(request.getParameter("postTitle"));
		post.setContents(request.getParameter("postContents"));
		String labels = request.getParameter("labels");
		if(StringUtils.isEmpty(labels)) {
			post.setLabels("");
		} else {
			post.setLabels(labels);
		}
		
		String privacy = request.getParameter("privacy");
		if(StringUtils.isNotEmpty(privacy)) {
			privacy = privacy.toLowerCase();
			if("public".equals(privacy)) {
				post.setPrivacyMode(BlogPost.PRIVACY_MODE_PUBLIC);
			} else if("private".equals(privacy)) {
				post.setPrivacyMode(BlogPost.PRIVACY_MODE_PRIVATE);
			} else if("protected".equals(privacy)) {
				post.setPrivacyMode(BlogPost.PRIVACY_MODE_RESTRICTED);
			} else if("custom".equals(privacy)) {
				post.setPrivacyMode(BlogPost.PRIVACY_MODE_CUSTOM);
			} else {
				// something is wrong - this should have been caught in validation
			}
		}
		
		return post;
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
