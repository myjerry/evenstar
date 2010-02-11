/**
 * myJerry | Evenstar
 * Copyright (C) 2010 myJerry Development Team
 * http://www.myjerry.org
 * 
 * The file is licensed under the the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
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
			boolean success = false;
			try {
				this.blogService.importBlog(blogID, BlogImportType.BLOGGER, blog, true);
				success = true;
			} catch(Throwable t) {
				mav.addObject("importErrors", t);
			}
			
			if(success) {
				mav.setViewName(".tools.import.blog.success");
				return mav;
			}
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
