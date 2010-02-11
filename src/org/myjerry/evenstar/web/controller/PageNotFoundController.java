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
package org.myjerry.evenstar.web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class PageNotFoundController extends SimpleFormController {
	
	@Autowired
	private BlogPostService blogPostService;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// extract the request URI
		// and check to see if this is a blog page URL
		// if yes - go ahead and display the page
		String uri = request.getRequestURI();
		
		BlogPost post = this.blogPostService.getPostForURI(uri);
		if(post != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/viewPost.html?postID=" + post.getPostID() + "&blogID=" + post.getBlogID());
			dispatcher.forward(request, response);
			return null;
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName(".page.not.found");
		return mav; 
	}

	/**
	 * @return the blogPostService
	 */
	public BlogPostService getBlogPostService() {
		return blogPostService;
	}

	/**
	 * @param blogPostService the blogPostService to set
	 */
	public void setBlogPostService(BlogPostService blogPostService) {
		this.blogPostService = blogPostService;
	}
	
}
