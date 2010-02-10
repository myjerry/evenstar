package org.myjerry.evenstar.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class EvenstarAdminUser {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long adminID;
	
	@Persistent
	private String userName;
	
	@Persistent
	private String email;
	
	@Persistent
	private String googleUserID;
	
	/**
	 * @return the adminID
	 */
	public Long getAdminID() {
		return adminID;
	}

	/**
	 * @param adminID the adminID to set
	 */
	public void setAdminID(Long adminID) {
		this.adminID = adminID;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the googleID
	 */
	public String getGoogleUserID() {
		return googleUserID;
	}

	/**
	 * @param googleID the googleID to set
	 */
	public void setGoogleUserID(String googleID) {
		this.googleUserID = googleID;
	}

}
