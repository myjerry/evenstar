package org.myjerry.evenstar.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class BlogPostLabelMapping {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long mappingID;
	
	@Persistent
	private Long blogID;
	
	@Persistent
	private Long postID;
	
	@Persistent
	private Long labelID;

	/**
	 * @return the mappingID
	 */
	public Long getMappingID() {
		return mappingID;
	}

	/**
	 * @param mappingID the mappingID to set
	 */
	public void setMappingID(Long mappingID) {
		this.mappingID = mappingID;
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
	 * @return the labelID
	 */
	public Long getLabelID() {
		return labelID;
	}

	/**
	 * @param labelID the labelID to set
	 */
	public void setLabelID(Long labelID) {
		this.labelID = labelID;
	}

}
