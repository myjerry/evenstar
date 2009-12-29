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
