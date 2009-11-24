package org.myjerry.evenstar.service.impl;

import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.myjerry.evenstar.model.BlogPreference;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.BlogPreferenceService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.ServerUtils;

public class BlogPreferenceServiceImpl implements BlogPreferenceService {

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkPreferenceExists(Long blogID, String key) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query q = manager.newQuery(BlogPreference.class, "blogID == blogIDParam && key == keyParam");
			q.declareParameters("Long blogIDParam, String keyParam");
			List<BlogPreference> preferences = (List<BlogPreference>) q.execute(blogID, key);
			if(preferences != null && preferences.size() == 1) {
				return true;
			}
		} catch(JDOObjectNotFoundException e) {
			return false;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean deletePreference(Long blogID, String key, String value) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query q = manager.newQuery(BlogPreference.class, "blogID == blogIDParam && key == keyParam");
			q.declareParameters("Long blogIDParam, String keyParam");
			List<BlogPreference> preferences = (List<BlogPreference>) q.execute(blogID, key);
			if(preferences != null && preferences.size() == 1) {
				BlogPreference pref = preferences.get(0);
				manager.deletePersistent(pref);
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
	public String getPreference(Long blogID, String key) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query q = manager.newQuery(BlogPreference.class, "blogID == blogIDParam && key == keyParam");
			q.declareParameters("Long blogIDParam, String keyParam");
			List<BlogPreference> preferences = (List<BlogPreference>) q.execute(blogID, key);
			if(preferences != null && preferences.size() == 1) {
				return preferences.get(0).getValue();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean insertPreference(Long blogID, String key, String value) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		BlogPreference pref = null;
		try {
			Query q = manager.newQuery(BlogPreference.class, "blogID == blogIDParam && key == keyParam");
			q.declareParameters("Long blogIDParam, String keyParam");
			List<BlogPreference> preferences = (List<BlogPreference>) q.execute(blogID, key);
			if(preferences != null && preferences.size() < 1) {
				pref = new BlogPreference();
				pref.setBlogID(blogID);
				pref.setKey(key);
				pref.setValue(value);
				pref.setLastUpdateUser(GAEUserUtil.getUserID());
				pref.setLastUpdateTime(ServerUtils.getServerDate());
				manager.makePersistent(pref);
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
	public boolean putPreference(Long blogID, String key, String value) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		BlogPreference pref = null;
		try {
			Query q = manager.newQuery(BlogPreference.class, "blogID == blogIDParam && key == keyParam");
			q.declareParameters("Long blogIDParam, String keyParam");
			List<BlogPreference> preferences = (List<BlogPreference>) q.execute(blogID, key);
			if(preferences != null && preferences.size() == 1) {
				pref = preferences.get(0);
				pref.setValue(value);
				pref.setLastUpdateUser(GAEUserUtil.getUserID());
				pref.setLastUpdateTime(ServerUtils.getServerDate());
			} else {
				pref = new BlogPreference();
				pref.setBlogID(blogID);
				pref.setKey(key);
				pref.setValue(value);
				pref.setLastUpdateUser(GAEUserUtil.getUserID());
				pref.setLastUpdateTime(ServerUtils.getServerDate());
				manager.makePersistent(pref);
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updatePreference(Long blogID, String key, String value) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		BlogPreference pref = null;
		try {
			Query q = manager.newQuery(BlogPreference.class, "blogID == blogIDParam && key == keyParam");
			q.declareParameters("Long blogIDParam, String keyParam");
			List<BlogPreference> preferences = (List<BlogPreference>) q.execute(blogID, key);
			if(preferences != null && preferences.size() == 1) {
				pref = preferences.get(0);
				pref.setValue(value);
				pref.setLastUpdateUser(GAEUserUtil.getUserID());
				pref.setLastUpdateTime(ServerUtils.getServerDate());
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

}
