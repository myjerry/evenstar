package org.myjerry.evenstar.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.ServerUtils;

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
			manager.makePersistent(blogPost);
			
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveDraftPost(BlogPost blogPost) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean schedulePost(BlogPost blogPost) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unPublishPost(Long blogPostID, Long blogID) {
		// TODO Auto-generated method stub
		return false;
	}

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

	@Override
	public Collection<BlogPost> getBlogPosts(Long blogID, long page, long count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTotalPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BlogPost getPost(Long blogPostID, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Collection<String> arguments = new ArrayList<String>();
			arguments.add("blogID == blogIDParam");
			arguments.add("postID == blogPostIDParam");
			Query query = manager.newQuery(BlogPost.class, arguments);
			query.declareParameters("Long blogIDParam, Long blogPostIDParam");
			
			BlogPost post = ((List<BlogPost>) query.execute(blogID, blogPostID)).get(0);
			post.getContents();
			
			return manager.detachCopy(post);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			manager.close();
		}
	}

}
