package org.myjerry.evenstar.service;

import java.util.List;
import java.util.Map;

import org.myjerry.evenstar.model.Blog;
import org.myjerry.evenstar.model.BlogPost;

public interface ViewPostService {
	
	public Map<String, Object> getPostsViewModel(Blog blog, List<BlogPost> posts, boolean fetchComments, Map<String, Object> model);

}
