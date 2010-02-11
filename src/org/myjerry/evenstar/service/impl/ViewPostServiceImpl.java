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
package org.myjerry.evenstar.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.myjerry.evenstar.constants.BlogPreferenceConstants;
import org.myjerry.evenstar.model.Blog;
import org.myjerry.evenstar.model.BlogArchive;
import org.myjerry.evenstar.model.BlogLabel;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.model.Comment;
import org.myjerry.evenstar.service.BlogArchiveService;
import org.myjerry.evenstar.service.BlogLabelService;
import org.myjerry.evenstar.service.BlogPreferenceService;
import org.myjerry.evenstar.service.BlogUserService;
import org.myjerry.evenstar.service.CommentService;
import org.myjerry.evenstar.service.ViewPostService;
import org.myjerry.evenstar.view.BlogInfo;
import org.myjerry.evenstar.view.BlogPostInfo;
import org.myjerry.evenstar.view.CommentInfo;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class ViewPostServiceImpl implements ViewPostService {
	
	@Autowired
	private BlogPreferenceService blogPreferenceService;
	
	@Autowired
	private BlogArchiveService blogArchiveService;
	
	@Autowired
	private BlogLabelService blogLabelService;
	
	@Autowired
	private BlogUserService blogUserService;
	
	@Autowired
	private CommentService commentService;

	@Override
	public Map<String, Object> getPostsViewModel(Blog blog, List<BlogPost> posts, boolean fetchComments, Map<String, Object> model) {
		Long blogID = blog.getBlogID();
		
		if(posts.size() > 1) {
			// this collection is sorted chronologically
			// sort in other order
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
		
		String dateHeaderFormat = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.postDateHeaderFormat);
		String commentTimeStampFormat = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.commentTimeStampFormat);

		boolean quickEditing = StringUtils.getBoolean(this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.quickEditing), true);
		boolean emailLinks = StringUtils.getBoolean(this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.emailLinks), true);
		
		Collection<BlogPostInfo> list = new ArrayList<BlogPostInfo>();
		if(posts != null) {
			for(BlogPost post : posts) {
				BlogPostInfo p = getBlogPostInfo(post, dateHeaderFormat, blog.getBlogID(), blog.getRestrictedPostText());
				if(fetchComments) {
					if(p.isRestricted()) {
						// get the comments
						Collection<Comment> comments = this.commentService.getPublishedCommentsForPost(post.getPostID(), blogID, 1000);
						p.setComments(getCommentList(comments, blogID, commentTimeStampFormat));
					}
				} else {
					// do not allow comments to be added
					p.setAddCommentUrl(null);
				}
				
				if(!quickEditing) {
					p.setEditUrl(null);
				}
				if(!emailLinks) {
					p.setEmailPostUrl(null);
				}
				
				list.add(p);
			}
		}
		
		BlogArchive archive = this.blogArchiveService.getBlogArchive(blogID);
		Collection<BlogLabel> labels = this.blogLabelService.getBlogLabels(blogID);
		
		model.put("blog", new BlogInfo(blog));
		model.put("title", blog.getTitle());
		model.put("description", blog.getDescription());
		model.put("posts", list);
		model.put("isBlogAdmin", GAEUserUtil.isCurrentUserHost());
		model.put("archive", archive);
		model.put("labels", labels);
		model.put("commentsAllowed", fetchComments);
		
		if(fetchComments) {
			model.put("showComments", true);
		}
		
		return model;
	}

	public BlogPostInfo getBlogPostInfo(BlogPost post, String dateHeaderFormat, Long blogID, String restrictedPostText) {
		if(StringUtils.isEmpty(dateHeaderFormat)) {
			dateHeaderFormat = "EEEE, MMMM d, yyyy";
		}

		// convert the post to its display format
		BlogPostInfo p = new BlogPostInfo(post, dateHeaderFormat);

		// check for security settings
		boolean allowed = false;
		if(post.getPrivacyMode() == null) {
			post.setPrivacyMode(BlogPost.PRIVACY_MODE_PUBLIC);
		}
		switch(post.getPrivacyMode()) {
			
			case BlogPost.PRIVACY_MODE_PRIVATE:
				// the post is only for blog authors
				allowed = blogUserService.isUserBlogAdmin(blogID);
				break;
				
			case BlogPost.PRIVACY_MODE_RESTRICTED:
				// the post is restricted to blog readers
				allowed = blogUserService.isUserBlogReader(blogID);
				break;
				
			case BlogPost.PRIVACY_MODE_CUSTOM:
				// the post is restricted to specific readers
				allowed = blogUserService.isPostAllowedForUser(post.getPostID(), blogID);
				break;
				
			case BlogPost.PRIVACY_MODE_PUBLIC:
			default:
				// do nothing
				allowed = true;
				break;
		}
		p.setRestricted(allowed);
		if(!allowed) {
			p.setBody(restrictedPostText);
		}
		
		p.setNumComments(commentService.getTotalPublishedCommentsOnPost(p.getId(), blogID));
		
		return p;
	}

	public List<CommentInfo> getCommentList(Collection<Comment> comments, Long blogID, String commentTimeStampFormat) {
		List<CommentInfo> list = new ArrayList<CommentInfo>();
		
		if(StringUtils.isEmpty(commentTimeStampFormat)) {
			commentTimeStampFormat = "EEEE, MMMM d, yyyy";
		}
		
		// check if the current user has permission to view the comment
		boolean allowed = blogUserService.isUserBlogAdmin(blogID);
		
		if(comments != null) {
			for(Comment comment : comments) {
				CommentInfo commentInfo = new CommentInfo(comment, commentTimeStampFormat);
				Integer permissions = comment.getPermissions();
				if(permissions != null && permissions.equals(Comment.PRIVACY_MODE_PRIVATE)) {
					if(!allowed) {
						commentInfo.setBody("The comment author has restricted access to the blog authors.");
						commentInfo.setRestricted(allowed);
					}
				}
				list.add(commentInfo);
			}
		}
		return list;
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

	/**
	 * @return the blogArchiveService
	 */
	public BlogArchiveService getBlogArchiveService() {
		return blogArchiveService;
	}

	/**
	 * @param blogArchiveService the blogArchiveService to set
	 */
	public void setBlogArchiveService(BlogArchiveService blogArchiveService) {
		this.blogArchiveService = blogArchiveService;
	}

	/**
	 * @return the blogLabelService
	 */
	public BlogLabelService getBlogLabelService() {
		return blogLabelService;
	}

	/**
	 * @param blogLabelService the blogLabelService to set
	 */
	public void setBlogLabelService(BlogLabelService blogLabelService) {
		this.blogLabelService = blogLabelService;
	}

	/**
	 * @return the blogUserService
	 */
	public BlogUserService getBlogUserService() {
		return blogUserService;
	}

	/**
	 * @param blogUserService the blogUserService to set
	 */
	public void setBlogUserService(BlogUserService blogUserService) {
		this.blogUserService = blogUserService;
	}

	/**
	 * @return the commentService
	 */
	public CommentService getCommentService() {
		return commentService;
	}

	/**
	 * @param commentService the commentService to set
	 */
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

}
