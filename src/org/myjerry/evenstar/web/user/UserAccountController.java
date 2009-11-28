package org.myjerry.evenstar.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.service.EvenstarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class UserAccountController extends MultiActionController {
	
	@Autowired
	private EvenstarUserService userService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(".my.account");
		return mav;
	}

	/**
	 * @return the userService
	 */
	public EvenstarUserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(EvenstarUserService userService) {
		this.userService = userService;
	}

}
