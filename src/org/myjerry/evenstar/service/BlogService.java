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

import org.myjerry.evenstar.enums.BlogImportType;
import org.myjerry.evenstar.model.Blog;

public interface BlogService {
	
	public boolean createBlog(String blogName, String blogAddress, String blogAlias);
	
	public boolean deleteBlog(Long blogID);

	public boolean existsBlogName(String blogTitle);

	public boolean existsBlogAddress(String blogAddress);
	
	public boolean existsBlogAlias(String alias);
	
	public Collection<Blog> getAllBlogs();
	
	public Long getDefaultBlogID();

	public boolean setDefaultBlogID(Long blogID);
	
	public Blog getBlog(Long blogID);

	public boolean updateBlog(Blog blog);

	public Long getBlogIDForServerName(String serverName);
	
	public Long getBlogIDForAlias(String alias);
	
	public boolean importBlog(Long blogID, BlogImportType importType, String blogData, boolean publishPosts);
	
}
