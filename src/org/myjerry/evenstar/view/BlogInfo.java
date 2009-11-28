package org.myjerry.evenstar.view;

import org.myjerry.evenstar.model.Blog;

public class BlogInfo {
	
	private String title;
	
	private String pageType;
	
	private String url;
	
	private String homepageUrl;
	
	private String pageTitle;
	
	private String encoding;
	
	private String feedLinks;

	public BlogInfo(Blog blog) {
		this.title = blog.getTitle();
		this.url = blog.getAddress();
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

}
