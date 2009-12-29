package org.myjerry.evenstar.view;

public class LabelInfo {
	
	private String name;
	
	public LabelInfo(String label) {
		this.name = label;
	}
	
	public String getUrl() {
		return "/showPostsForLabel.html?label=" + this.name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
