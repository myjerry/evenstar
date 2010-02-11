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

import java.util.Collection;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.myjerry.evenstar.model.BlogAuthor;
import org.myjerry.evenstar.model.BlogReader;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.BlogUserService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.ServerUtils;

public class BlogUserServiceImpl implements BlogUserService {

	@Override
	public boolean isPostAllowedForUser(Long postID, Long blogID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUserBlogAdmin(Long blogID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUserBlogReader(Long blogID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addBlogAuthor(String emailAddress, Long blogID) {
		if(!existsBlogAuthor(emailAddress, blogID)) {
			BlogAuthor author = new BlogAuthor();
			author.setBlogID(blogID);
			author.setEmailAddress(emailAddress);
			author.setLastUpdatedOn(ServerUtils.getServerDate());
			author.setLastUpdatedBy(GAEUserUtil.getUserID());
			
			PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
			try {
				manager.makePersistent(author);
				
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				manager.close();
			}
		}
		return false;
	}

	@Override
	public boolean addBlogReader(String emailAddress, Long blogID) {
		if(!existsBlogReader(emailAddress, blogID)) {
			BlogReader reader = new BlogReader();
			reader.setBlogID(blogID);
			reader.setEmailAddress(emailAddress);
			reader.setLastUpdatedOn(ServerUtils.getServerDate());
			reader.setLastUpdatedBy(GAEUserUtil.getUserID());
			
			PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
			try {
				manager.makePersistent(reader);
				
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				manager.close();
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteBlogAuthor(String emailAddress, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BlogAuthor.class);
	    query.setFilter("emailAddress == emailAddressParam && blogID == blogIDParam");
	    query.declareParameters("String emailAddressParam, String blogIDParam");
	    
	    try {
	    	List<BlogAuthor> authors = (List<BlogAuthor>) query.execute(emailAddress, blogID);
	    	if(authors != null && authors.size() > 0) {
	    		for(BlogAuthor author : authors) {
	    			manager.deletePersistent(author);
	    		}
	    	}
	    	return true;
	    } catch(JDOObjectNotFoundException e) {
	    	// do nothing
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	query.closeAll();
	    	manager.close();
	    }
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteBlogReader(String emailAddress, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BlogReader.class);
	    query.setFilter("emailAddress == emailAddressParam && blogID == blogIDParam");
	    query.declareParameters("String emailAddressParam, String blogIDParam");
	    
	    try {
	    	List<BlogReader> readers = (List<BlogReader>) query.execute(emailAddress, blogID);
	    	if(readers != null && readers.size() > 0) {
	    		for(BlogReader reader : readers) {
	    			manager.deletePersistent(reader);
	    		}
	    	}
	    	return true;
	    } catch(JDOObjectNotFoundException e) {
	    	// do nothing
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	query.closeAll();
	    	manager.close();
	    }
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean existsBlogAuthor(String emailAddress, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BlogAuthor.class);
	    query.setFilter("emailAddress == emailAddressParam && blogID == blogIDParam");
	    query.declareParameters("String emailAddressParam, String blogIDParam");
	    
	    try {
	    	List<BlogAuthor> authors = (List<BlogAuthor>) query.execute(emailAddress, blogID);
	    	if(authors != null && authors.size() == 1) {
	    		return true;
	    	}
	    } catch(JDOObjectNotFoundException e) {
	    	// do nothing
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	query.closeAll();
	    	manager.close();
	    }
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean existsBlogReader(String emailAddress, Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BlogReader.class);
	    query.setFilter("emailAddress == emailAddressParam && blogID == blogIDParam");
	    query.declareParameters("String emailAddressParam, String blogIDParam");
	    
	    try {
	    	List<BlogReader> readers = (List<BlogReader>) query.execute(emailAddress, blogID);
	    	if(readers != null && readers.size() == 1) {
	    		return true;
	    	}
	    } catch(JDOObjectNotFoundException e) {
	    	// do nothing
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	query.closeAll();
	    	manager.close();
	    }
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<BlogAuthor> getBlogAuthors(Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BlogAuthor.class);
	    query.setFilter("blogID == blogIDParam");
	    query.declareParameters("String blogIDParam");
	    
	    try {
	    	List<BlogAuthor> authors = (List<BlogAuthor>) query.execute(blogID);
	    	if(authors != null && authors.size() > 0) {
	    		return manager.detachCopyAll(authors);
	    	}
	    } catch(JDOObjectNotFoundException e) {
	    	// do nothing
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	query.closeAll();
	    	manager.close();
	    }
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<BlogReader> getBlogReaders(Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		Query query = manager.newQuery(BlogAuthor.class);
	    query.setFilter("blogID == blogIDParam");
	    query.declareParameters("String blogIDParam");
	    
	    try {
	    	List<BlogReader> readers = (List<BlogReader>) query.execute(blogID);
	    	if(readers != null && readers.size() > 0) {
	    		return manager.detachCopyAll(readers);
	    	}
	    } catch(JDOObjectNotFoundException e) {
	    	// do nothing
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	query.closeAll();
	    	manager.close();
	    }
		return null;
	}

}
