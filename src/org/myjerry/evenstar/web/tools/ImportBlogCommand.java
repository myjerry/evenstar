package org.myjerry.evenstar.web.tools;

import org.springframework.web.multipart.MultipartFile;

public class ImportBlogCommand {
	
	private MultipartFile blogXMLFile;

	/**
	 * @return the blogXMLFile
	 */
	public MultipartFile getBlogXMLFile() {
		return blogXMLFile;
	}

	/**
	 * @param blogXMLFile the blogXMLFile to set
	 */
	public void setBlogXMLFile(MultipartFile blogXMLFile) {
		this.blogXMLFile = blogXMLFile;
	}

}
