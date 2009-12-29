package org.myjerry.evenstar.web.author;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.constants.BlogPreferenceConstants;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.service.BlogPreferenceService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

public class NewPostController extends PostController {
	
	@Autowired
	private BlogPreferenceService blogPreferenceService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		
		mav.setViewName(".author.newpost");
		
		mav.addObject("blogID", blogID);
		mav.addObject("privacyMode", new Integer(BlogPost.PRIVACY_MODE_PUBLIC));
		
		String postTemplate = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.postTemplate);
		if(StringUtils.isNotEmpty(postTemplate)) {
			BlogPost post = new BlogPost();
			post.setContents(postTemplate);
			mav.addObject("post", post);
		}
		
		return mav;
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
