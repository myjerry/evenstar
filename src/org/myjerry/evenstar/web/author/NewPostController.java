package org.myjerry.evenstar.web.author;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class NewPostController extends PostController {
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(".author.newpost");
		mav.addObject("blogID", request.getParameter("blogID"));
		return mav;
	}

}
