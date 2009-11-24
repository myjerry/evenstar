package org.myjerry.evenstar.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class BlogPreference {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long preferenceID;
	
	@Persistent
	private String key;
	
	@Persistent
	private Long blogID;
	
	@Persistent
	private String value;
	
	@Persistent
	private String lastUpdateUser;
	
	@Persistent
	private Date lastUpdateTime;

	/**
	 * @return the preferenceID
	 */
	public Long getPreferenceID() {
		return preferenceID;
	}

	/**
	 * @param preferenceID the preferenceID to set
	 */
	public void setPreferenceID(Long preferenceID) {
		this.preferenceID = preferenceID;
	}

	/**
	 * @return the keyName
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param keyName the keyName to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the blogID
	 */
	public Long getBlogID() {
		return blogID;
	}

	/**
	 * @param blogID the blogID to set
	 */
	public void setBlogID(Long blogID) {
		this.blogID = blogID;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the lastUpdateUser
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser the lastUpdateUser to set
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return the lastUpdateTime
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}


}
