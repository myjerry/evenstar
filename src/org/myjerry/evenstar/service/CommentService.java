package org.myjerry.evenstar.service;

import java.util.Collection;

import org.myjerry.evenstar.model.Comment;

public interface CommentService {
	
	public boolean postComment(Comment comment);
	
	public boolean deleteComment(Long commentID, Long postID, Long blogID);
	
	public boolean publishComment(Long commentID, Long postID, Long blogID);
	
	public Comment getComment(Long commentID, Long postID, Long blogID);
	
	public Collection<Comment> getCommentsForPost(Long postID, Long blogID, int count);

	public Collection<Comment> getAllUnpublishedComments();
	
	public Collection<Comment> getUnpublishedCommentsForPost(Long postID, Long blogID);
	
	public boolean postHasComment(Long commentID, Long postID, Long blogID);
	
	public int getTotalCommentsOnPost(Long postID, Long blogID);
	
}
