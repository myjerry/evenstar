package org.myjerry.evenstar.web.settings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.constants.BlogPreferenceConstants;
import org.myjerry.evenstar.service.BlogPreferenceService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class FeedSettingsController extends MultiActionController {
	
	@Autowired
	private BlogPreferenceService blogPreferenceService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		mav.addObject("blogID", blogID);
		
		String feedType = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.feedType);
		String feedRedirectURL = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.feedRedirectURL);
		String postFeedFooter = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.postFeedFooter);

		mav.addObject("feedType", feedType);
		mav.addObject("feedRedirectURL", feedRedirectURL);
		mav.addObject("postFeedFooter", postFeedFooter);
		
		mav.setViewName(".admin.settings.feed");
		return mav;
	}
	
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		mav.addObject("blogID", blogID);
		
		String feedType = request.getParameter("feedType");
		String feedRedirectURL = request.getParameter("feedRedirectURL");
		String postFeedFooter = request.getParameter("postFeedFooter");

		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.feedType, feedType);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.feedRedirectURL, feedRedirectURL);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.postFeedFooter, postFeedFooter);
		
		mav.addObject("feedType", feedType);
		mav.addObject("feedRedirectURL", feedRedirectURL);
		mav.addObject("postFeedFooter", postFeedFooter);
		
		mav.setViewName(".admin.settings.feed");
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
