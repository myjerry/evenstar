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
package org.myjerry.evenstar.filters;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.myjerry.evenstar.model.EvenstarUser;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GoogleAdminAuthenticationFilter implements Filter {

	@Override
	public void destroy() {
		// do nothing
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		// check if the user is logged in and is a GOOGLE ADMIN USER
		UserService service = UserServiceFactory.getUserService();

		if(service != null) {
			if(service.isUserLoggedIn() && service.isUserAdmin()) {
				User user = service.getCurrentUser();
				
				// check if this user is already present as an EVENSTAR ADMIN
				// first check if we have the session attribute set - if yes, skip the DB check
				// for it is too costly
				String userID = user.getUserId();
				HttpServletRequest request = (HttpServletRequest) servletRequest;
				String attribute = (String) request.getSession().getAttribute(userID);
				if(!("isAdmin".equals(attribute))) {
					boolean present = checkUserPresentAsAdmin(userID);
	
					// if not add him up by default
					if(!present) {
						addUserAsAdmin(user);
					}
				}
			}
		}
		
		filterChain.doFilter(servletRequest, servletResponse);
	}
	
	private void addUserAsAdmin(User user) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			EvenstarUser adminUser = new EvenstarUser();
			adminUser.setEmail(user.getEmail());
			adminUser.setGoogleUserID(user.getUserId());
			adminUser.setUserName(user.getNickname());
			adminUser.setAdmin(true);
			
			manager.makePersistent(adminUser);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
	}

	@SuppressWarnings("unchecked")
	private static final boolean checkUserPresentAsAdmin(String googleUserID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(EvenstarUser.class, "googleUserID == googleUserIDParam");
			query.declareParameters("String googleUserIDParam");
			
			List<EvenstarUser> users = (List<EvenstarUser>) query.execute(googleUserID);
			if(users != null && users.size() == 1) {
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

}
