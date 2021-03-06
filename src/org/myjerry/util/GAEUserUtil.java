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
package org.myjerry.util;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GAEUserUtil {
	
	public static String getNickName() {
		User user = getUser();
		if(user != null) {
			return user.getNickname();
		}
		return null;
	}
	
	public static String getEmail() {
		User user = getUser();
		if(user != null) {
			return user.getEmail();
		}
		return null;
	}
	
	public static UserService getUserService() {
		return UserServiceFactory.getUserService();
	}
	
	public static User getUser() {
		return getUserService().getCurrentUser();
	}
	
	public static String getUserID() {
		User user = getUser();
		if(user != null) {
			return user.getUserId();
		}
		return null;
	}
	
	public static boolean isCurrentUserHost() {
		UserService service = getUserService();
		if(service != null) {
			return service.isUserLoggedIn() && service.isUserAdmin();
		}
		return false;
	}

}
