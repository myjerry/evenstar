package org.myjerry.evenstar.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class BlogLabel {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long labelID;
	
	@Persistent
	private Long blogID;
	
	@Persistent
	private String labelName;
	
	@Persistent
	private String escapedLabel;
	
	@Persistent
	private Integer numPosts;
	
	public String getUrl() {
		return "/showPostsForLabel.html?label=" + this.labelName;
	}

	/**
	 * @return the labelID
	 */
	public long getLabelID() {
		return labelID;
	}

	/**
	 * @param labelID the labelID to set
	 */
	public void setLabelID(long labelID) {
		this.labelID = labelID;
	}

	/**
	 * @return the blogID
	 */
	public long getBlogID() {
		return blogID;
	}

	/**
	 * @param blogID the blogID to set
	 */
	public void setBlogID(long blogID) {
		this.blogID = blogID;
	}

	/**
	 * @return the labelName
	 */
	public String getLabelName() {
		return labelName;
	}

	/**
	 * @param labelName the labelName to set
	 */
	public void setLabelName(String labelName) {
		this.labelName = labelName;
		this.escapedLabel = labelName.toUpperCase();
	}

	/**
	 * @return the numPosts
	 */
	public Integer getNumPosts() {
		return numPosts;
	}

	/**
	 * @param numPosts the numPosts to set
	 */
	public void setNumPosts(Integer numPosts) {
		this.numPosts = numPosts;
	}

	/**
	 * @return the escapedLabel
	 */
	public String getEscapedLabel() {
		return escapedLabel;
	}
	
}
