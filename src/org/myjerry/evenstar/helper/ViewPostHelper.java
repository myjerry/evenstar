package org.myjerry.evenstar.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.model.Comment;
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.evenstar.service.BlogUserService;
import org.myjerry.evenstar.service.CommentService;
import org.myjerry.evenstar.view.BlogPostInfo;
import org.myjerry.evenstar.view.CommentInfo;
import org.myjerry.util.StringUtils;

public class ViewPostHelper {
	
	public static final BlogPostInfo getBlogPostInfo(BlogPost post, String dateHeaderFormat, Long blogID, String restrictedPostText, BlogUserService blogUserService, CommentService commentService) {
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
		
		p.setNumComments(commentService.getTotalCommentsOnPost(p.getId(), blogID));
		
		return p;
	}

	public static final List<CommentInfo> getCommentList(Collection<Comment> comments, Long blogID, String commentTimeStampFormat, BlogUserService blogUserService) {
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

	public static final Long convertToBlogID(HttpServletRequest request, BlogService blogService) {
		String uri = request.getServerName();
		Long blogID = null;
		
		blogID = blogService.getBlogIDForServerName(uri);
		if(blogID == null) {
			blogID = blogService.getDefaultBlogID();
		}
		
		return blogID;
	}
}
