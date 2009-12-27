package org.myjerry.evenstar.service.impl;

import java.util.Collection;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.myjerry.evenstar.model.AutoApprovedCommentor;
import org.myjerry.evenstar.model.BannedCommentor;
import org.myjerry.evenstar.model.Comment;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.CommentService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.ServerUtils;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class CommentServiceImpl implements CommentService {

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
	public Collection<Comment> getCommentsForPost(Long postID, Long blogID, int count) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(Comment.class);
	    query.setFilter("postID == postIDParam && blogID == blogIDParam");
	    query.setOrdering("timestamp desc");
	    query.declareParameters("String postIDParam, String blogIDParam");
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
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			manager.makePersistent(comment);
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	@Override
	public boolean publishComment(Long commentID, Long postID, Long blogID) {
		// verify if blog and post both have comments enabled
		
		// verify if comment is for the same blog and post
		
		// check if comment has not been deleted
		
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
	public int getTotalCommentsOnPost(Long postID, Long blogID) {
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(Comment.class.getSimpleName());
		query.setKeysOnly();
		query.addFilter("blogID", FilterOperator.EQUAL, blogID);
		query.addFilter("postID", FilterOperator.EQUAL, postID);
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

}
