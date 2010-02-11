package org.myjerry.evenstar.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.myjerry.evenstar.constants.PreferenceConstants;
import org.myjerry.evenstar.enums.BlogImportType;
import org.myjerry.evenstar.helper.ImportBlogHelper;
import org.myjerry.evenstar.model.Blog;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.model.Comment;
import org.myjerry.evenstar.model.EvenstarUser;
import org.myjerry.evenstar.model.blogimport.AuthorImport;
import org.myjerry.evenstar.model.blogimport.BlogImport;
import org.myjerry.evenstar.model.blogimport.CommentImport;
import org.myjerry.evenstar.model.blogimport.PostImport;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.evenstar.service.CommentService;
import org.myjerry.evenstar.service.PreferenceService;
import org.myjerry.evenstar.service.UserService;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.ServerUtils;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class BlogServiceImpl implements BlogService {
	
	@Autowired
	private PreferenceService preferenceService;
	
	@Autowired
	private BlogPostService blogPostService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	
	@Override
	public boolean createBlog(String blogName, String blogAddress, String blogAlias) {
		// TODO Auto-generated method stub
		Blog blog = new Blog();
		blog.setTitle(blogName);
		blog.setAddress(blogAddress);
		blog.setAlias(blogAlias);
		
		blog.setCreationDate(ServerUtils.getServerDate());
		blog.setOwnerID(GAEUserUtil.getUserID());
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			manager.makePersistent(blog);
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			manager.close();
		}
	}

	@Override
	public boolean deleteBlog(Long blogID) {
		if(blogID == null) {
			return false;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(Blog.class.getSimpleName(), blogID);
			Blog blog = manager.getObjectById(Blog.class, key);
			manager.deletePersistent(blog);
			return true;
		} catch(JDOObjectNotFoundException e) {
			// do nothing
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean existsBlogAddress(String blogAddress) {
		if(StringUtils.isEmpty(blogAddress)) {
			return false;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(Blog.class, "address == addressParam");
			query.declareParameters("String addressParam");
			
			List<Blog> blogs = (List<Blog>) query.execute(blogAddress);
			if(blogs != null && blogs.size() > 0) {
				return true;
			}
			return false;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean existsBlogAlias(String alias) {
		if(StringUtils.isEmpty(alias)) {
			return false;
		}

		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(Blog.class, "alias == aliasParam");
			query.declareParameters("String aliasParam");
			
			List<Blog> blogs = (List<Blog>) query.execute(alias);
			if(blogs != null && blogs.size() > 0) {
				return true;
			}
			return false;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean existsBlogName(String blogTitle) {
		if(StringUtils.isEmpty(blogTitle)) {
			return false;
		}

		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(Blog.class, "title == titleParam");
			query.declareParameters("String titleParam");
			
			List<Blog> blogs = (List<Blog>) query.execute(blogTitle);
			if(blogs != null && blogs.size() > 0) {
				return true;
			}
			return false;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Blog> getAllBlogs() {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			List<Blog> blogs = (List<Blog>) manager.newQuery("select from " + Blog.class.getName()).execute();
			return manager.detachCopyAll(blogs);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			manager.close();
		}
	}

	@Override
	public Long getDefaultBlogID() {
		String blogID = this.preferenceService.getPreference(PreferenceConstants.DEFAULT_BLOG_ID);
		if(StringUtils.isNotEmpty(blogID)) {
			return new Long(blogID);
		}
		return null;
	}

	@Override
	public boolean setDefaultBlogID(Long blogID) {
		if(blogID != null) {
			return this.preferenceService.setPreference(PreferenceConstants.DEFAULT_BLOG_ID, String.valueOf(blogID));
		} else {
			return this.preferenceService.deletePreference(PreferenceConstants.DEFAULT_BLOG_ID);
		}
	}

	/**
	 * @return the preferenceService
	 */
	public PreferenceService getPreferenceService() {
		return preferenceService;
	}

	/**
	 * @param preferenceService the preferenceService to set
	 */
	public void setPreferenceService(PreferenceService preferenceService) {
		this.preferenceService = preferenceService;
	}

	@Override
	public Blog getBlog(Long blogID) {
		if(blogID == null) {
			return null;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(Blog.class.getSimpleName(), blogID);
			Blog blog = manager.getObjectById(Blog.class, key);
			// hack for GAE to fetch text fields
			blog.getDescription();
			return manager.detachCopy(blog);
		} catch(JDOObjectNotFoundException e) {
			// do nothing
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}

	@Override
	public boolean updateBlog(Blog blog) {
		if(blog == null) {
			return false;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(Blog.class.getSimpleName(), blog.getBlogID());
			Blog b = manager.getObjectById(Blog.class, key);

			// hack for GAE to fetch text fields
			b.setAddress(blog.getAddress());
			b.setAlias(blog.getAlias());
			b.setDescription(blog.getDescription());
			b.setTitle(blog.getTitle());
			b.setRestrictedPostText(blog.getRestrictedPostText());
			
			manager.makePersistent(b);
			return true;
		} catch(JDOObjectNotFoundException e) {
			// do nothing
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getBlogIDForServerName(String serverName) {
		if(StringUtils.isEmpty(serverName)) {
			return null;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(Blog.class, "address == addressParam");
			query.declareParameters("String addressParam");
			
			List<Blog> blogs = (List<Blog>) query.execute(serverName);
			if(blogs != null && blogs.size() > 0) {
				return blogs.get(0).getBlogID();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}

	@Override
	public boolean importBlog(Long blogID, BlogImportType importType, String blogData, boolean publishPosts) {
		if(blogID == null || StringUtils.isEmpty(blogData)) {
			return false;
		}
		
		BlogImport blogImport = null; 
		switch(importType) {
			case BLOGGER:
				blogImport = ImportBlogHelper.parseBlogger(blogData);
				break;
		}
		
		if(blogImport != null) {
			try {
				importBlog(blogID, blogImport, publishPosts);
				return true;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}

	private void importBlog(final Long blogID, BlogImport blogImport, boolean publishPosts) {
		if(blogID == null || blogImport == null) {
			return;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		
		// get all authors too - we need them
		Set<EvenstarUser> authors = new HashSet<EvenstarUser>();
		for(PostImport postImport : blogImport.getPosts()) {
			EvenstarUser author = ImportBlogHelper.getEvenstarUser(postImport.getAuthor());
			if(author != null) {
				authors.add(author);
			}
		}
		for(CommentImport commentImport : blogImport.getComments()) {
			EvenstarUser author = ImportBlogHelper.getEvenstarUser(commentImport.getAuthor());
			if(author != null) {
				authors.add(author);
			}
		}
		
		// check if these authors exist - if they do remove them from this list
		Set<EvenstarUser> unknownAuthors = new HashSet<EvenstarUser>();
		for(EvenstarUser author : authors) {
			if(StringUtils.isNotEmpty(author.getHomePage())) {
				EvenstarUser user = this.userService.getEvenstarUserForUri(author.getHomePage());
				if(user == null) {
					unknownAuthors.add(author);
				}
			} else if(StringUtils.isNotEmpty(author.getEmail())) {
				EvenstarUser user = this.userService.getEvenstarUser(author.getEmail());
				if(user == null) {
					unknownAuthors.add(author);
				}
			}
		}
		
		// start the transaction
		try {
			// persist all authors
			for(EvenstarUser user : unknownAuthors) {
				this.userService.addEvenstarUser(user);
			}
			
			// re-populate the author IDs in posts
			List<BlogPost> posts = new ArrayList<BlogPost>();
			for(PostImport postImport : blogImport.getPosts()) {
				AuthorImport author = postImport.getAuthor();
				if(StringUtils.isNotEmpty(author.getUri())) {
					Long userID = this.userService.getEvenstarUserIDForUri(author.getUri());
					BlogPost post = ImportBlogHelper.getBlogPost(blogID, postImport);
					post.setLastUpdateUser(userID);
					if(!publishPosts) {
						post.setPostedDate(null);
					}
					posts.add(post);
				}
			}
			
			// persist all posts
			// we dont persist them together because we want blog labels
			// to be persisted along too
			Map<String, Long> postCommentMapping = new HashMap<String, Long>();
			if(publishPosts) {
				for(BlogPost post : posts) {
					this.blogPostService.publishPost(post);
					postCommentMapping.put(post.getUniqueImportID(), post.getPostID());
				}
			} else {
				for(BlogPost post : posts) {
					this.blogPostService.saveDraftPost(post);
					postCommentMapping.put(post.getUniqueImportID(), post.getPostID());
				}
			}
			
			// re-populate the IDs in comments
			for(CommentImport commentImport : blogImport.getComments()) {
				Comment comment = ImportBlogHelper.getComment(blogID, commentImport);
				comment.setBlogID(blogID);
				Long postID = postCommentMapping.get(commentImport.getUniquePostID());
				comment.setPostID(postID);
				Long userID = this.userService.getEvenstarUserIDForUri(commentImport.getAuthor().getUri());
				comment.setAuthorID(userID);
				
				// set permissions to PUBLIC
				comment.setPermissions(Comment.PRIVACY_MODE_PUBLIC);
				
				// we will make this change later
				// this.commentService.postComment(comment);
				manager.makePersistent(comment);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getBlogIDForAlias(String alias) {
		if(StringUtils.isEmpty(alias)) {
			return null;
		}

		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(Blog.class, "alias == aliasParam");
			query.declareParameters("String aliasParam");
			
			List<Blog> blogs = (List<Blog>) query.execute(alias);
			if(blogs != null && blogs.size() > 0) {
				return blogs.get(0).getBlogID();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}

	/**
	 * @return the blogPostService
	 */
	public BlogPostService getBlogPostService() {
		return blogPostService;
	}

	/**
	 * @param blogPostService the blogPostService to set
	 */
	public void setBlogPostService(BlogPostService blogPostService) {
		this.blogPostService = blogPostService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
