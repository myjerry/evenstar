package org.myjerry.evenstar.web.settings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.constants.BlogPreferenceConstants;
import org.myjerry.evenstar.constants.DateHeaderConstants;
import org.myjerry.evenstar.service.BlogPreferenceService;
import org.myjerry.util.ServerUtils;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class FormatSettingsController extends MultiActionController {
	
	@Autowired
	private BlogPreferenceService blogPreferenceService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		mav.addObject("blogID", blogID);
		
		String numPosts = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.showNumPosts);
		String dateHeaderFormat = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.postDateHeaderFormat);
		String postTimeStampFormat = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.postTimeStampFormat);
		String postTimeZoneFormat = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.postTimeZoneFormat);
		String convertLineBreaks = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.convertLineBreaks);
		String postTemplate = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.postTemplate);
		
		Map<String, String> dateHeaderFormats = new HashMap<String, String>(); 
		List<String> dateFormats = DateHeaderConstants.dateFormats;
		Date newDate = ServerUtils.getServerDate();
		SimpleDateFormat formatter = null;
		for(String format : dateFormats) {
			formatter = new SimpleDateFormat(format);
			String formattedDate = formatter.format(newDate);
			dateHeaderFormats.put(format, formattedDate);
		}
		
		mav.addObject("dateHeaderFormats", dateHeaderFormats);
		mav.addObject("numPosts", numPosts);
		mav.addObject("dateHeaderFormat", dateHeaderFormat);
		mav.addObject("postTimeStampFormat", postTimeStampFormat);
		mav.addObject("postTimeZoneFormat", postTimeZoneFormat);
		mav.addObject("convertLineBreaks", convertLineBreaks);
		mav.addObject("postTemplate", postTemplate);
		
		mav.setViewName(".admin.settings.format");
		return mav;
	}
	
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		mav.addObject("blogID", blogID);
		
		String numPosts = request.getParameter("numPosts");
		String dateHeaderFormat = request.getParameter("dateHeaderFormat");
		String postTimeStampFormat = request.getParameter("postTimeStampFormat");
		String postTimeZoneFormat = request.getParameter("postTimeZoneFormat");
		String convertLineBreaks = request.getParameter("convertLineBreaks");
		String postTemplate = request.getParameter("postTemplate");
		
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.showNumPosts, numPosts);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.postDateHeaderFormat, dateHeaderFormat);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.postTimeStampFormat, postTimeStampFormat);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.postTimeZoneFormat, postTimeZoneFormat);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.convertLineBreaks, convertLineBreaks);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.postTemplate, postTemplate);

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
