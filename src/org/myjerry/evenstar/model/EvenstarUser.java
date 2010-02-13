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
package org.myjerry.evenstar.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.myjerry.util.ServerUtils;

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
	private Date joinedOn = ServerUtils.getServerDate();
	
	@Persistent
	private Boolean admin = false;
	
	@Persistent
	private String googleUserID;
	
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
	
	public boolean isAdmin() {
		if(this.admin != null) {
			return this.admin.booleanValue();
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

	/**
	 * @return the admin
	 */
	public Boolean getAdmin() {
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return the googleUserID
	 */
	public String getGoogleUserID() {
		return googleUserID;
	}

	/**
	 * @param googleUserID the googleUserID to set
	 */
	public void setGoogleUserID(String googleUserID) {
		this.googleUserID = googleUserID;
	}

}
