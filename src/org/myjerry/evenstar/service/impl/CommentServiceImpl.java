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
package org.myjerry.evenstar.service.impl;

import java.util.Collection;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.myjerry.evenstar.constants.BlogPreferenceConstants;
import org.myjerry.evenstar.enums.CommentModerationType;
import org.myjerry.evenstar.model.AutoApprovedCommentor;
import org.myjerry.evenstar.model.BannedCommentor;
import org.myjerry.evenstar.model.Comment;
import org.myjerry.evenstar.model.RejectedComment;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.BlogPreferenceService;
import org.myjerry.evenstar.service.CommentService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.ServerUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private BlogPreferenceService blogPreferenceService;
	
	@Override
	public boolean deleteComment(Long commentID, Long postID, Long blogID) {
		// verify if blog id and post id are in match and comment is for the same blog and post
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(Comment.class.getSimpleName(), commentID); 
			Comment comment = manager.getObjectById(Comment.class, key);
			if(comment.getBlogID().equals(blogID) && comment.getPostID().equals(postID)) {
				manager.deletePersistent(comment);
				return true;
			}
		} catch(JDOObjectNotFoundException e) {
			// not found
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	@Override
	public Collection<Comment> getAllUnpublishedComments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment getComment(Long commentID, Long postID, Long blogID) {
		// verify if blog id and post id are in match and comment is for the same blog and post
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(Comment.class.getSimpleName(), commentID); 
			Comment comment = manager.getObjectById(Comment.class, key);
			if(comment.getBlogID().equals(blogID) && comment.getPostID().equals(postID)) {
				comment.getContent();
				return manager.detachCopy(comment);
			}
		} catch(JDOObjectNotFoundException e) {
			// not found
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Comment> getPublishedCommentsForPost(Long postID, Long blogID, int count) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(Comment.class);
	    query.setFilter("postID == postIDParam && blogID == blogIDParam && published != null");
	    query.setOrdering("published desc");
	    query.declareParameters("Long postIDParam, Long blogIDParam");
		query.setRange(0, count);
		
	    try {
	    	List<Comment> comments = (List<Comment>) query.execute(postID, blogID);
	    	if(comments != null) {
	    		// take care of a GAE Bug
	    		for(Comment comment : comments) {
	    			// hack for GAE not fetching text field in one go
	    			comment.getContent();
	    		}
	    		return manager.detachCopyAll(comments);
	    	}
	    	return comments;
	    } catch(JDOObjectNotFoundException e) {
	    	// do nothing
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	query.closeAll();
	    	manager.close();
	    }
		return null;
	}

	@Override
	public Collection<Comment> getUnpublishedCommentsForPost(Long postID, Long blogID) {
		// verify if blog id and post id are in match
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean postComment(Comment comment) {
		// check whether post id, blog id, and if parent id are all from the same post-blog combination

		// if all is good - persist them in the database
		comment.setTimestamp(ServerUtils.getServerDate());

		CommentModerationType moderation = CommentModerationType.getModeration(this.blogPreferenceService.getPreference(comment.getBlogID(), BlogPreferenceConstants.commentModeration));
		if(moderation == CommentModerationType.NEVER) {
			comment.setPublished(ServerUtils.getServerDate());
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			manager.makePersistent(comment);
			
			// moderate this comment
			if(moderation != CommentModerationType.NEVER) {
				// for auto approved commentors
				moderateComment(moderation, comment.getBlogID(), comment.getCommentID());
			}
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	private void moderateComment(CommentModerationType moderation, Long blogID, Long commentID) {
		// check and moderate this comment for auto approved commentors
		
		// or for admin users
		
		// or if the post type is older
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean publishComment(Long commentID, Long postID, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(Comment.class, "commentID == commentIDParam && postID == postIDParam && blogID == blogIDParam && published == null");
			query.declareParameters("Long commentIDParam, Long postIDParam, Long blogIDParam");
			
			List<Comment> comments = (List<Comment>) query.executeWithArray(commentID, postID, blogID);
			if(comments != null && comments.size() > 0) {
				for(Comment comment : comments) {
					if(comment.getDeleted() != null && (comment.getDeleted() != true)) {
						comment.setPublished(ServerUtils.getServerDate());
						manager.makePersistent(comment);
					}
				}
				
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	@Override
	public boolean postHasComment(Long commentID, Long postID, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(Comment.class.getSimpleName(), commentID); 
			Comment comment = manager.getObjectById(Comment.class, key);
			if(comment.getBlogID().equals(blogID) && comment.getPostID().equals(postID)) {
				return true;
			}			
		} catch(JDOObjectNotFoundException e) {
			// object was not found
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	@Override
	public int getTotalPublishedCommentsOnPost(Long postID, Long blogID) {
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(Comment.class.getSimpleName());
		query.setKeysOnly();
		query.addFilter("blogID", FilterOperator.EQUAL, blogID);
		query.addFilter("postID", FilterOperator.EQUAL, postID);
		query.addFilter("published", FilterOperator.NOT_EQUAL, null);
		FetchOptions fetchOptions = FetchOptions.Builder.withOffset(0).limit(Integer.MAX_VALUE);
		PreparedQuery preparedQuery = datastoreService.prepare(query);
		int size = preparedQuery.asList(fetchOptions).size();
		
		return size;
	}

	@Override
	public boolean addAutoApproveCommentor(String emailAddress, Long blogID) {
		if(!existsAutoApproveCommentor(emailAddress, blogID)) {
			AutoApprovedCommentor commentor = new AutoApprovedCommentor();
			commentor.setBlogID(blogID);
			commentor.setEmailAddress(emailAddress);
			commentor.setLastUpdatedOn(ServerUtils.getServerDate());
			commentor.setLastUpdatedBy(GAEUserUtil.getUserID());
			
			PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
			try {
				manager.makePersistent(commentor);
				
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				manager.close();
			}
		}
		return false;
	}

	@Override
	public boolean addAutoRejectCommentor(String emailAddress, Long blogID) {
		if(!existsAutoRejectCommentor(emailAddress, blogID)) {
			BannedCommentor commentor = new BannedCommentor();
			commentor.setBlogID(blogID);
			commentor.setEmailAddress(emailAddress);
			commentor.setLastUpdatedOn(ServerUtils.getServerDate());
			commentor.setLastUpdatedBy(GAEUserUtil.getUserID());
			
			PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
			try {
				manager.makePersistent(commentor);
				
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				manager.close();
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteAutoApproveCommentor(String emailAddress, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(AutoApprovedCommentor.class);
	    query.setFilter("emailAddress == emailAddressParam && blogID == blogIDParam");
	    query.declareParameters("String emailAddressParam, String blogIDParam");
	    
	    try {
	    	List<AutoApprovedCommentor> commentors = (List<AutoApprovedCommentor>) query.execute(emailAddress, blogID);
	    	if(commentors != null && commentors.size() > 0) {
	    		for(AutoApprovedCommentor commentor : commentors) {
	    			manager.deletePersistent(commentor);
	    		}
	    	}
	    	return true;
	    } catch(JDOObjectNotFoundException e) {
	    	// do nothing
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	query.closeAll();
	    	manager.close();
	    }
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteAutoRejectCommentor(String emailAddress, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BannedCommentor.class);
	    query.setFilter("emailAddress == emailAddressParam && blogID == blogIDParam");
	    query.declareParameters("String emailAddressParam, String blogIDParam");
	    
	    try {
	    	List<BannedCommentor> commentors = (List<BannedCommentor>) query.execute(emailAddress, blogID);
	    	if(commentors != null && commentors.size() > 0) {
	    		for(BannedCommentor commentor : commentors) {
	    			manager.deletePersistent(commentor);
	    		}
	    	}
	    	return true;
	    } catch(JDOObjectNotFoundException e) {
	    	// do nothing
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	query.closeAll();
	    	manager.close();
	    }
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean existsAutoApproveCommentor(String emailAddress, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(AutoApprovedCommentor.class);
	    query.setFilter("emailAddress == emailAddressParam && blogID == blogIDParam");
	    query.declareParameters("String emailAddressParam, String blogIDParam");
	    
	    try {
	    	List<AutoApprovedCommentor> commentors = (List<AutoApprovedCommentor>) query.execute(emailAddress, blogID);
	    	if(commentors != null && commentors.size() == 1) {
	    		return true;
	    	}
	    } catch(JDOObjectNotFoundException e) {
	    	// do nothing
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	query.closeAll();
	    	manager.close();
	    }
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean existsAutoRejectCommentor(String emailAddress, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BannedCommentor.class);
	    query.setFilter("emailAddress == emailAddressParam && blogID == blogIDParam");
	    query.declareParameters("String emailAddressParam, String blogIDParam");
	    
	    try {
	    	List<BannedCommentor> commentors = (List<BannedCommentor>) query.execute(emailAddress, blogID);
	    	if(commentors != null && commentors.size() == 1) {
	    		return true;
	    	}
	    } catch(JDOObjectNotFoundException e) {
	    	// do nothing
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	query.closeAll();
	    	manager.close();
	    }
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<AutoApprovedCommentor> getAutoApproveCommentors(Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(AutoApprovedCommentor.class);
	    query.setFilter("blogID == blogIDParam");
	    query.declareParameters("String blogIDParam");
	    
	    try {
	    	List<AutoApprovedCommentor> commentors = (List<AutoApprovedCommentor>) query.execute(blogID);
	    	if(commentors != null && commentors.size() > 0) {
	    		return manager.detachCopyAll(commentors);
	    	}
	    } catch(JDOObjectNotFoundException e) {
	    	// do nothing
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	query.closeAll();
	    	manager.close();
	    }
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<BannedCommentor> getAutoRejectCommentors(Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BannedCommentor.class);
	    query.setFilter("blogID == blogIDParam");
	    query.declareParameters("String blogIDParam");
	    
	    try {
	    	List<BannedCommentor> commentors = (List<BannedCommentor>) query.execute(blogID);
	    	if(commentors != null && commentors.size() > 0) {
	    		return manager.detachCopyAll(commentors);
	    	}
	    } catch(JDOObjectNotFoundException e) {
	    	// do nothing
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	query.closeAll();
	    	manager.close();
	    }
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getNumUnpublishedCommentsForBlog(Long blogID) {
		if(blogID != null) {
			PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
			Query query = manager.newQuery(Comment.class, "blogID == blogIDParam && published == null");
			query.declareParameters("Long blogIDParam");
			
			try {
				List<Comment> comments = (List<Comment>) query.execute(blogID);
				if(comments != null) {
					int size = comments.size();
					return new Long(size);
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				manager.close();
			}
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Comment> getUnpublishedCommentsForBlog(Long blogID) {
		if(blogID != null) {
			PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
			Query query = manager.newQuery(Comment.class, "blogID == blogIDParam && published == null");
			query.declareParameters("Long blogIDParam");
			
			try {
				List<Comment> comments = (List<Comment>) query.execute(blogID);
				return manager.detachCopyAll(comments);
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				manager.close();
			}
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean rejectComment(Long commentID, Long postID, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(Comment.class, "commentID == commentIDParam && postID == postIDParam && blogID == blogIDParam && published == null");
			query.declareParameters("Long commentIDParam, Long postIDParam, Long blogIDParam");
			
			List<Comment> comments = (List<Comment>) query.executeWithArray(commentID, postID, blogID);
			if(comments != null && comments.size() > 0) {
				for(Comment comment : comments) {
					if(comment.getDeleted() == null || comment.getDeleted() == false) {
						RejectedComment rejectedComment = new RejectedComment(comment);
						
						// TODO: do this in a transaction
						manager.deletePersistent(comment);
						manager.makePersistent(rejectedComment);
					}
				}
				
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}
	
	// Usual accessors follow
	
	/**
	 * @return the blogPreferenceService
	 */
	public BlogPreferenceService getBlogPreferenceService() {
		return blogPreferenceService;
	}

	/**
	 * @param blogPreferenceService the blogPreferenceService to set
	 */
	public void setBlogPreferenceService(BlogPreferenceService blogPreferenceService) {
		this.blogPreferenceService = blogPreferenceService;
	}

}
