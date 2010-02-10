package org.myjerry.evenstar.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class BlogPost {
	
	public static final int PRIVACY_MODE_PUBLIC = 1;
	
	public static final int PRIVACY_MODE_RESTRICTED = 2;
	
	public static final int PRIVACY_MODE_PRIVATE = 4;
	
	public static final int PRIVACY_MODE_CUSTOM = 8;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long postID;
	
	@Persistent
	private Long blogID;
	
	@Persistent
	private String title;
	
	@Persistent
	private Text contents;
	
	@Persistent
	private String labels;
	
	@Persistent
	private Integer privacyMode = PRIVACY_MODE_PUBLIC;
	
	@Persistent
	private Date creationDate;
	
	@Persistent
	private String url;
	
	@Persistent
	private Date postedDate;

	@Persistent
	private Date lastUpdated;
	
	@Persistent
	private Long lastUpdateUser;
	
	/**
	 * Just an ID that identifies the post import details. Kept for history purposes only.
	 */
	@Persistent
	private String uniqueImportID;
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof BlogPost)) {
			return false;
		}
		
		BlogPost o2 = (BlogPost) obj;
		return this.postID.equals(o2.postID);
	}

	/**
	 * @return the postID
	 */
	public Long getPostID() {
		return postID;
	}

	/**
	 * @param postID the postID to set
	 */
	public void setPostID(Long postID) {
		this.postID = postID;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		if(contents != null) {
			return contents.getValue();
		}
		return null;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = new Text(contents);
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the postedDate
	 */
	public Date getPostedDate() {
		return postedDate;
	}

	/**
	 * @param postedDate the postedDate to set
	 */
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	/**
	 * @return the lastUpdated
	 */
	public Date getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * @param lastUpdated the lastUpdated to set
	 */
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	/**
	 * @return the lastUpdateUser
	 */
	public Long getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser the lastUpdateUser to set
	 */
	public void setLastUpdateUser(Long lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return the labels
	 */
	public String getLabels() {
		return labels;
	}

	/**
	 * @param labels the labels to set
	 */
	public void setLabels(String labels) {
		this.labels = labels;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the privacyMode
	 */
	public Integer getPrivacyMode() {
		return privacyMode;
	}

	/**
	 * @param privacyMode the privacyMode to set
	 */
	public void setPrivacyMode(Integer privacyMode) {
		this.privacyMode = privacyMode;
	}

	/**
	 * @return the uniqueImportID
	 */
	public String getUniqueImportID() {
		return uniqueImportID;
	}

	/**
	 * @param uniqueImportID the uniqueImportID to set
	 */
	public void setUniqueImportID(String uniqueImportID) {
		this.uniqueImportID = uniqueImportID;
	}

}
