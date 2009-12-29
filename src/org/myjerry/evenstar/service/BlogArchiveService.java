package org.myjerry.evenstar.service;

import java.util.Collection;

import org.myjerry.evenstar.model.BlogArchive;

public interface BlogArchiveService {
	
	public Collection<BlogArchive> getBlogArchiveCounts(Long blogID);
	
	public BlogArchive getBlogArchive(Long blogID);
	
	public long getNumPostsForMonth(Long blogID, int month, int year);
	
	public Long getPostsForMonth(Long blogID, int month, int year);

}
