package org.myjerry.evenstar.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class RejectedComment {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long commentID;
	
	@Persistent
	private Long postID;
	
	@Persistent
	private Long blogID;
	
	@Persistent
	private Text content;
	
	@Persistent
	private Long authorID;
	
	@Persistent
	private Date timestamp;
	
	@Persistent
	private Integer permissions;
	
	@Persistent
	private Long parentID;
	
	public RejectedComment(Comment comment) {
		this.postID = comment.getPostID();
		this.blogID = comment.getBlogID();
		this.setContent(comment.getContent());
		this.authorID = comment.getAuthorID();
		this.timestamp = comment.getTimestamp();
		this.permissions = comment.getPermissions();
		this.parentID = comment.getParentID();
	}

	/**
	 * @return the commentID
	 */
	public Long getCommentID() {
		return commentID;
	}

	/**
	 * @param commentID the commentID to set
	 */
	public void setCommentID(Long commentID) {
		this.commentID = commentID;
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
	 * @return the content
	 */
	public String getContent() {
		if(this.content != null) {
			return this.content.getValue();
		}
		return null;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = new Text(content);
	}

	/**
	 * @return the authorID
	 */
	public Long getAuthorID() {
		return authorID;
	}

	/**
	 * @param authorID the authorID to set
	 */
	public void setAuthorID(Long authorID) {
		this.authorID = authorID;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the permissions
	 */
	public Integer getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(Integer permissions) {
		this.permissions = permissions;
	}

	/**
	 * @return the parentID
	 */
	public Long getParentID() {
		return parentID;
	}

	/**
	 * @param parentID the parentID to set
	 */
	public void setParentID(Long parentID) {
		this.parentID = parentID;
	}
	
}
