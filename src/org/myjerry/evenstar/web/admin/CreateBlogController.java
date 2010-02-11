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
package org.myjerry.evenstar.web.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.service.BlogService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class CreateBlogController extends MultiActionController {
	
	@Autowired
	private BlogService blogService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		UserService userService = UserServiceFactory.getUserService();
		if(!userService.isUserAdmin()) {
			mav.setViewName(".noaccess");
		}
		
		mav.setViewName(".admin.create.blog");
		return mav;
	}

	public ModelAndView submit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// check for requisite parameters
		String blogTitle = request.getParameter("blogTitle");
		String blogAddress = request.getParameter("blogAddress");
		String blogAlias = request.getParameter("blogAlias");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName(".admin.create.blog");
		
		List<String> validationErrors = new ArrayList<String>();
		if(StringUtils.isNotBlank(blogTitle) && StringUtils.isNotBlank(blogAddress)) {
			if(this.blogService.existsBlogName(blogTitle)) {
				validationErrors.add("A blog with the same name already exists.");
			}
			if(this.blogService.existsBlogAddress(blogAddress)) {
				validationErrors.add("A blog with the same address already exists.");
			}
			if(this.blogService.existsBlogAlias(blogAlias)) {
				validationErrors.add("A blog with the same alias already exists.");
			}

			if(validationErrors.size() == 0) {
				try {
					boolean result = this.blogService.createBlog(blogTitle, blogAddress, blogAlias);
					if(!result) {
						validationErrors.add("Error creating blog");
					} else {
						mav.setViewName(".admin.create.blog.complete");
					}
				} catch(Exception e) {
					validationErrors.add("Error creating blog");
				}
			}
		} else {
			
		}
		
		mav.addObject("validationErrors", validationErrors);
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
