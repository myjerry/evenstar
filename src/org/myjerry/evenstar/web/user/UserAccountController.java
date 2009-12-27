package org.myjerry.evenstar.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.service.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class UserAccountController extends MultiActionController {
	
	@Autowired
	private BlogUserService blogUserService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(".my.account");
		return mav;
	}

	/**
	 * @return the blogUserService
	 */
	public BlogUserService getBlogUserService() {
		return blogUserService;
	}

	/**
	 * @param blogUserService the blogUserService to set
	 */
	public void setBlogUserService(BlogUserService blogUserService) {
		this.blogUserService = blogUserService;
	}

}
