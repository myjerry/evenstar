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
