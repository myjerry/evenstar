package org.myjerry.evenstar.service.impl;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import org.myjerry.evenstar.model.Preference;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.PreferenceService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.ServerUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class PreferenceServiceImpl implements PreferenceService {

	@Override
	public boolean checkPreferenceExists(String key) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key k = KeyFactory.createKey(Preference.class.getSimpleName(), key);
			Preference pref = manager.getObjectById(Preference.class, KeyFactory.keyToString(k));
			return true;
		} catch(JDOObjectNotFoundException e) {
			// object not found
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	@Override
	public boolean deletePreference(String key) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager(); 
		try {
			Key k = KeyFactory.createKey(Preference.class.getSimpleName(), key);
			Preference pref = manager.getObjectById(Preference.class, KeyFactory.keyToString(k));
			manager.deletePersistent(pref);
			return true;
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
	public String getPreference(String key) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager(); 
		try {
			Key k = KeyFactory.createKey(Preference.class.getSimpleName(), key);
			Preference pref = manager.getObjectById(Preference.class, KeyFactory.keyToString(k));
			return pref.getValue();
		} catch(JDOObjectNotFoundException e) {
			return null;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}

	@Override
	public boolean insertPreference(String key, String value) {
		if(!checkPreferenceExists(key)) {
			return setPreference(key, value);
		}
		return false;
	}

	@Override
	public boolean setPreference(String key, String value) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Preference pref = null;
		try {
			Key k = KeyFactory.createKey(Preference.class.getSimpleName(), key);
			pref = manager.getObjectById(Preference.class, KeyFactory.keyToString(k));

			pref.setValue(value);
			pref.setLastUpdateUser(GAEUserUtil.getUserID());
			pref.setLastUpdateTime(ServerUtils.getServerDate());
			return true;
		} catch(JDOObjectNotFoundException e) {
			// create new preference
			pref = new Preference();
			pref.setKey(key);
			pref.setValue(value);
			pref.setLastUpdateUser(GAEUserUtil.getUserID());
			pref.setLastUpdateTime(ServerUtils.getServerDate());
			manager.makePersistent(pref);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	@Override
	public boolean updatePreference(String key, String value) {
		if(checkPreferenceExists(key)) {
			return setPreference(key, value);
		}
		return false;
	}

}
