package org.myjerry.evenstar.service;

import org.myjerry.evenstar.model.EvenstarUser;

public interface UserService {

	public Long getEvenstarUserID(String email);
	
	public Long getEvenstarUserIDForUri(String uri);
	
	public EvenstarUser getEvenstarUser(String email);
	
	public EvenstarUser getEvenstarUserForUri(String uri);

	public EvenstarUser getEvenstarUser(Long userID);

	public boolean isUserAdmin(String email);
	
	public boolean isUserAdmin(Long userID);
	
	public boolean addAdmin(String email);
	
	public boolean addAdmin(Long userID);
	
	public boolean addEvenstarUser(EvenstarUser evenstarUser);
	
}
