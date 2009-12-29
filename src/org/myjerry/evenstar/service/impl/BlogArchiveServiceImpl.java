package org.myjerry.evenstar.service.impl;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.myjerry.evenstar.constants.BlogPreferenceConstants;
import org.myjerry.evenstar.enums.BlogArchiveFrequency;
import org.myjerry.evenstar.model.BlogArchive;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.BlogArchiveService;
import org.myjerry.evenstar.service.BlogPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;

public class BlogArchiveServiceImpl implements BlogArchiveService {
	
	@Autowired
	private BlogPreferenceService blogPreferenceService;

	@SuppressWarnings("unchecked")
	@Override
	public BlogArchive getBlogArchive(Long blogID) {
		if(blogID != null) {
			PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
			Query query = manager.newQuery(BlogPost.class);
		    query.setFilter("blogID == blogIDParam && postedDate != null");
		    query.setOrdering("postedDate desc");
		    query.declareParameters("String blogIDParam");
		    
		    BlogArchiveFrequency frequency = BlogArchiveFrequency.getFrequency(this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.archiveFrequency)); 
	    	BlogArchive archive = new BlogArchive(blogID, frequency);
	    	
	    	if(frequency != BlogArchiveFrequency.NONE) {
		    	try {
			    	List<BlogPost> posts = (List<BlogPost>) query.execute(blogID);
			    	
			    	if(posts != null && posts.size() > 0) {
			    		for(BlogPost post : posts) {
			    			// get the data required - the title, url, and posted date
			    			archive.addBlogArchivePost(post.getPostID(), post.getTitle(), post.getUrl(), post.getPostedDate());
			    		}
			    	}
			    	
			    } catch(Exception e) {
			    	e.printStackTrace();
			    } finally {
			    	query.closeAll();
			    	manager.close();
			    }
	    	}
	    	
	    	archive.reIndex();
	    	return archive;
		}
		return null;
	}

	@Override
	public Collection<BlogArchive> getBlogArchiveCounts(Long blogID) {
		if(blogID != null) {
//			PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
//			Query query = manager.newQuery(BlogPost.class);
//		    query.setFilter("blogID == blogIDParam && postedDate != null");
//		    query.setOrdering("postedDate desc");
//		    query.declareParameters("String blogIDParam");
//		    query.setRange(0, 1);
//		    
//		    try {
//		    	List<BlogPost> posts = (List<BlogPost>) query.execute(blogID);
//		    	if(posts != null && posts.size() == 1) {
//		    		if(posts.get(0).getPostID().equals(post.getPostID())) {
//		    			return true;
//		    		}
//		    	}
//		    } catch(Exception e) {
//		    	e.printStackTrace();
//		    } finally {
//		    	query.closeAll();
//		    	manager.close();
//		    }
		}
		return null;
	}

	@Override
	public long getNumPostsForMonth(Long blogID, int month, int year) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long getPostsForMonth(Long blogID, int month, int year) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the blogPreferenceService
	 */
	public BlogPreferenceService getBlogPreferenceService() {
		return blogPreferenceService;
	}

	/**
	 * @param blogPreferenceService the blogPreferenceService to set
	 */
	public void setBlogPreferenceService(BlogPreferenceService blogPreferenceService) {
		this.blogPreferenceService = blogPreferenceService;
	}

}
