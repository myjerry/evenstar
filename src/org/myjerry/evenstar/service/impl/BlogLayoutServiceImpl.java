package org.myjerry.evenstar.service.impl;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import org.myjerry.evenstar.model.BlogLayout;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.BlogLayoutService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.ServerUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

public class BlogLayoutServiceImpl implements BlogLayoutService {

	@Override
	public String getBlogTemplate(Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(BlogLayout.class.getSimpleName(), blogID);
			BlogLayout layout = manager.getObjectById(BlogLayout.class, key);
			return layout.getTemplateCode().getValue();
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
	public boolean saveBlogTemplate(Long blogID, String template) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		BlogLayout layout = null;
		try {
			Key key = KeyFactory.createKey(BlogLayout.class.getSimpleName(), blogID);
			layout = manager.getObjectById(BlogLayout.class, key);
			layout.setTemplateCode(new Text(template));
			
			manager.makePersistent(layout);
			return true;
		} catch(JDOObjectNotFoundException e) {
			layout = new BlogLayout();
			layout.setBlogID(blogID);
			layout.setTemplateCode(new Text(template));
			layout.setLastUpdateTime(ServerUtils.getServerDate());
			layout.setLastUpdateUser(GAEUserUtil.getUserID());
			
			manager.makePersistent(layout);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

}
