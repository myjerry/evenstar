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
import java.util.Date;

import org.myjerry.evenstar.model.BlogPost;

public interface BlogPostService {
	
	public boolean publishPost(BlogPost blogPost);
	
	public boolean schedulePost(BlogPost blogPost);
	
	public boolean saveDraftPost(BlogPost blogPost);

	public boolean deletePost(Long postID, Long blogID);

	public boolean unPublishPost(Long postID, Long blogID);
	
	public BlogPost getPost(Long postID, Long blogID);
	
	public Collection<BlogPost> getAllBlogPosts(Long blogID);
	
	public Collection<BlogPost> getBlogPosts(Long blogID, int count);
	
	public Collection<BlogPost> getBlogPosts(Long blogID, int count, Long olderThan, Long newerThan);
	
	public Collection<BlogPost> getBlogPostsForLabel(Long blogID, Long labelID, int count);
	
	public Collection<BlogPost> getBlogPostsForLabel(Long blogID, Long labelID, int count, Long olderThan, Long newerThan);
	
	public Collection<BlogPost> getOlderBlogPosts(Long blogID, int count, Date lastUpdated);
	
	public Collection<BlogPost> getNewerBlogPosts(Long blogID, int count, Date lastUpdated);
	
	public Long getTotalPosts(Long blogID);
	
	public boolean existsPost(Long postID, Long blogID);
	
	public BlogPost getPostForURI(String uri);
	
	public boolean isFirstPost(BlogPost post);

	public Date getLastPublishedPostDate(Long blogID);

	public String getOlderPostUrl(Long blogID, Date date);

	public String getNewerPostUrl(Long blogID,Date date);

}
