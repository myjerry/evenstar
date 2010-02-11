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
			if(preferences != null) {
				if(preferences.size() == 1) {
					pref = preferences.get(0);
					pref.setValue(value);
					pref.setLastUpdateUser(GAEUserUtil.getUserID());
					pref.setLastUpdateTime(ServerUtils.getServerDate());
					manager.makePersistent(pref);
					return true;
				} else if(preferences.size() == 0) {
					pref = new BlogPreference();
					pref.setBlogID(blogID);
					pref.setKey(key);
					pref.setValue(value);
					pref.setLastUpdateUser(GAEUserUtil.getUserID());
					pref.setLastUpdateTime(ServerUtils.getServerDate());
					manager.makePersistent(pref);
					return true;
				} else {
					// this cannot be - two preferences with same ID
					// just can't be
				}
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
