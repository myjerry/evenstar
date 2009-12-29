package org.myjerry.evenstar.model;

import java.util.Date;

public class BlogArchivePost implements Comparable<BlogArchivePost> {
	
	private Long postID;
	
	private String title;
	
	private String url;
	
	private Date postedDate;
	
	public BlogArchivePost(Long postID, String title, String url, Date postedDate) {
		this.postID = postID;
		this.title = title;
		this.url = url;
		this.postedDate = postedDate;
	}

	@Override
	public int compareTo(BlogArchivePost o) {
		if(o == null) {
			return 1;
		}
		return this.postedDate.compareTo(o.postedDate);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof BlogArchivePost)) {
			return false;
		}
		BlogArchivePost o2 = (BlogArchivePost) obj;
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

}
