package org.myjerry.evenstar.service.impl;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.myjerry.evenstar.model.Blog;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.ServerUtils;

public class BlogServiceImpl implements BlogService {
	
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

}
