package org.myjerry.evenstar.service;

import java.util.Collection;

import org.myjerry.evenstar.model.BlogLabel;
import org.myjerry.evenstar.model.BlogPost;

public interface BlogLabelService {
	
	public Collection<BlogLabel> getBlogLabels(Long blogID);
	
	public void updatePostLabels(Long blogID, Long postID, String oldLabels, String newLabels);
	
	public Integer getNumPostsForLabel(Long blogID, String label);

	public void deleteLabelsForPost(Long blogID, Long postID, String labels);
	
	public Collection<BlogPost> getPostsForLabel(Long blogID, String label);
	
	public Collection<Long> getPostIDsForLabel(Long blogID, String label);
	
	public Long getLabelID(Long blogID, String label);
	
	public BlogLabel getLabel(Long blogID, String label);
}
