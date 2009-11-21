package org.myjerry.evenstar.service;

import java.util.Collection;

import org.myjerry.evenstar.model.Comment;

public interface CommentService {
	
	public boolean postComment(Comment comment);
	
	public boolean deleteComment(Long commentID);
	
	public boolean publishComment(Long commentID);
	
	public Comment getComment(Long commentID);
	
	public Collection<Comment> getCommentsForPost(Long blogPostID);

	public Collection<Comment> getAllUnpublishedComments();
	
	public Collection<Comment> getUnpublishedCommentsForPost(Long blogPostID);
}
