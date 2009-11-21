package org.myjerry.evenstar.model;

public class BlogLabels {
	
	private long labelID;
	
	private long blogID;
	
	private String labelName;

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
	}
	
}
