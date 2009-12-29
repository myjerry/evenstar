package org.myjerry.evenstar.web.settings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.constants.BlogPreferenceConstants;
import org.myjerry.evenstar.service.BlogPreferenceService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ArchiveSettingsController extends MultiActionController {
	
	@Autowired
	private BlogPreferenceService blogPreferenceService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		mav.addObject("blogID", blogID);
		
		String archiveFrequency = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.archiveFrequency);
		String postPages = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.postPages);
		String archiveIndexFormat = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.archiveIndexFormat);

		mav.addObject("archiveFrequency", archiveFrequency);
		mav.addObject("postPages", postPages);
		mav.addObject("archiveIndexFormat", archiveIndexFormat);
		
		mav.setViewName(".admin.settings.archives");
		return mav;
	}
	
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		mav.addObject("blogID", blogID);

		String archiveFrequency = request.getParameter("archiveFrequency");
		String postPages = request.getParameter("postPages");
		String archiveIndexFormat = request.getParameter("archiveIndexFormat");
		
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.archiveFrequency, archiveFrequency);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.postPages, postPages);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.archiveIndexFormat, archiveIndexFormat);

		mav.addObject("archiveFrequency", archiveFrequency);
		mav.addObject("postPages", postPages);
		mav.addObject("archiveIndexFormat", archiveIndexFormat);
		
		mav.setViewName(".admin.settings.archives");
		return mav;
	}
	
	public ModelAndView reBuild(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return view(request, response);
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
