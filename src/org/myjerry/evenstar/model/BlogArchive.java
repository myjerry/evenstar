package org.myjerry.evenstar.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.myjerry.evenstar.constants.CalendarConstants;
import org.myjerry.evenstar.core.ArchiveKey;
import org.myjerry.evenstar.enums.BlogArchiveFrequency;

public class BlogArchive {
	
	private Long blogID;
	
	private BlogArchiveFrequency frequency;
	
	private List<BlogArchivePost> archives = new ArrayList<BlogArchivePost>();
	
	@SuppressWarnings("unchecked")
	private Map data;
	
	public BlogArchive(Long blogID, BlogArchiveFrequency frequency) {
		this.blogID = blogID;
		this.frequency = frequency;
	}
	
	public void addArchivePost(BlogArchivePost archivePost) {
		this.archives.add(archivePost);
	}
	
	public void addBlogArchivePost(Long postID, String title, String url, Date postedDate) {
		BlogArchivePost post = new BlogArchivePost(postID, title, url, postedDate);
		this.archives.add(post);
	}
	
	public void reIndex() {
		if(this.archives.size() == 0) {
			// nothing to index
			return;
		}
		
		switch(this.frequency) {
			case DAILY:
				this.data = dailyIndex();
				break;
				
			case WEEKLY:
				this.data = weeklyIndex();
				break;
				
			case MONTHLY:
				this.data = monthlyIndex();
				break;
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map monthlyIndex() {
		Map<ArchiveKey, Map<ArchiveKey, List<BlogArchivePost>>> yearIndex = new TreeMap<ArchiveKey, Map<ArchiveKey, List<BlogArchivePost>>>();
		
		ArchiveKey key = null;
		
		for(BlogArchivePost post : this.archives) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(post.getPostedDate());
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
		
			// month, monthIndex
			Map<ArchiveKey, List<BlogArchivePost>> monthIndex = null;
			key = new ArchiveKey(year);
			if(yearIndex.containsKey(key)) {
				monthIndex = yearIndex.get(key);
			} else {
				monthIndex = new TreeMap<ArchiveKey, List<BlogArchivePost>>();
				yearIndex.put(key, monthIndex);
			}
			
			List<BlogArchivePost> list = null;
			key = new ArchiveKey(month, CalendarConstants.MONTHS[month - 1]);
			if(monthIndex.containsKey(key)) {
				list = monthIndex.get(key);
			} else {
				list = new ArrayList<BlogArchivePost>();
				monthIndex.put(key, list);
			}
			
			list.add(post);
		}
		
		return yearIndex;
	}

	@SuppressWarnings("unchecked")
	private Map weeklyIndex() {
		Map<ArchiveKey, Map<ArchiveKey, List<BlogArchivePost>>> yearIndex = new TreeMap<ArchiveKey, Map<ArchiveKey, List<BlogArchivePost>>>();
		
		ArchiveKey key = null;
		
		for(BlogArchivePost post : this.archives) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(post.getPostedDate());
			int year = calendar.get(Calendar.YEAR);
			int week = calendar.get(Calendar.WEEK_OF_YEAR);
			
			Map<ArchiveKey, List<BlogArchivePost>> weekIndex = null;
			key = new ArchiveKey(year);
			if(yearIndex.containsKey(key)) {
				weekIndex = yearIndex.get(key);
			} else {
				weekIndex = new TreeMap<ArchiveKey, List<BlogArchivePost>>();
				yearIndex.put(key, weekIndex);
			}
			
			List<BlogArchivePost> list = null;
			key = new ArchiveKey(week);
			if(weekIndex.containsKey(key)) {
				list = weekIndex.get(key);
			} else {
				list = new ArrayList<BlogArchivePost>();
				weekIndex.put(key, list);
			}
			
			list.add(post);
		}
		
		return yearIndex;
	}

	@SuppressWarnings("unchecked")
	private Map dailyIndex() {
		// year, yearIndex
		Map<ArchiveKey, Map<ArchiveKey, Map<ArchiveKey, List<BlogArchivePost>>>> yearIndex 
				= new TreeMap<ArchiveKey, Map<ArchiveKey, Map<ArchiveKey, List<BlogArchivePost>>>>();
		
		ArchiveKey key = null;
		
		for(BlogArchivePost post : this.archives) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(post.getPostedDate());
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int day = calendar.get(Calendar.DAY_OF_MONTH);
		
			// month, monthIndex
			Map<ArchiveKey, Map<ArchiveKey, List<BlogArchivePost>>> monthIndex = null;
			key = new ArchiveKey(year);
			if(yearIndex.containsKey(key)) {
				monthIndex = yearIndex.get(key);
			} else {
				monthIndex = new TreeMap<ArchiveKey, Map<ArchiveKey, List<BlogArchivePost>>>();
				yearIndex.put(key, monthIndex);
			}
			
			// day, posts
			Map<ArchiveKey, List<BlogArchivePost>> dayIndex = null;
			key = new ArchiveKey(month, CalendarConstants.MONTHS[month - 1]);
			if(monthIndex.containsKey(key)) {
				dayIndex = monthIndex.get(key);
			} else {
				dayIndex = new TreeMap<ArchiveKey, List<BlogArchivePost>>();
				monthIndex.put(key, dayIndex);
			}
			
			List<BlogArchivePost> list = null;
			key = new ArchiveKey(day, CalendarConstants.MONTHS[month - 1] + " " + day);
			if(dayIndex.containsKey(key)) {
				list = dayIndex.get(key);
			} else {
				list = new ArrayList<BlogArchivePost>();
				dayIndex.put(key, list);
			}
			
			list.add(post);
		}
		
		return yearIndex;
	}

	/**
	 * @return the blogID
	 */
	public Long getBlogID() {
		return blogID;
	}

	/**
	 * @return the archives
	 */
	public List<BlogArchivePost> getArchives() {
		return archives;
	}

	/**
	 * @return the frequency
	 */
	public BlogArchiveFrequency getFrequency() {
		return frequency;
	}
	
	@SuppressWarnings("unchecked")
	public Map getData() {
		return this.data;
	}

}
