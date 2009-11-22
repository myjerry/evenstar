package org.myjerry.evenstar.service.impl;

import java.util.Collection;

import org.myjerry.evenstar.model.Comment;
import org.myjerry.evenstar.service.CommentService;

public class CommentServiceImpl implements CommentService {

	@Override
	public boolean deleteComment(Long commentID, Long postID, Long blogID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Comment> getAllUnpublishedComments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment getComment(Long commentID, Long postID, Long blogID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Comment> getCommentsForPost(Long postID, Long blogID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Comment> getUnpublishedCommentsForPost(Long postID,
			Long blogID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean postComment(Comment comment) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean publishComment(Long commentID, Long postID, Long blogID) {
		// TODO Auto-generated method stub
		return false;
	}

}
