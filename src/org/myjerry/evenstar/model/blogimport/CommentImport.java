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
package org.myjerry.evenstar.model.blogimport;

import java.util.Date;

public class CommentImport {

	private String content;
	
	private Date published;
	
	private Date updated;
	
	private String uniquePostID;
	
	private AuthorImport author;

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return the uniquePostID
	 */
	public String getUniquePostID() {
		return uniquePostID;
	}

	/**
	 * @param uniquePostID the uniquePostID to set
	 */
	public void setUniquePostID(String uniquePostID) {
		this.uniquePostID = uniquePostID;
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
}
