package org.myjerry.evenstar.service;

import org.myjerry.evenstar.model.EvenstarUser;

public interface UserService {

	public Long getEvenstarUserID(String userID, String email);
	
	public EvenstarUser getEvenstarUser(String userID, String email);

	public EvenstarUser getEvenstarUser(Long evenstarUserID);

	public boolean isUserAdmin(String userGoogleID);
	
	public boolean addAdmin(String userGoogleID);
	

}
