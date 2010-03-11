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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.myjerry.evenstar.helper.SortHelper;
import org.myjerry.evenstar.model.Blog;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.model.BlogPostLabelMapping;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.BlogLabelService;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.evenstar.service.UserService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.ServerUtils;
import org.myjerry.util.StringUtils;
import org.myjerry.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class BlogPostServiceImpl implements BlogPostService {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private BlogLabelService blogLabelService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean publishPost(BlogPost blogPost) {
		if(blogPost.getCreationDate() == null) {
			blogPost.setCreationDate(ServerUtils.getServerDate());
		}
		blogPost.setLastUpdated(ServerUtils.getServerDate());
		blogPost.setLastUpdateUser(StringUtils.getLong(GAEUserUtil.getUserID()));
		if(blogPost.getPostedDate() == null) {
			blogPost.setPostedDate(ServerUtils.getServerDate());
		}

		if(StringUtils.isEmpty(blogPost.getUrl())) {
			// this also means that we need to create a new URL for this service
			int year = blogPost.getPostedDate().getYear() + 1900;
			int month = blogPost.getPostedDate().getMonth() + 1;
			String prefix = WebUtils.getUrlStringFromPostTitle(blogPost.getTitle());
			String url = null;
			if(StringUtils.isNotEmpty(prefix)) {
				url = "/" + year + "/" + month + "/" + prefix;
			}
			
			Blog blog = this.blogService.getBlog(blogPost.getBlogID());
			if(StringUtils.isNotEmpty(blog.getAlias()) && StringUtils.isNotEmpty(url)) {
				url = "/" + blog.getAlias() + url;
			}
			
			blogPost.setUrl(url);
			if(StringUtils.isEmpty(url)) {
				blogPost.setPostedDate(null);
			}
		} else {
			char firstChar = blogPost.getUrl().charAt(0);
			if(firstChar != '/') {
				blogPost.setUrl("/" + blogPost.getUrl());
			}
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		
		try {
			if(blogPost.getPostID() == null) {
				// create a new post
				manager.makePersistent(blogPost);
				
				// update the labels
				this.blogLabelService.updatePostLabels(blogPost.getBlogID(), blogPost.getPostID(), null, blogPost.getLabels());
			} else {
				// this post should be updated
				Key key = KeyFactory.createKey(BlogPost.class.getSimpleName(), blogPost.getPostID());
				BlogPost post = manager.getObjectById(BlogPost.class, key);

				String oldLabels = post.getLabels();
				
				post.setContents(blogPost.getContents());
				post.setLabels(blogPost.getLabels());
				post.setTitle(blogPost.getTitle());
				post.setUrl(blogPost.getUrl());
				post.setLastUpdated(blogPost.getLastUpdated());
				post.setLastUpdateUser(blogPost.getLastUpdateUser());
				post.setPostedDate(blogPost.getPostedDate());
				post.setPrivacyMode(blogPost.getPrivacyMode());

				// update the labels
				this.blogLabelService.updatePostLabels(blogPost.getBlogID(), blogPost.getPostID(), oldLabels, blogPost.getLabels());

				manager.makePersistent(post);
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	@Override
	public boolean deletePost(Long blogPostID, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(BlogPost.class.getSimpleName(), blogPostID); 
			BlogPost post = manager.getObjectById(BlogPost.class, key);
			
			if(post.getBlogID().equals(blogID)) {
				// delete labels for post
				this.blogLabelService.deleteLabelsForPost(post.getBlogID(), post.getPostID(), post.getLabels());
				
				// delete the post itself
				manager.deletePersistent(post);
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
	public boolean saveDraftPost(BlogPost blogPost) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		
		try {
			if(blogPost.getPostID() == null) {
				// create a new post
				if(blogPost.getCreationDate() == null) {
					blogPost.setCreationDate(ServerUtils.getServerDate());
				}
				blogPost.setLastUpdated(ServerUtils.getServerDate());
				blogPost.setLastUpdateUser(StringUtils.getLong(GAEUserUtil.getUserID()));
				
				manager.makePersistent(blogPost);
			} else {
				// this post should be updated
				Key key = KeyFactory.createKey(BlogPost.class.getSimpleName(), blogPost.getPostID()); 
				BlogPost post = manager.getObjectById(BlogPost.class, key);
				// overwrite this post object
				post.setContents(blogPost.getContents());
				post.setLabels(blogPost.getLabels());
				post.setLastUpdated(ServerUtils.getServerDate());
				post.setLastUpdateUser(StringUtils.getLong(GAEUserUtil.getUserID()));
				post.setTitle(blogPost.getTitle());
				post.setPrivacyMode(blogPost.getPrivacyMode());
				
				manager.makePersistent(post);
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
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
				post.setPostedDate(null);
				
				// delete labels for post
				this.blogLabelService.deleteLabelsForPost(post.getBlogID(), post.getPostID(), post.getLabels());
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
		} finally {
			manager.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<BlogPost> getBlogPosts(Long blogID, int count) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BlogPost.class);
	    query.setFilter("blogID == blogIDParam && postedDate != null");
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
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<BlogPost> getBlogPosts(Long blogID, int count, Long olderThan, Long newerThan) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BlogPost.class);
	    query.setFilter("blogID == blogIDParam && postedDate != null");
	    query.setOrdering("postedDate desc");
	    query.declareParameters("String blogIDParam");
	    
	    try {
	    	List<BlogPost> allPosts = (List<BlogPost>) query.execute(blogID);
	    	if(allPosts != null && allPosts.size() > 0) {

	    		List<BlogPost> posts = new ArrayList<BlogPost>();
	    		int length = count + 1;
	    		if(length > allPosts.size()) {
	    			length = allPosts.size();
	    		}

	    		for(int i=0; i < allPosts.size(); i++) {
	    			BlogPost post = allPosts.get(i);
	    			Date posted = post.getPostedDate();
	    			if(posted != null) {
	    				long time = posted.getTime();
	    				if(olderThan != null && time > olderThan) {
	    					continue;
	    				}
	    				if(newerThan != null && time < newerThan) {
	    					continue;
	    				}
	    				
	    				post.getContents();
	    				post = manager.detachCopy(post);
	    				posts.add(post);
	    				
	    				if(posts.size() == length) {
	    					break;
	    				}
	    			}
	    		}
	    		
	    		return posts;
	    	}
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	query.closeAll();
	    	manager.close();
	    }
		return null;
	}

	@Override
	public Collection<BlogPost> getBlogPostsForLabel(Long blogID, Long labelID, int count) {
		return getBlogPostsForLabel(blogID, labelID, count, null, null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<BlogPost> getBlogPostsForLabel(Long blogID, Long labelID, int count, Long olderThan, Long newerThan) {
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

		// get a list of all IDs that match this label
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(BlogPostLabelMapping.class.getSimpleName());
		query.addFilter("blogID", FilterOperator.EQUAL, blogID);
		query.addFilter("labelID", FilterOperator.EQUAL, labelID);
		FetchOptions fetchOptions = FetchOptions.Builder.withOffset(0).limit(Integer.MAX_VALUE);
		PreparedQuery preparedQuery = datastoreService.prepare(query);
		List<Entity> list = preparedQuery.asList(fetchOptions);
		
		List<Long> postIDs = new ArrayList<Long>();
		if(list.size() > 0) {
			for(Entity entity : list) {
				Long id = (Long) entity.getProperty("postID");
				postIDs.add(id);
			}
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query q = manager.newQuery(BlogPost.class, ":p.contains(postID)");
		List<BlogPost> allPosts = (List<BlogPost>) q.execute(postIDs);
		SortHelper.sortPosts(allPosts);
		
		// get first count + 1 objects
		List<BlogPost> posts = new ArrayList<BlogPost>();
		int length = count + 1;
		if(length > allPosts.size()) {
			length = allPosts.size();
		}

		for(int i=0; i < allPosts.size(); i++) {
			BlogPost post = allPosts.get(i);
			Date posted = post.getPostedDate();
			if(posted != null) {
				long time = posted.getTime();
				if(olderThan != null && time > olderThan) {
					continue;
				}
				if(newerThan != null && time < newerThan) {
					continue;
				}
				
				post.getContents();
				post = manager.detachCopy(post);
				posts.add(post);
				
				if(posts.size() == length) {
					break;
				}
			}
		}
		
		return posts;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<BlogPost> getOlderBlogPosts(Long blogID, int count, Date lastUpdated) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BlogPost.class);
	    query.setFilter("blogID == blogIDParam && postedDate != null && postedDate <= postedDateParam");
	    query.setOrdering("postedDate desc");
	    query.declareImports("import java.util.Date");
	    query.declareParameters("String blogIDParam, Date postedDateParam");
	    query.setRange(0, count + 1);
	    
	    try {
	    	List<BlogPost> posts = (List<BlogPost>) query.execute(blogID, lastUpdated);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<BlogPost> getNewerBlogPosts(Long blogID, int count, Date lastUpdated) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BlogPost.class);
	    query.setFilter("blogID == blogIDParam && postedDate != null && postedDate >= postedDateParam");
	    query.setOrdering("postedDate asc");
	    query.declareImports("import java.util.Date");
	    query.declareParameters("String blogIDParam, Date postedDateParam");
	    query.setRange(0, count + 1);
	    
	    try {
	    	List<BlogPost> posts = (List<BlogPost>) query.execute(blogID, lastUpdated);
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
		if(blogPostID == null || blogID == null) {
			return null;
		}
		
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

	@Override
	public boolean existsPost(Long postID, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(BlogPost.class.getSimpleName(), postID); 
			BlogPost post = manager.getObjectById(BlogPost.class, key);
			if(post.getBlogID().equals(blogID)) {
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;	
	}

	@SuppressWarnings("unchecked")
	@Override
	public BlogPost getPostForURI(String uri) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BlogPost.class, "url == urlParam");
		query.declareParameters("String urlParam");
		try {
			List<BlogPost> posts = (List<BlogPost>) query.execute(uri);
			if(posts != null && posts.size() == 1) {
				return manager.detachCopy(posts.get(0));
			}
		} catch(JDOObjectNotFoundException e) {
			// do nothing
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isFirstPost(BlogPost post) {
		if(post != null) {
			Long blogID = post.getBlogID();
			PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
			Query query = manager.newQuery(BlogPost.class);
		    query.setFilter("blogID == blogIDParam && postedDate != null");
		    query.setOrdering("postedDate desc");
		    query.declareParameters("String blogIDParam");
		    query.setRange(0, 1);
		    
		    try {
		    	List<BlogPost> posts = (List<BlogPost>) query.execute(blogID);
		    	if(posts != null && posts.size() == 1) {
		    		if(posts.get(0).getPostID().equals(post.getPostID())) {
		    			return true;
		    		}
		    	}
		    } catch(Exception e) {
		    	e.printStackTrace();
		    } finally {
		    	query.closeAll();
		    	manager.close();
		    }
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Date getLastPublishedPostDate(Long blogID) {
		if(blogID == null) {
			return null;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BlogPost.class, "blogID == blogIDParam && postedDate != null");
		query.setOrdering("postedDate desc");
	    query.declareParameters("String blogIDParam");
	    query.setRange(0, 1);
	    
	    try {
	    	List<BlogPost> posts = (List<BlogPost>) query.execute(blogID);
	    	if(posts != null && posts.size() == 1) {
	    		return posts.get(0).getPostedDate();
	    	}
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
	public String getNewerPostUrl(Long blogID, Date date) {
		if(blogID != null && date != null) {
			PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
			Query query = manager.newQuery(BlogPost.class, "blogID == blogIDParam && postedDate > postedDateParam");
			query.setOrdering("postedDate asc");
			query.declareImports("import java.util.Date");
		    query.declareParameters("Long blogIDParam, Date postedDateParam");
		    query.setRange(0, 1);
			
		    try {
		    	List<BlogPost> posts = (List<BlogPost>) query.execute(blogID, date);
		    	if(posts != null && posts.size() == 1) {
		    		return posts.get(0).getUrl();
		    	}
		    } catch(Exception e) {
		    	e.printStackTrace();
		    } finally {
		    	query.closeAll();
		    	manager.close();
		    }
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getOlderPostUrl(Long blogID, Date date) {
		if(blogID != null && date != null) {
			PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
			Query query = manager.newQuery(BlogPost.class, "blogID == blogIDParam && postedDate < postedDateParam");
			query.setOrdering("postedDate desc");
			query.declareImports("import java.util.Date");
		    query.declareParameters("Long blogIDParam, Date postedDateParam");
		    query.setRange(0, 1);
			
		    try {
		    	List<BlogPost> posts = (List<BlogPost>) query.execute(blogID, date);
		    	if(posts != null && posts.size() == 1) {
		    		return posts.get(0).getUrl();
		    	}
		    } catch(Exception e) {
		    	e.printStackTrace();
		    } finally {
		    	query.closeAll();
		    	manager.close();
		    }
		}
		return null;
	}

	/**
	 * @return the blogService
	 */
	public BlogService getBlogService() {
		return blogService;
	}

	/**
	 * @param blogService the blogService to set
	 */
	public void setBlogService(BlogService blogService) {
		this.blogService = blogService;
	}

	/**
	 * @return the blogLabelService
	 */
	public BlogLabelService getBlogLabelService() {
		return blogLabelService;
	}

	/**
	 * @param blogLabelService the blogLabelService to set
	 */
	public void setBlogLabelService(BlogLabelService blogLabelService) {
		this.blogLabelService = blogLabelService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
