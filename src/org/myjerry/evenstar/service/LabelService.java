package org.myjerry.evenstar.service;

import java.util.Collection;

import org.myjerry.evenstar.model.BlogLabels;

public interface LabelService {

	public Collection<BlogLabels> getBlogLabels(Long blogID);
	
	public boolean addBlogLabels(Collection<BlogLabels> labels);
	
	public int getPostsTaggedWithLabel(Long blogID);
	
}
