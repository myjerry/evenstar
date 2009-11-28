package org.myjerry.evenstar.service.impl;

import java.util.Collection;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.myjerry.evenstar.constants.PreferenceConstants;
import org.myjerry.evenstar.model.Blog;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.evenstar.service.PreferenceService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.ServerUtils;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class BlogServiceImpl implements BlogService {
	
	@Autowired
	private PreferenceService preferenceService;
	
	@Override
	public boolean createBlog(String blogName, String blogAddress) {
		// TODO Auto-generated method stub
		Blog blog = new Blog();
		blog.setTitle(blogName);
		blog.setAddress(blogAddress);
		blog.setCreationDate(ServerUtils.getServerDate());
		blog.setOwnerID(GAEUserUtil.getUserID());
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			manager.makePersistent(blog);
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			manager.close();
		}
	}

	@Override
	public boolean deleteBlog(String blogName) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean existsBlogAddress(String blogAddress) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(Blog.class, "address == addressParam");
			query.declareParameters("String addressParam");
			
			List<Blog> blogs = (List<Blog>) query.execute(blogAddress);
			if(blogs != null && blogs.size() > 0) {
				return true;
			}
			return false;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return true;
	}

	@Override
	public boolean existsBlogName(String blogTitle) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Blog> getAllBlogs() {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			List<Blog> blogs = (List<Blog>) manager.newQuery("select from " + Blog.class.getName()).execute();
			return manager.detachCopyAll(blogs);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			manager.close();
		}
	}

	@Override
	public Long getDefaultBlogID() {
		String blogID = this.preferenceService.getPreference(PreferenceConstants.DEFAULT_BLOG_ID);
		if(StringUtils.isNotEmpty(blogID)) {
			return new Long(blogID);
		}
		return null;
	}

	@Override
	public boolean setDefaultBlogID(Long blogID) {
		if(blogID != null) {
			return this.preferenceService.setPreference(PreferenceConstants.DEFAULT_BLOG_ID, String.valueOf(blogID));
		} else {
			return this.preferenceService.deletePreference(PreferenceConstants.DEFAULT_BLOG_ID);
		}
	}

	/**
	 * @return the preferenceService
	 */
	public PreferenceService getPreferenceService() {
		return preferenceService;
	}

	/**
	 * @param preferenceService the preferenceService to set
	 */
	public void setPreferenceService(PreferenceService preferenceService) {
		this.preferenceService = preferenceService;
	}

	@Override
	public Blog getBlog(Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(Blog.class.getSimpleName(), blogID);
			Blog blog = manager.getObjectById(Blog.class, key);
			// hack for GAE to fetch text fields
			blog.getDescription();
			return manager.detachCopy(blog);
		} catch(JDOObjectNotFoundException e) {
			// do nothing
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}

}
