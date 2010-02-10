package org.myjerry.evenstar.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class EvenstarUser {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long userID;
	
	@Persistent
	private String userName;
	
	@Persistent
	private String homePage;
	
	@Persistent
	private String provider;
	
	@Persistent
	private String email;
	
	@Persistent
	private Date joinedOn;
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof EvenstarUser)) {
			return false;
		}
		
		EvenstarUser o2 = (EvenstarUser) obj;
		
		// either both id should be not null and equal
		if(this.userID != null && o2.userID != null && this.userID.equals(o2.userID)) {
			return true;
		}
		
		// both email should be same
		if(this.email != null && o2.email != null && this.email.equals(o2.email)) {
			return true;
		}
		
		// both the home page should be same
		if(this.homePage != null && o2.homePage != null && this.homePage.equals(o2.homePage)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * @return the userID
	 */
	public long getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(long userID) {
		this.userID = userID;
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
	 * @return the joinedOn
	 */
	public Date getJoinedOn() {
		return joinedOn;
	}

	/**
	 * @param joinedOn the joinedOn to set
	 */
	public void setJoinedOn(Date joinedOn) {
		this.joinedOn = joinedOn;
	}

	/**
	 * @return the homePage
	 */
	public String getHomePage() {
		return homePage;
	}

	/**
	 * @param homePage the homePage to set
	 */
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

}
