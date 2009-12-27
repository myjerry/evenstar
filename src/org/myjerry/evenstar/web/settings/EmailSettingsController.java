package org.myjerry.evenstar.web.settings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.constants.BlogPreferenceConstants;
import org.myjerry.evenstar.service.BlogPreferenceService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class EmailSettingsController extends MultiActionController {
	
	@Autowired
	private BlogPreferenceService blogPreferenceService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		mav.addObject("blogID", blogID);
		
		String blogSendAddress = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.blogSendAddress);
		String emailPostingAddress = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.emailPostingAddress);
		
		mav.addObject("blogSendAddress", blogSendAddress);
		mav.addObject("emailPostingAddress", emailPostingAddress);
		
		mav.setViewName(".admin.settings.email");
		return mav;
	}
	
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		mav.addObject("blogID", blogID);
		
		String blogSendAddress = request.getParameter("blogSendAddress");
		String emailPostingAddress = request.getParameter("emailPostingAddress");
		
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.blogSendAddress, blogSendAddress);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.emailPostingAddress, emailPostingAddress);
		
		mav.addObject("blogSendAddress", blogSendAddress);
		mav.addObject("postingSecret", emailPostingAddress);
		
		mav.setViewName(".admin.settings.email");
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
