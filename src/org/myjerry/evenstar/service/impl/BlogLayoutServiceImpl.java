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

import java.io.IOException;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import org.myjerry.evenstar.model.BlogLayout;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.BlogLayoutService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.ServerUtils;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

public class BlogLayoutServiceImpl implements BlogLayoutService {

	@Autowired
	private Resource defaultTemplate; 

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

	@Override
	public String getDefaultBlogTemplate() {
		try {
			return StringUtils.getString(this.defaultTemplate.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return the defaultTemplate
	 */
	public Resource getDefaultTemplate() {
		return defaultTemplate;
	}

	/**
	 * @param defaultTemplate the defaultTemplate to set
	 */
	public void setDefaultTemplate(Resource defaultTemplate) {
		this.defaultTemplate = defaultTemplate;
	}

}
