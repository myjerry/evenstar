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
package org.myjerry.evenstar.persistence;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public final class PersistenceManagerFactoryImpl {

	private static final PersistenceManagerFactory instance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	private PersistenceManagerFactoryImpl() {
		// make it a singleton
	}
	
	public static PersistenceManagerFactory getFactory() {
		return instance;
	}
	
	public static PersistenceManager getPersistenceManager() {
		return instance.getPersistenceManager();
	}
}
