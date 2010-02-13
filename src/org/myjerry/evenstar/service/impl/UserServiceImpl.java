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

import org.myjerry.evenstar.model.EvenstarUser;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.UserService;
import org.myjerry.util.StringUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class UserServiceImpl implements UserService {

	@Override
	public boolean addAdmin(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAdmin(Long userID) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EvenstarUser getEvenstarUser(String email) {
		if(StringUtils.isEmpty(email)) {
			return null;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(EvenstarUser.class, "email == emailParam");
			query.declareParameters("String emailParam");
			List<EvenstarUser> users = (List<EvenstarUser>) query.execute(email);
			if(users != null && users.size() == 1) {
				return manager.detachCopy(users.get(0));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}

	@Override
	public EvenstarUser getEvenstarUser(Long userID) {
		if(userID == null) {
			return null;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(EvenstarUser.class.getSimpleName(), userID);
			EvenstarUser user = manager.getObjectById(EvenstarUser.class, key);
			return manager.detachCopy(user);
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
	public Long getEvenstarUserID(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUserAdmin(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUserAdmin(Long userID) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EvenstarUser getEvenstarUserForUri(String uri) {
		if(StringUtils.isEmpty(uri)) {
			return null;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(EvenstarUser.class, "homePage == uriParam");
			query.declareParameters("String uriParam");
			List<EvenstarUser> users = (List<EvenstarUser>) query.execute(uri);
			if(users != null && users.size() == 1) {
				return manager.detachCopy(users.get(0));
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
	public Long getEvenstarUserIDForUri(String email) {
		if(StringUtils.isEmpty(email)) {
			return null;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(EvenstarUser.class, "email == emailParam");
			query.declareParameters("String emailParam");
			List<EvenstarUser> users = (List<EvenstarUser>) query.execute(email);
			if(users != null && users.size() == 1) {
				return users.get(0).getUserID();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}

	@Override
	public boolean addEvenstarUser(EvenstarUser evenstarUser) {
		if(evenstarUser == null) {
			return false;
		}
		
		if(StringUtils.isEmpty(evenstarUser.getEmail()) && StringUtils.isEmpty(evenstarUser.getHomePage())) {
			return false;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		
		EvenstarUser exist = getEvenstarUser(evenstarUser.getEmail());
		if(exist != null) {
			return false;
		}
		
		exist = getEvenstarUserForUri(evenstarUser.getHomePage());
		if(exist != null) {
			return false;
		}
		
		try {
			manager.makePersistent(evenstarUser);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		
		return false;
	}

	@Override
	public boolean isEvenstarUser(String email) {
		if(StringUtils.isEmpty(email)) {
			return false;
		}
		
		EvenstarUser user = getEvenstarUser(email);
		if(user != null) {
			return true;
		}
		
		return false;
	}
}
