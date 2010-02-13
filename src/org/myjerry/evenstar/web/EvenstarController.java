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
package org.myjerry.evenstar.web;

import javax.servlet.http.HttpServletRequest;

import org.myjerry.evenstar.constants.EvenstarConstants;
import org.myjerry.evenstar.model.EvenstarUser;
import org.myjerry.evenstar.openid.OpenID;
import org.myjerry.evenstar.service.UserService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.dyuproject.openid.OpenIdUser;

public abstract class EvenstarController extends MultiActionController {
	
	public abstract UserService getUserService();
	
	protected ModelAndView getModelAndView(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		EvenstarUser user = getEvenstarUser(request);
		boolean loggedIn = false;
		if(user != null) {
			mav.addObject(EvenstarConstants.EVENSTAR_MODEL_ATTRIBUTE_NAME, user);
			loggedIn = true;
		}
		mav.addObject("userLoggedIn", loggedIn);
		
		return mav;
	}
	
	protected boolean isUserLoggedIn(HttpServletRequest request) {
		EvenstarUser user = getEvenstarUser(request);
		if(user != null) {
			return true;
		}
		return false;
	}
	
	protected boolean isUserEvenstarAdmin(HttpServletRequest request) {
		EvenstarUser user = getEvenstarUser(request);
		if(user != null) {
			return user.isAdmin();
		}
		return false;
	}
	
	protected boolean isUserHost(HttpServletRequest request) {
		return GAEUserUtil.isCurrentUserHost();
	}
	
	protected OpenIdUser getOpenIdUser(HttpServletRequest request) {
		return OpenID.getUser(request);
	}
	
	protected EvenstarUser getEvenstarUser(HttpServletRequest request) {
		String email = GAEUserUtil.getEmail();
		if(StringUtils.isEmpty(email)) {
			email = OpenID.getUserEmail(request);
		}
		
		if(StringUtils.isNotEmpty(email)) {
			UserService service = getUserService();
			if(service != null) {
				return service.getEvenstarUser(email);
			}
		}
		return null;
	}
	
}
