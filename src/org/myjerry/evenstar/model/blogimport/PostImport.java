package org.myjerry.evenstar.model.blogimport;

import java.util.Date;

public class PostImport {
	
	private String uniqueID;
	
	private Date published;
	
	private Date updated;

	private String title;
	
	private String htmlContent;
	
	private AuthorImport author;
	
	private String tags;
	
	private String href;

	/**
	 * @return the uniqueID
	 */
	public String getUniqueID() {
		return uniqueID;
	}

	/**
	 * @param uniqueID the uniqueID to set
	 */
	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	/**
	 * @return the published
	 */
	public Date getPublished() {
		return published;
	}

	/**
	 * @param published the published to set
	 */
	public void setPublished(Date published) {
		this.published = published;
	}

	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
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
	 * @return the htmlContent
	 */
	public String getHtmlContent() {
		return htmlContent;
	}

	/**
	 * @param htmlContent the htmlContent to set
	 */
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	/**
	 * @return the author
	 */
	public AuthorImport getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(AuthorImport author) {
		this.author = author;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}
}
