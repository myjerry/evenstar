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
	public boolean createBlog(String blogName, String blogAddress, String blogAlias) {
		// TODO Auto-generated method stub
		Blog blog = new Blog();
		blog.setTitle(blogName);
		blog.setAddress(blogAddress);
		blog.setAlias(blogAlias);
		
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
	public boolean deleteBlog(Long blogID) {
		if(blogID == null) {
			return false;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(Blog.class.getSimpleName(), blogID);
			Blog blog = manager.getObjectById(Blog.class, key);
			manager.deletePersistent(blog);
			return true;
		} catch(JDOObjectNotFoundException e) {
			// do nothing
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean existsBlogAddress(String blogAddress) {
		if(StringUtils.isEmpty(blogAddress)) {
			return false;
		}
		
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
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean existsBlogAlias(String alias) {
		if(StringUtils.isEmpty(alias)) {
			return false;
		}

		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(Blog.class, "alias == aliasParam");
			query.declareParameters("String aliasParam");
			
			List<Blog> blogs = (List<Blog>) query.execute(alias);
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

	@SuppressWarnings("unchecked")
	@Override
	public boolean existsBlogName(String blogTitle) {
		if(StringUtils.isEmpty(blogTitle)) {
			return false;
		}

		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(Blog.class, "title == titleParam");
			query.declareParameters("String titleParam");
			
			List<Blog> blogs = (List<Blog>) query.execute(blogTitle);
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
		if(blogID == null) {
			return null;
		}
		
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

	@Override
	public boolean updateBlog(Blog blog) {
		if(blog == null) {
			return false;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(Blog.class.getSimpleName(), blog.getBlogID());
			Blog b = manager.getObjectById(Blog.class, key);

			// hack for GAE to fetch text fields
			b.setAddress(blog.getAddress());
			b.setAlias(blog.getAlias());
			b.setDescription(blog.getDescription());
			b.setTitle(blog.getTitle());
			b.setRestrictedPostText(blog.getRestrictedPostText());
			
			manager.makePersistent(b);
			return true;
		} catch(JDOObjectNotFoundException e) {
			// do nothing
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getBlogIDForServerName(String serverName) {
		if(StringUtils.isEmpty(serverName)) {
			return null;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(Blog.class, "address == addressParam");
			query.declareParameters("String addressParam");
			
			List<Blog> blogs = (List<Blog>) query.execute(serverName);
			if(blogs != null && blogs.size() > 0) {
				return blogs.get(0).getBlogID();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}

}
