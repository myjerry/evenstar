package org.myjerry.evenstar.web.tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.enums.BlogImportType;
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ImportBlogController extends MultiActionController {
	
	@Autowired
	private BlogService blogService;

	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Long blogID = StringUtils.getLong(request.getParameter("blogID"));
		
		mav.setViewName(".tools.import.blog");
		mav.addObject("blogID", blogID);
		return mav;
	}

	public ModelAndView confirmImportBlog(HttpServletRequest request, HttpServletResponse response, ImportBlogCommand command) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		ImportBlogCommand uploadBean = (ImportBlogCommand) command;
		
		if(uploadBean.getBlogXMLFile() != null) {
			String blog = new String(uploadBean.getBlogXMLFile().getBytes());
			Long blogID = StringUtils.getLong(request.getParameter("blogID"));
			this.blogService.importBlog(blogID, BlogImportType.BLOGGER, blog, true);
		}
		
		mav.setViewName(".tools.import.blog");
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
	

}
