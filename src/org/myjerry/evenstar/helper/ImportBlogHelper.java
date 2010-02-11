package org.myjerry.evenstar.helper;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.model.Comment;
import org.myjerry.evenstar.model.EvenstarUser;
import org.myjerry.evenstar.model.blogimport.AuthorImport;
import org.myjerry.evenstar.model.blogimport.BlogImport;
import org.myjerry.evenstar.model.blogimport.CommentImport;
import org.myjerry.evenstar.model.blogimport.PostImport;
import org.myjerry.util.ServerUtils;

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndPerson;
import com.sun.syndication.io.SyndFeedInput;

public class ImportBlogHelper {

	/**
	 * Parse blogger XML data and create the global {@link BlogImport} domain object.
	 * 
	 * @param blogData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static BlogImport parseBlogger(String blogData) {
		SyndFeedInput input = new SyndFeedInput();
		Reader reader = new StringReader(blogData);
		BlogImport blogImport = new BlogImport();

		try {
			SyndFeed feed = input.build(reader);
			
			blogImport.setTitle(feed.getTitle());
			
			List list = feed.getEntries();
			for(Object o : list) {
				SyndEntry entry = (SyndEntry) o;
				if(entry.getCategories() != null && entry.getCategories().size() > 0) {
					for(Object categoryObject : entry.getCategories()) {
						SyndCategory category = (SyndCategory) categoryObject;

						// check for POSTS
						if("http://schemas.google.com/g/2005#kind".equals(category.getTaxonomyUri()) && "http://schemas.google.com/blogger/2008/kind#post".equals(category.getName())) {
							blogImport.addPostImport(getBloggerPost(entry));
							continue;
						}
						
						// check for COMMENTS
						if("http://schemas.google.com/g/2005#kind".equals(category.getTaxonomyUri()) && "http://schemas.google.com/blogger/2008/kind#comment".equals(category.getName())) {
							blogImport.addCommentImport(getBloggerComment(entry));
						}
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return blogImport;
	}

	@SuppressWarnings("unchecked")
	private static CommentImport getBloggerComment(SyndEntry entry) {
		CommentImport commentImport = null;
		if(entry.getContents() != null && entry.getContents().size() > 0) {
			commentImport = new CommentImport();
			
			SyndContent content = (SyndContent) entry.getContents().get(0);
			commentImport.setContent(content.getValue());
			
			commentImport.setPublished(entry.getPublishedDate());
			commentImport.setUpdated(entry.getUpdatedDate());

			// get author
			if(entry.getAuthors() != null && entry.getAuthors().size() > 0) {
				SyndPerson person = (SyndPerson) entry.getAuthors().get(0);
				
				AuthorImport authorImport = new AuthorImport();
				String email = person.getEmail();
				if(!("noreply@blogger.com".equals(email))) {
					authorImport.setEmail(person.getEmail());
				}
				authorImport.setName(person.getName());
				authorImport.setUri(person.getUri());
				
				commentImport.setAuthor(authorImport);
			}
			
			// get the unique post ID
			// this is needed to relate to which post actually has the comment
			Object foreign = entry.getForeignMarkup();
			if(foreign != null) {
				if(foreign instanceof List) {
					List elements = (List) foreign;
					if(elements != null && elements.size() > 0) {
						for(Object elementObject : elements) {
							Element element = (Element) elementObject;
							if("in-reply-to".equals(element.getName()) && "thr".equals(element.getNamespace().getPrefix())) {
								String ref = element.getAttributeValue("ref");
								commentImport.setUniquePostID(ref.trim());
							}
						}
					}
				}
			}
		}
		return commentImport;
	}

	private static PostImport getBloggerPost(SyndEntry entry) {
		PostImport postImport = null;
		if(entry.getContents() != null && entry.getContents().size() > 0) {
			postImport = new PostImport();
			
			SyndContent content = (SyndContent) entry.getContents().get(0);
			postImport.setHtmlContent(content.getValue());
			
			postImport.setTitle(entry.getTitle());
			postImport.setPublished(entry.getPublishedDate());
			postImport.setUpdated(entry.getUpdatedDate());
			postImport.setUniqueID(entry.getUri().trim());
			
			// get post link
			if(entry.getLinks() != null && entry.getLinks().size() > 0) {
				for(Object linkObject : entry.getLinks()) {
					SyndLink link = (SyndLink) linkObject;
					if("alternate".equals(link.getRel()) && "text/html".equals(link.getType())) {
						postImport.setHref(ServerUtils.getRelativeLink(link.getHref()));
						break;
					}
				}
			}
			
			// get author
			if(entry.getAuthors() != null && entry.getAuthors().size() > 0) {
				SyndPerson person = (SyndPerson) entry.getAuthors().get(0);
				
				AuthorImport authorImport = new AuthorImport();
				authorImport.setEmail(person.getEmail());
				authorImport.setName(person.getName());
				authorImport.setUri(person.getUri());
				
				postImport.setAuthor(authorImport);
			}
			
			// get labels
			List<String> labels = new ArrayList<String>();
			String labelString = null;
			for(Object categoryObject : entry.getCategories()) {
				SyndCategory category = (SyndCategory) categoryObject;
				if("http://www.blogger.com/atom/ns#".equals(category.getTaxonomyUri())) {
					String labelName = category.getName(); 
					labels.add(labelName);
				}
			}
			if(labels != null && labels.size() > 0) {
				for(int index = 0; index < labels.size(); index++) {
					if(index == 0) {
						labelString = labels.get(0);
					} else {
						labelString += ";" + labels.get(index);
					}
				}
			}
			postImport.setTags(labelString);
		}
		return postImport;
	}

	/**
	 * Convert and imported blog post to {@link BlogPost} domain object.
	 * @param blogID
	 * @param postImport
	 * @return
	 */
	public static BlogPost getBlogPost(Long blogID, PostImport postImport) {
		BlogPost post = new BlogPost();
		
		post.setBlogID(blogID);
		post.setContents(postImport.getHtmlContent());
		post.setLastUpdated(postImport.getUpdated());
		post.setPostedDate(postImport.getPublished());
		post.setLabels(postImport.getTags());
		post.setTitle(postImport.getTitle());
		post.setCreationDate(ServerUtils.getServerDate());
		post.setUniqueImportID(postImport.getUniqueID());
		post.setUrl(postImport.getHref());
		
		return post;
	}

	/**
	 * Convert an imported comment to {@link Comment} domain object.
	 * 
	 * @param blogID
	 * @param commentImport
	 * @return
	 */
	public static Comment getComment(Long blogID, CommentImport commentImport) {
		Comment comment = new Comment();
		
		comment.setContent(commentImport.getContent());
		comment.setPublished(commentImport.getPublished());
		comment.setTimestamp(commentImport.getUpdated());
		
		return comment;
	}

	/**
	 * Convert an imported user to {@link EvenstarUser} domain object.
	 * 
	 * @param authorImport
	 * @return
	 */
	public static EvenstarUser getEvenstarUser(AuthorImport authorImport) {
		EvenstarUser user = new EvenstarUser();
		user.setUserName(authorImport.getName());
		user.setEmail(authorImport.getEmail());
		user.setHomePage(authorImport.getUri());
		return user;
	}

}
