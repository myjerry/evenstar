package org.myjerry.evenstar.view;

import java.util.List;

import org.myjerry.evenstar.model.BlogPost;

public class BlogPostInfo {

	private String dateHeader;
	
	private Long id;
	
	private Long blogID;
	
	private String title;
	
	private String body;
	
	private String author;
	
	private String url;
	
	private String timestamp;
	
	private List<LabelInfo> labels;
	
	private boolean allowComments;
	
	private int numComments;
	
	private boolean showBackLinks;
	
	private int numBackLinks;
	
	private String addCommentUrl;
	
	private String emailPostUrl;
	
	private String editUrl;
	
	private List<FeedLinkInfo> feedLinks;
	
	private List<CommentInfo> comments;

	public BlogPostInfo(BlogPost post) {
		this.id = post.getPostID();
		this.title = post.getTitle();
		this.body = post.getContents();
		this.author = post.getLastUpdateUser();
		this.blogID = post.getBlogID();
		this.timestamp = post.getPostedDate().toString();
		this.addCommentUrl = "/postComment.html?postID=" + this.id + "&blogID=" + this.blogID;
		this.editUrl = "/post.html?postID=" + this.id + "&blogID=" + this.blogID;
		this.url = post.getUrl();
		this.emailPostUrl = "/emailPost.html?postID=" + this.id + "&blogID=" + this.blogID;
	}

	/**
	 * @return the dateHeader
	 */
	public String getDateHeader() {
		return dateHeader;
	}

	/**
	 * @param dateHeader the dateHeader to set
	 */
	public void setDateHeader(String dateHeader) {
		this.dateHeader = dateHeader;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
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
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the labels
	 */
	public List<LabelInfo> getLabels() {
		return labels;
	}

	/**
	 * @param labels the labels to set
	 */
	public void setLabels(List<LabelInfo> labels) {
		this.labels = labels;
	}

	/**
	 * @return the allowComments
	 */
	public boolean isAllowComments() {
		return allowComments;
	}

	/**
	 * @param allowComments the allowComments to set
	 */
	public void setAllowComments(boolean allowComments) {
		this.allowComments = allowComments;
	}

	/**
	 * @return the numComments
	 */
	public int getNumComments() {
		return numComments;
	}

	/**
	 * @param numComments the numComments to set
	 */
	public void setNumComments(int numComments) {
		this.numComments = numComments;
	}

	/**
	 * @return the showBackLinks
	 */
	public boolean isShowBackLinks() {
		return showBackLinks;
	}

	/**
	 * @param showBackLinks the showBackLinks to set
	 */
	public void setShowBackLinks(boolean showBackLinks) {
		this.showBackLinks = showBackLinks;
	}

	/**
	 * @return the numBackLinks
	 */
	public int getNumBackLinks() {
		return numBackLinks;
	}

	/**
	 * @param numBackLinks the numBackLinks to set
	 */
	public void setNumBackLinks(int numBackLinks) {
		this.numBackLinks = numBackLinks;
	}

	/**
	 * @return the addCommentUrl
	 */
	public String getAddCommentUrl() {
		return this.addCommentUrl;
	}

	/**
	 * @param addCommentUrl the addCommentUrl to set
	 */
	public void setAddCommentUrl(String addCommentUrl) {
		this.addCommentUrl = addCommentUrl;
	}

	/**
	 * @return the emailPostUrl
	 */
	public String getEmailPostUrl() {
		return emailPostUrl;
	}

	/**
	 * @param emailPostUrl the emailPostUrl to set
	 */
	public void setEmailPostUrl(String emailPostUrl) {
		this.emailPostUrl = emailPostUrl;
	}

	/**
	 * @return the editUrl
	 */
	public String getEditUrl() {
		return editUrl;
	}

	/**
	 * @param editUrl the editUrl to set
	 */
	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}

	/**
	 * @return the feedLinks
	 */
	public List<FeedLinkInfo> getFeedLinks() {
		return feedLinks;
	}

	/**
	 * @param feedLinks the feedLinks to set
	 */
	public void setFeedLinks(List<FeedLinkInfo> feedLinks) {
		this.feedLinks = feedLinks;
	}

	/**
	 * @return the comments
	 */
	public List<CommentInfo> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<CommentInfo> comments) {
		this.comments = comments;
	}

	/**
	 * @param blogID the blogID to set
	 */
	public void setBlogID(Long blogID) {
		this.blogID = blogID;
	}
	
}
