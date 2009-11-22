package org.myjerry.evenstar.service.impl;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.myjerry.evenstar.model.Preference;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.PreferenceService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.ServerUtils;

public class PreferenceServiceImpl implements PreferenceService {

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkPreferenceExists(String key) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(Preference.class, "key == keyParam");
			query.declareParameters("String keyParam");
			
			List<Preference> preferences = (List<Preference>) query.execute(key);
			if(preferences != null && preferences.size() > 0) {
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
	public boolean deletePreference(String key) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager(); 
		try {
			Query query = manager.newQuery(Preference.class, "key == keyParam");
			query.declareParameters("String keyParam");
			
			List<Preference> preferences = (List<Preference>) query.execute(key);
			manager.deletePersistentAll(preferences);
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
	public String getPreference(String key) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager(); 
		try {
			Query query = manager.newQuery(Preference.class, "key == keyParam");
			query.declareParameters("String keyParam");
			
			List<Preference> preferences = (List<Preference>) query.execute(key);
			if(preferences != null && preferences.size() == 1) {
				Preference pref = preferences.get(0);
				return pref.getValue();
			}
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

	@SuppressWarnings("unchecked")
	@Override
	public boolean setPreference(String key, String value) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Preference pref = null;
		try {
			Query query = manager.newQuery(Preference.class, "key == keyParam");
			query.declareParameters("String keyParam");
			
			List<Preference> preferences = (List<Preference>) query.execute(key);
			if(preferences != null && preferences.size() == 1) {
				pref = preferences.get(0);
			}
			if(pref == null) {
				pref = new Preference();
				pref.setKey(key);
				pref.setValue(value);
				pref.setLastUpdateUser(GAEUserUtil.getUserID());
				pref.setLastUpdateTime(ServerUtils.getServerDate());
				manager.makePersistent(pref);
			} else {
				pref.setValue(value);
				pref.setLastUpdateUser(GAEUserUtil.getUserID());
				pref.setLastUpdateTime(ServerUtils.getServerDate());
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
	public boolean updatePreference(String key, String value) {
		if(checkPreferenceExists(key)) {
			return setPreference(key, value);
		}
		return false;
	}

}
