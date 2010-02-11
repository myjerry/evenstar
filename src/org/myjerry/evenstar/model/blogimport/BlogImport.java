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

import java.util.ArrayList;
import java.util.List;

public class BlogImport {
	
	private String title;
	
	private List<PostImport> posts = new ArrayList<PostImport>();
	
	private List<CommentImport> comments = new ArrayList<CommentImport>();
	
	public void addPostImport(PostImport postImport) {
		if(postImport != null) {
			this.posts.add(postImport);
		}
	}
	
	public void addCommentImport(CommentImport commentImport) {
		if(commentImport != null) {
			this.comments.add(commentImport);
		}
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
	 * @return the posts
	 */
	public List<PostImport> getPosts() {
		return posts;
	}

	/**
	 * @param posts the posts to set
	 */
	public void setPosts(List<PostImport> posts) {
		this.posts = posts;
	}

	/**
	 * @return the comments
	 */
	public List<CommentImport> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<CommentImport> comments) {
		this.comments = comments;
	}
	
}
