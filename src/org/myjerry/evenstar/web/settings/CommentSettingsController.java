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

public class CommentSettingsController extends MultiActionController {
	
	@Autowired
	private BlogPreferenceService blogPreferenceService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		mav.addObject("blogID", blogID);
		
		String showComments = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.showPostComments);
		String whoCanComment = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.whoCanComment);
		String commentFormPlacement = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.commentFormPlacement);
		String commentsOnNewPosts = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.commentsInNewPosts);
		String commentTimeStampFormat = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.commentTimeStampFormat);
		String commentModeration = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.commentModeration);
		String commentCaptcha = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.commentCaptcha);
		String commentNotificationMail = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.commentNotificationMail);
		String showBackLinks = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.showBackLinks);
		String backLinksOnNewPosts = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.backLinksInNewPosts);
		
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
		mav.addObject("commentTimeStampFormat", commentTimeStampFormat);
		
		mav.addObject("showComments", showComments);
		mav.addObject("whoCanComment", whoCanComment);
		mav.addObject("commentFormPlacement", commentFormPlacement);
		mav.addObject("commentsOnNewPosts", commentsOnNewPosts);
		mav.addObject("commentModeration", commentModeration);
		mav.addObject("commentCaptcha", commentCaptcha);
		mav.addObject("commentNotificationMail", commentNotificationMail);
		mav.addObject("showBackLinks", showBackLinks);
		mav.addObject("backLinksOnNewPosts", backLinksOnNewPosts);
		
		mav.setViewName(".admin.settings.comments");
		return mav;
	}
	
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));

		String showComments = request.getParameter("showComments");
		String whoCanComment = request.getParameter("whoCanComment");
		String commentFormPlacement = request.getParameter("commentFormPlacement");
		String commentsOnNewPosts = request.getParameter("commentsOnNewPosts");
		String commentTimeStampFormat = request.getParameter("commentTimeStampFormat");
		String commentModeration = request.getParameter("commentModeration");
		String commentCaptcha = request.getParameter("commentCaptcha");
		String commentNotificationMail = request.getParameter("commentNotificationMail");
		String showBackLinks = request.getParameter("showBackLinks");
		String backLinksOnNewPosts = request.getParameter("backLinksOnNewPosts");
		
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.showPostComments, showComments);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.whoCanComment, whoCanComment);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.commentFormPlacement, commentFormPlacement);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.commentsInNewPosts, commentsOnNewPosts);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.commentTimeStampFormat, commentTimeStampFormat);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.commentModeration, commentModeration);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.commentCaptcha, commentCaptcha);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.commentNotificationMail, commentNotificationMail);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.showBackLinks, showBackLinks);
		this.blogPreferenceService.putPreference(blogID, BlogPreferenceConstants.backLinksInNewPosts, backLinksOnNewPosts);
		
		ModelAndView mav = view(request, response);
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
