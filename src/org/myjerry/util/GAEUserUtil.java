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
		return getUserService().isUserAdmin();
	}

}
