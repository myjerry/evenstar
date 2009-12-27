package org.myjerry.evenstar.web.blog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.service.BlogEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class EmailPostController extends MultiActionController {
	
	@Autowired
	private BlogEmailService blogEmailService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(".email.post");
		return mav;
	}
	
	public ModelAndView sendMail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
	/**
	 * @return the blogEmailService
	 */
	public BlogEmailService getBlogEmailService() {
		return blogEmailService;
	}

	/**
	 * @param blogEmailService the blogEmailService to set
	 */
	public void setBlogEmailService(BlogEmailService blogEmailService) {
		this.blogEmailService = blogEmailService;
	}

}
