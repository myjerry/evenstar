package org.myjerry.evenstar.service.impl;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.ServerUtils;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class BlogPostServiceImpl implements BlogPostService {

	@Override
	public boolean publishPost(BlogPost blogPost) {
		if(blogPost.getCreationDate() == null) {
			blogPost.setCreationDate(ServerUtils.getServerDate());
		}
		blogPost.setLastUpdated(ServerUtils.getServerDate());
		blogPost.setLastUpdateUser(GAEUserUtil.getUserID());
		blogPost.setPostedDate(ServerUtils.getServerDate());
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		
		try {
			if(blogPost.getPostID() == null) {
				// create a new post
				manager.makePersistent(blogPost);
			} else {
				// this post should be updated
				
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			manager.close();
		}
	}

	@Override
	public boolean deletePost(Long blogPostID, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(BlogPost.class.getSimpleName(), blogPostID); 
			BlogPost post = manager.getObjectById(BlogPost.class, key);
			if(post.getBlogID().equals(blogID)) {
				manager.deletePersistent(post);
				return true;
			}
			return false;
		} catch(Exception e) {
			return false;
		} finally {
			manager.close();
		}
	}

	@Override
	public boolean saveDraftPost(BlogPost blogPost) {
		if(blogPost.getCreationDate() == null) {
			blogPost.setCreationDate(ServerUtils.getServerDate());
		}
		blogPost.setLastUpdated(ServerUtils.getServerDate());
		blogPost.setLastUpdateUser(GAEUserUtil.getUserID());
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		
		try {
			if(blogPost.getPostID() == null) {
				// create a new post
				manager.makePersistent(blogPost);
			} else {
				// this post should be updated
				
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			manager.close();
		}
	}

	@Override
	public boolean schedulePost(BlogPost blogPost) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unPublishPost(Long blogPostID, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(BlogPost.class.getSimpleName(), blogPostID); 
			BlogPost post = manager.getObjectById(BlogPost.class, key);
			if(post.getBlogID().equals(blogID)) {
				post.setCreationDate(null);
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	/**
	 * Due to GAE Datastore restrictions only max 1000 posts will be returned by this method.
	 * Use the {@link #getBlogPosts(Long, long, long)} method instead.  
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<BlogPost> getAllBlogPosts(Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(BlogPost.class, "blogID == blogIDParam");
			query.declareParameters("String blogIDParam");
			
			List<BlogPost> blogs = (List<BlogPost>) query.execute(blogID);
			return manager.detachCopyAll(blogs);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			manager.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<BlogPost> getBlogPosts(Long blogID, int count) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BlogPost.class);
	    query.setFilter("blogID == blogIDParam");
	    query.setOrdering("postedDate desc");
	    query.declareParameters("String blogIDParam");
	    query.setRange(0, count + 1);
	    
	    try {
	    	List<BlogPost> posts = (List<BlogPost>) query.execute(blogID);
	    	if(posts != null) {
	    		// take care of a GAE Bug
	    		for(BlogPost post : posts) {
	    			post.getContents(); // trash this get :(
	    		}
	    		return manager.detachCopyAll(posts);
	    	}
	    	return posts;
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	query.closeAll();
	    	manager.close();
	    }
		return null;
	}

	@Override
	public Long getTotalPosts(Long blogID) {
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(BlogPost.class.getSimpleName());
		query.setKeysOnly();
		query.addFilter("blogID", FilterOperator.EQUAL, blogID);
		FetchOptions fetchOptions = FetchOptions.Builder.withOffset(0).limit(Integer.MAX_VALUE);
		PreparedQuery preparedQuery = datastoreService.prepare(query);
		int size = preparedQuery.asList(fetchOptions).size();
		
		return new Long(size);
	}

	@Override
	public BlogPost getPost(Long blogPostID, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(BlogPost.class.getSimpleName(), blogPostID); 
			BlogPost post = manager.getObjectById(BlogPost.class, key);
			if(post.getBlogID().equals(blogID)) {
				post.getContents();
				return manager.detachCopy(post);
			}			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}
}
