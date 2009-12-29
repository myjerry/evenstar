package org.myjerry.evenstar.web.layout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.service.BlogLayoutService;
import org.myjerry.util.RequestUtil;
import org.myjerry.util.ResponseUtil;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class LayoutController extends MultiActionController {
	
	@Autowired
	private BlogLayoutService blogLayoutService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String blogID = request.getParameter("blogID");
		mav.setViewName(".admin.layout");
		mav.addObject("blogID", blogID);
		mav.addObject("templateCode", this.blogLayoutService.getBlogTemplate(new Long(blogID)));
		return mav;
	}
	
	public ModelAndView clearEdits(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return view(request, response);
	}
	
	public ModelAndView preview(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(".admin.layout");
		mav.addObject("blogID", request.getParameter("blogID"));
		return mav;
	}
	
	public ModelAndView downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		String templateCode = this.blogLayoutService.getBlogTemplate(blogID);
		return ResponseUtil.sendFileToDownload(response, templateCode, "template.vm", "text/plain", RequestUtil.isHttp11(request));
	}
	
	public ModelAndView saveTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String blogID = request.getParameter("blogID");
		String templateCode = request.getParameter("templateCode");
		
		this.blogLayoutService.saveBlogTemplate(new Long(blogID), templateCode);
		
		mav.setViewName(".admin.layout");
		mav.addObject("blogID", blogID);
		mav.addObject("templateCode", templateCode);
		return mav;
	}
	
	/**
	 * @return the blogLayoutService
	 */
	public BlogLayoutService getBlogLayoutService() {
		return blogLayoutService;
	}

	/**
	 * @param blogLayoutService the blogLayoutService to set
	 */
	public void setBlogLayoutService(BlogLayoutService blogLayoutService) {
		this.blogLayoutService = blogLayoutService;
	}

}
