package org.myjerry.evenstar.service.impl;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.myjerry.evenstar.model.EvenstarUser;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.UserService;
import org.myjerry.util.StringUtils;

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
		// TODO Auto-generated method stub
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
}
