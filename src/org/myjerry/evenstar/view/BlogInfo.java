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
package org.myjerry.evenstar.view;

import java.util.Date;

import org.myjerry.evenstar.model.Blog;

public class BlogInfo {
	
	private Long blogID;
	
	private String title;
	
	private String pageType;
	
	private String url;
	
	private String homepageUrl;
	
	private String pageTitle;
	
	private String encoding;
	
	private String feedLinks;
	
	private Long numPosts;
	
	private Long unpublishedComments;
	
	private Date lastPublishedDate;
	
	private String description;

	public BlogInfo(Blog blog) {
		this.blogID = blog.getBlogID();
		this.title = blog.getTitle();
		this.url = blog.getAddress();
		this.description = blog.getDescription();
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
	 * @return the pageType
	 */
	public String getPageType() {
		return pageType;
	}

	/**
	 * @param pageType the pageType to set
	 */
	public void setPageType(String pageType) {
		this.pageType = pageType;
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
	 * @return the homepageUrl
	 */
	public String getHomepageUrl() {
		return homepageUrl;
	}

	/**
	 * @param homepageUrl the homepageUrl to set
	 */
	public void setHomepageUrl(String homepageUrl) {
		this.homepageUrl = homepageUrl;
	}

	/**
	 * @return the pageTitle
	 */
	public String getPageTitle() {
		return pageTitle;
	}

	/**
	 * @param pageTitle the pageTitle to set
	 */
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @return the feedLinks
	 */
	public String getFeedLinks() {
		return feedLinks;
	}

	/**
	 * @param feedLinks the feedLinks to set
	 */
	public void setFeedLinks(String feedLinks) {
		this.feedLinks = feedLinks;
	}

	/**
	 * @return the numPosts
	 */
	public Long getNumPosts() {
		return numPosts;
	}

	/**
	 * @param numPosts the numPosts to set
	 */
	public void setNumPosts(Long numPosts) {
		this.numPosts = numPosts;
	}

	/**
	 * @return the unpublishedComments
	 */
	public Long getUnpublishedComments() {
		return unpublishedComments;
	}

	/**
	 * @param unpublishedComments the unpublishedComments to set
	 */
	public void setUnpublishedComments(Long unpublishedComments) {
		this.unpublishedComments = unpublishedComments;
	}

	/**
	 * @return the lastPublishedDate
	 */
	public Date getLastPublishedDate() {
		return lastPublishedDate;
	}

	/**
	 * @param lastPublishedDate the lastPublishedDate to set
	 */
	public void setLastPublishedDate(Date lastPublishedDate) {
		this.lastPublishedDate = lastPublishedDate;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	} 

}
