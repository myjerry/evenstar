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
package org.myjerry.evenstar.service;

import java.util.Collection;

import org.myjerry.evenstar.model.AutoApprovedCommentor;
import org.myjerry.evenstar.model.BannedCommentor;
import org.myjerry.evenstar.model.Comment;

public interface CommentService {
	
	public boolean postComment(Comment comment);
	
	public boolean deleteComment(Long commentID, Long postID, Long blogID);
	
	public boolean publishComment(Long commentID, Long postID, Long blogID);
	
	public Comment getComment(Long commentID, Long postID, Long blogID);
	
	public Collection<Comment> getPublishedCommentsForPost(Long postID, Long blogID, int count);

	public Collection<Comment> getAllUnpublishedComments();
	
	public Collection<Comment> getUnpublishedCommentsForPost(Long postID, Long blogID);
	
	public boolean postHasComment(Long commentID, Long postID, Long blogID);
	
	public int getTotalPublishedCommentsOnPost(Long postID, Long blogID);
	
	public boolean addAutoApproveCommentor(String emailAddress, Long blogID);
	
	public boolean deleteAutoApproveCommentor(String emailAddress, Long blogID);
	
	public Collection<AutoApprovedCommentor> getAutoApproveCommentors(Long blogID);
	
	public boolean existsAutoApproveCommentor(String emailAddress, Long blogID);
	
	public boolean addAutoRejectCommentor(String emailAddress, Long blogID);
	
	public boolean deleteAutoRejectCommentor(String emailAddress, Long blogID);
	
	public Collection<BannedCommentor> getAutoRejectCommentors(Long blogID);
	
	public boolean existsAutoRejectCommentor(String emailAddress, Long blogID);

	public Long getNumUnpublishedCommentsForBlog(Long blogID);

	public Collection<Comment> getUnpublishedCommentsForBlog(Long blogID);

	public boolean rejectComment(Long commentID, Long postID, Long blogID);
	
}
