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
package org.myjerry.evenstar.service;

import org.myjerry.evenstar.model.EvenstarUser;

public interface UserService {

	public Long getEvenstarUserID(String email);
	
	public Long getEvenstarUserIDForUri(String uri);
	
	public EvenstarUser getEvenstarUser(String email);
	
	public EvenstarUser getEvenstarUserForUri(String uri);

	public EvenstarUser getEvenstarUser(Long userID);

	public boolean isUserAdmin(String email);
	
	public boolean isUserAdmin(Long userID);
	
	public boolean addAdmin(String email);
	
	public boolean addAdmin(Long userID);
	
	public boolean addEvenstarUser(EvenstarUser evenstarUser);
	
}
