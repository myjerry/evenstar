package org.myjerry.evenstar.web.settings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.constants.BlogPreferenceConstants;
import org.myjerry.evenstar.model.Blog;
import org.myjerry.evenstar.service.BlogPreferenceService;
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class BlogSettingsController extends MultiActionController {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private BlogPreferenceService blogPreferenceService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(".admin.settings");
		
		String blogID = request.getParameter("blogID");
		Blog blog = this.blogService.getBlog(StringUtils.getLong(blogID));
		mav.addObject("blog", blog);
		
		Boolean quickEditing = StringUtils.getBoolean(this.blogPreferenceService.getPreference(blog.getBlogID(), BlogPreferenceConstants.quickEditing), true);
		Boolean emailPostLinks = StringUtils.getBoolean(this.blogPreferenceService.getPreference(blog.getBlogID(), BlogPreferenceConstants.emailLinks), true);

		mav.addObject("quickEditing", quickEditing);
		mav.addObject("emailPostLinks", emailPostLinks);
		
		return mav;
	}
	
	public ModelAndView updateSettings(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;

		String blogID = request.getParameter("blogID");
		
		String quickEditing = request.getParameter("quickEditing");
		String emailLinks = request.getParameter("emailPostLinks");
		
		boolean result1 = false, result2 = false, result3 = false;

		Blog blog = this.blogService.getBlog(StringUtils.getLong(blogID));
		if(blog != null) {
			blog.setTitle(request.getParameter("blogTitle"));
			blog.setDescription(request.getParameter("description"));
			blog.setAddress(request.getParameter("blogAddress"));
			blog.setAlias(request.getParameter("blogAlias"));
			blog.setRestrictedPostText(request.getParameter("restrictedPostText"));
			
			result1 = this.blogService.updateBlog(blog);
			result2 = this.blogPreferenceService.putPreference(blog.getBlogID(), BlogPreferenceConstants.quickEditing, StringUtils.getBoolean(quickEditing, true).toString());
			result3 = this.blogPreferenceService.putPreference(blog.getBlogID(), BlogPreferenceConstants.emailLinks, StringUtils.getBoolean(emailLinks, true).toString());

		}

		mav = view(request, response);
		if(!result1 || !result2 || !result3) {
			mav.addObject("errorString", "Unable to update the blog.");
		}
		
		return mav;
	}

	/**
	 * @return the blogService
	 */
	public BlogService getBlogService() {
		return blogService;
	}

	/**
	 * @param blogService the blogService to set
	 */
	public void setBlogService(BlogService blogService) {
		this.blogService = blogService;
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
