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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.myjerry.evenstar.model.BlogPost;

public class SortHelper {
	
	public static final void sortPosts(List<BlogPost> posts) {
		Collections.sort(posts, new Comparator<BlogPost>() {
			
			@Override
			public int compare(BlogPost o1, BlogPost o2) {
				if(o1 != null && o2 != null) {
					return 0 - o1.getPostedDate().compareTo(o2.getPostedDate());
				} else if(o1 == null && o2 == null) {
					return 0;
				} else if(o1 == null) {
					return 1;
				}
				// o2 is null
				return -1;
			}
			
		});
	}

}
