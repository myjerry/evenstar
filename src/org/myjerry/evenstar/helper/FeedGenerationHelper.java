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
package org.myjerry.evenstar.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.myjerry.evenstar.model.BlogPost;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndPerson;

public class FeedGenerationHelper {

	public static SyndFeed getPostsFeed(Collection<BlogPost> posts) {
		SyndFeed feed = new SyndFeedImpl();
		List<SyndLink> links = new ArrayList<SyndLink>();
		List<SyndPerson> authors = new ArrayList<SyndPerson>();
		
		feed.setTitle("");
		feed.setDescription("");
		feed.setPublishedDate(null);
		feed.setAuthors(authors);
		feed.setLinks(links);
		
		return feed;
	}

	public static SyndFeed getPostFeed(BlogPost post) {
		// TODO Auto-generated method stub
		return null;
	}

}
