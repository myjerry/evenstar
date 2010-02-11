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

import java.util.Collection;

import org.myjerry.evenstar.model.BlogAuthor;
import org.myjerry.evenstar.model.BlogReader;


public interface BlogUserService {
	
	public boolean isUserBlogAdmin(Long blogID);
	
	public boolean isUserBlogReader(Long blogID);
	
	public boolean isPostAllowedForUser(Long postID, Long blogID);
	

	public boolean addBlogAuthor(String emailAddress, Long blogID);
	
	public boolean deleteBlogAuthor(String emailAddress, Long blogID);
	
	public boolean existsBlogAuthor(String emailAddress, Long blogID);
	
	public Collection<BlogAuthor> getBlogAuthors(Long blogID);
	
	
	public boolean addBlogReader(String emailAddress, Long blogID);

	public boolean deleteBlogReader(String emailAddress, Long blogID);
	
	public boolean existsBlogReader(String emailAddress, Long blogID);
	
	public Collection<BlogReader> getBlogReaders(Long blogID);
	
}
