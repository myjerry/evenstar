package org.myjerry.evenstar.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Comment {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long commentID;
	
	@Persistent
	private Long blogPostID;
	
	@Persistent
	private Long blogID;
	
	@Persistent
	private Text content;
	
	@Persistent
	private long authorID;
	
	@Persistent
	private Date timestamp;
	
	@Persistent
	private int permissions;
	
	@Persistent
	private boolean deleted;
	
	@Persistent
	private long parentID;

	/**
	 * @return the commentID
	 */
	public long getCommentID() {
		return commentID;
	}

	/**
	 * @param commentID the commentID to set
	 */
	public void setCommentID(long commentID) {
		this.commentID = commentID;
	}

	/**
	 * @return the blogPostID
	 */
	public long getBlogPostID() {
		return blogPostID;
	}

	/**
	 * @param blogPostID the blogPostID to set
	 */
	public void setBlogPostID(long blogPostID) {
		this.blogPostID = blogPostID;
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
	 * @return the content
	 */
	public String getContent() {
		return content.getValue();
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
	public long getAuthorID() {
		return authorID;
	}

	/**
	 * @param authorID the authorID to set
	 */
	public void setAuthorID(long authorID) {
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
	public int getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}

	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the parentID
	 */
	public long getParentID() {
		return parentID;
	}

	/**
	 * @param parentID the parentID to set
	 */
	public void setParentID(long parentID) {
		this.parentID = parentID;
	}
	
}
