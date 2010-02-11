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

import java.text.SimpleDateFormat;

import org.myjerry.evenstar.model.Comment;

public class CommentInfo {
	
	private long id;
	
	private String body;
	
	private String timestamp;
	
	private String author;
	
	private String authorUrl;
	
	private String deleteUrl;
	
	private boolean isDeleted;
	
	private boolean restricted;

	public CommentInfo(Comment comment, String commentTimeStampFormat) {
		if(comment == null) {
			return;
		}
		
		this.id = comment.getCommentID();
		this.body = comment.getContent();
		
		SimpleDateFormat formatter = new SimpleDateFormat(commentTimeStampFormat);
		this.timestamp = formatter.format(comment.getTimestamp());
		
		this.author = String.valueOf(comment.getAuthorID());
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
	 * @return the authorUrl
	 */
	public String getAuthorUrl() {
		return authorUrl;
	}

	/**
	 * @param authorUrl the authorUrl to set
	 */
	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

	/**
	 * @return the deleteUrl
	 */
	public String getDeleteUrl() {
		return deleteUrl;
	}

	/**
	 * @param deleteUrl the deleteUrl to set
	 */
	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}

	/**
	 * @return the isDeleted
	 */
	public boolean isDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the restricted
	 */
	public boolean isRestricted() {
		return restricted;
	}

	/**
	 * @param restricted the restricted to set
	 */
	public void setRestricted(boolean restricted) {
		this.restricted = restricted;
	}
	
}
