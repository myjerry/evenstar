package org.myjerry.evenstar.web.tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ExportBlogController extends MultiActionController {

	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		mav.addObject("blogID", blogID);
		mav.setViewName(".tools.export.blog");
		return mav;
	}
	
	public ModelAndView confirmExportBlog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName(".tools.export.blog");
		return mav;
	}
}
