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
