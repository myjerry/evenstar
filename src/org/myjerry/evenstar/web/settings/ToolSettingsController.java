package org.myjerry.evenstar.web.settings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.constants.BlogPreferenceConstants;
import org.myjerry.evenstar.service.BlogPreferenceService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ToolSettingsController extends MultiActionController {
	
	@Autowired
	private BlogPreferenceService blogPreferenceService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		mav.addObject("blogID", blogID);
		
		String googleAnalytics = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.toolGoogleAnalytics);
		String googleWebmaster = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.toolGoogleWebMaster);
		String yahooSiteExplorer = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.toolYahooSiteExplorer);
		String bingWebmaster = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.toolBingWebMaster);
		
		mav.addObject("googleAnalytics", googleAnalytics);
		mav.addObject("googleWebmaster", googleWebmaster);
		mav.addObject("yahooSiteExplorer", yahooSiteExplorer);
		mav.addObject("bingWebmaster", bingWebmaster);
		
		mav.setViewName(".admin.settings.tools");
		return mav;
	}
	
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		mav.addObject("blogID", blogID);
		
		String googleAnalytics = request.getParameter("googleAnalytics");
		String googleWebmaster = request.getParameter("googleWebmaster");
		String yahooSiteExplorer = request.getParameter("yahooSiteExplorer");
		String bingWebmaster = request.getParameter("bingWebmaster");
		
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.toolGoogleAnalytics, googleAnalytics);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.toolGoogleWebMaster, googleWebmaster);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.toolYahooSiteExplorer, yahooSiteExplorer);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.toolBingWebMaster, bingWebmaster);
		
		mav.addObject("feedType", googleAnalytics);
		mav.addObject("feedType", googleWebmaster);
		mav.addObject("feedType", yahooSiteExplorer);
		mav.addObject("feedType", bingWebmaster);
		
		
		mav.setViewName(".admin.settings.tools");
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
