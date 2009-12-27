package org.myjerry.evenstar.web.blog;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.myjerry.evenstar.constants.BlogPreferenceConstants;
import org.myjerry.evenstar.helper.ViewPostHelper;
import org.myjerry.evenstar.model.Blog;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.service.BlogLayoutService;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.evenstar.service.BlogPreferenceService;
import org.myjerry.evenstar.service.BlogService;
import org.myjerry.evenstar.service.CommentService;
import org.myjerry.evenstar.service.BlogUserService;
import org.myjerry.evenstar.view.BlogInfo;
import org.myjerry.evenstar.view.BlogPostInfo;
import org.myjerry.util.GAEUserUtil;
import org.myjerry.util.ServerUtils;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class BlogHomeController extends MultiActionController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private BlogPreferenceService blogPreferenceService;
	
	@Autowired
	private BlogPostService blogPostService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private BlogLayoutService blogLayoutService;
	
	@Autowired
	private BlogUserService blogUserService;

	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean searchMode = false;
		String uri = request.getRequestURI();
		String older = request.getParameter("older");
		String newer = request.getParameter("newer");
		if(uri.equals("/showPosts.html")) {
			searchMode = true;
		}
		
		ModelAndView mav = new ModelAndView();
		Long blogID = ViewPostHelper.convertToBlogID(request, this.blogService);
		
		if(blogID == null) {
			mav.setViewName(".evenstar");
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			Blog blog = this.blogService.getBlog(blogID);
			
			String numPostsString = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.showNumPosts);
			int numPosts = 10;
			if(StringUtils.isNotEmpty(numPostsString)) {
				numPosts = Integer.valueOf(numPostsString);
			}
			
			Collection<BlogPost> postsCollection = null;
			if(!searchMode) {
				postsCollection = this.blogPostService.getBlogPosts(blogID, numPosts);
			} else {
				if(StringUtils.isNotEmpty(older)) {
					Date lastUpdatedDate = ServerUtils.getUniversalDate(older);
					postsCollection = this.blogPostService.getOlderBlogPosts(blogID, numPosts, lastUpdatedDate);
				}
				if(StringUtils.isNotEmpty(newer)) {
					Date lastUpdatedDate = ServerUtils.getUniversalDate(newer);
					postsCollection = this.blogPostService.getNewerBlogPosts(blogID, numPosts, lastUpdatedDate);
				}
			}
			
			List<BlogPost> posts = new ArrayList<BlogPost>(postsCollection);
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

			if(posts != null && posts.size() > 0) {
				if(posts.size() > numPosts) {
					int last = posts.size() - 1;
					BlogPost lastPost = posts.get(last);
					model.put("olderPageUrl", "/showPosts.html?older=" + ServerUtils.getUniversalDateString(lastPost.getPostedDate()));
					posts.remove(last);
				} else {
					model.put("olderPageUrl", "");
				}
				
				if(!this.blogPostService.isFirstPost(posts.get(0))) {
					model.put("newerPageUrl", "/showPosts.html?newer=" + ServerUtils.getUniversalDateString(posts.get(0).getPostedDate()));
				} else {
					model.put("newerPageUrl", "");
				}
			}
			
			
			String dateHeaderFormat = this.blogPreferenceService.getPreference(blogID, BlogPreferenceConstants.postDateHeaderFormat);
			
			Collection<BlogPostInfo> list = new ArrayList<BlogPostInfo>();
			if(posts != null) {
				for(BlogPost post : posts) {
					BlogPostInfo p = ViewPostHelper.getBlogPostInfo(post, dateHeaderFormat, blog.getBlogID(), blog.getRestrictedPostText(), this.blogUserService, this.commentService);
					list.add(p);
				}
			}
			model.put("blog", new BlogInfo(blog));
			model.put("title", blog.getTitle());
			model.put("description", blog.getDescription());
			model.put("posts", list);
			model.put("isBlogAdmin", GAEUserUtil.isCurrentUserHost());
			
			String template = this.blogLayoutService.getBlogTemplate(blogID);
			if(StringUtils.isEmpty(template)) {
				template = this.blogLayoutService.getDefaultBlogTemplate();
			}

			StringWriter result = new StringWriter();
			this.velocityEngine.evaluate(new VelocityContext(model), result, "log string", template);
			String generatedBlogPage = result.toString();
			
			mav.addAllObjects(model);
			mav.addObject("generatedBlogPage", generatedBlogPage);
			mav.setViewName(".view.blog.home");
		}
		return mav;
	}

	/**
	 * @return the blogService
	 */
	public BlogService getBlogService() {
		return blogService;
	}

	/**
	 * @param blogService the blogService to set
	 */
	public void setBlogService(BlogService blogService) {
		this.blogService = blogService;
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
	 * @return the velocityEngine
	 */
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	/**
	 * @param velocityEngine the velocityEngine to set
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	/**
	 * @return the blogLayoutService
	 */
	public BlogLayoutService getBlogLayoutService() {
		return blogLayoutService;
	}

	/**
	 * @param blogLayoutService the blogLayoutService to set
	 */
	public void setBlogLayoutService(BlogLayoutService blogLayoutService) {
		this.blogLayoutService = blogLayoutService;
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
