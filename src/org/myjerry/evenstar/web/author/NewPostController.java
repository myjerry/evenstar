package org.myjerry.evenstar.web.author;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.service.BlogPostService;
import org.myjerry.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.appengine.api.datastore.Text;

public class NewPostController extends MultiActionController {
	
	@Autowired
	private BlogPostService blogPostService;
	
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(".author.newpost");
		mav.addObject("blogID", request.getParameter("blogID"));
		return mav;
	}

	public ModelAndView saveAsDraft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(".author.newpost");
		return mav;
	}
	
	public ModelAndView publishPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> validationErrors = validateRequest(request);

		ModelAndView mav = new ModelAndView();
		if(validationErrors.size() == 0) {
			BlogPost post = new BlogPost();
			post.setBlogID(new Long(request.getParameter("blogID")));
			post.setTitle(request.getParameter("postTitle"));
			post.setContents(request.getParameter("postContents"));
			String labels = request.getParameter("labels");
			if(StringUtils.isEmpty(labels)) {
				post.setLabels("");
			} else {
				post.setLabels(labels);
			}
			
			boolean result = this.blogPostService.publishPost(post);
			if(result) {
				mav.setViewName(".author.newpost.published");	
			} else {
				mav.setViewName(".author.newpost");
			}
		} else {
			mav.setViewName(".author.newpost");
		}
		return mav;
	}

	private List<String> validateRequest(HttpServletRequest request) {
		return new ArrayList<String>();
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
}
