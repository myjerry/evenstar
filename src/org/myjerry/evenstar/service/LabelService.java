package org.myjerry.evenstar.service;

import java.util.Collection;

import org.myjerry.evenstar.model.BlogLabel;

public interface LabelService {

	public Collection<BlogLabel> getBlogLabels(Long blogID);
	
	public boolean addBlogLabels(Collection<BlogLabel> labels);
	
	public int getPostsTaggedWithLabel(Long blogID);
	
}
