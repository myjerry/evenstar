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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.myjerry.evenstar.helper.LabelServiceHelper;
import org.myjerry.evenstar.model.BlogLabel;
import org.myjerry.evenstar.model.BlogPost;
import org.myjerry.evenstar.model.BlogPostLabelMapping;
import org.myjerry.evenstar.persistence.PersistenceManagerFactoryImpl;
import org.myjerry.evenstar.service.BlogLabelService;
import org.myjerry.util.StringUtils;

public class BlogLabelServiceImpl implements BlogLabelService {

	@Override
	public void deleteLabelsForPost(Long blogID, Long postID, String labels) {
		if(StringUtils.isEmpty(labels)) {
			return;
		}

		// tokenize the whole string
		// semi-colon, comma and white space are the separators
		String[] tokens = LabelServiceHelper.tokenizeLabels(labels);
		if(tokens.length > 0) {
			PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
			try {
				// process each label token
				for(String token : tokens) {
					removePostLabelMapping(blogID, postID, token);
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				manager.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<BlogLabel> getBlogLabels(Long blogID) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(BlogLabel.class, "blogID == blogIDParam");
			query.declareParameters("String blogIDParam");
			
			List<BlogLabel> labels = (List<BlogLabel>) query.execute(blogID);
			return manager.detachCopyAll(labels);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getNumPostsForLabel(Long blogID, String label) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(BlogLabel.class, "blogID == blogIDParam && escapedLabel == labelParam ");
			query.declareParameters("Long blogIDParam, String labelParam");
			
			List<BlogLabel> labels = (List<BlogLabel>) query.execute(blogID, label.toUpperCase());
			if(labels != null && labels.size() == 1) {
				return labels.get(0).getNumPosts();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Long> getPostIDsForLabel(Long blogID, String label) {
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Long labelID = this.getLabelID(blogID, label);
			if(labelID != null) {
				Query query = manager.newQuery(BlogPostLabelMapping.class, "blogID == blogIDParam && labelID == labelIDParam ");
				query.declareParameters("Long blogIDParam, Long labelIDParam");
				
				List<BlogPostLabelMapping> mappings = (List<BlogPostLabelMapping>) query.execute(blogID, labelID);
				List<Long> list = new ArrayList<Long>();
				for(BlogPostLabelMapping mapping : mappings) {
					list.add(mapping.getPostID());
				}
				return list;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}

	@Override
	public void updatePostLabels(Long blogID, Long postID, String oldLabels, String newLabels) {
		if(blogID == null || postID == null) {
			return;
		}
		
		if(oldLabels != null && oldLabels.equals(newLabels)) {
			// all labels are same - nothing to do
			return;
		}
		
		if(StringUtils.isEmpty(newLabels)) {
			this.deleteLabelsForPost(blogID, postID, oldLabels);
		}

		String[] newTokens = LabelServiceHelper.tokenizeLabels(newLabels);

		if(StringUtils.isEmpty(oldLabels)) {
			for(String token : newTokens) {
				labelPost(blogID, postID, token);
			}
			
			return;
		}
		
		// we need to delete the tokens that have been removed
		
		String[] oldTokens = LabelServiceHelper.tokenizeLabels(oldLabels);
		
		Set<String> oldSet = new HashSet<String>(Arrays.asList(oldTokens));
		Set<String> newSet = new HashSet<String>(Arrays.asList(newTokens));
		
		// old set now contains the ones that have been removed
		oldSet.removeAll(Arrays.asList(newTokens));
		for(String label : oldSet) {
			removePostLabelMapping(blogID, postID, label);
		}
		
		// new set now contains the ones that have been added
		newSet.removeAll(Arrays.asList(oldTokens));
		for(String label : newSet) {
			labelPost(blogID, postID, label);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<BlogPost> getPostsForLabel(Long blogID, String label) {
		Collection<Long> ids = getPostIDsForLabel(blogID, label);
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Set<BlogPost> collectedPosts = new HashSet<BlogPost>();

			for(Long id : ids) {
				Query query = manager.newQuery(BlogPost.class, "blogID == blogIDParam && postID == postIDParam");
				query.declareParameters("Long blogID, Long postID");
				List<BlogPost> posts = (List<BlogPost>) query.execute(id);
				if(posts != null && posts.size() > 0) {
					for(BlogPost post : posts) {
						post.getContents();
					}
				}
				collectedPosts.addAll(manager.detachCopyAll(posts));
			}
			
			return collectedPosts;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getLabelID(Long blogID, String label) {
		if(blogID == null || StringUtils.isEmpty(label)) {
			return null;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(BlogLabel.class, "blogID == blogIDParam && escapedLabel == labelParam ");
			query.declareParameters("Long blogIDParam, String labelParam");
			
			List<BlogLabel> labels = (List<BlogLabel>) query.execute(blogID, label.toUpperCase());
			if(labels != null && labels.size() == 1) {
				return labels.get(0).getLabelID();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public BlogLabel getLabel(Long blogID, String label) {
		if(StringUtils.isNotEmpty(label)) {
			PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
			try {
				Query query = manager.newQuery(BlogLabel.class, "blogID == blogIDParam && escapedLabel == labelParam ");
				query.declareParameters("Long blogIDParam, String labelParam");
				
				List<BlogLabel> labels = (List<BlogLabel>) query.execute(blogID, label.toUpperCase());
				if(labels != null && labels.size() == 1) {
					return labels.get(0);
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				manager.close();
			}
		}
		return null;
	}
	
	private void labelPost(Long blogID, Long postID, String label) {
		if(blogID == null || postID == null || StringUtils.isEmpty(label)) {
			return;
		}
		
		// add the new label token, if needed
		Long labelID = addUpdateLabelID(blogID, label);
		// create the post mapping
		createNewPostLabelMapping(blogID, postID, labelID);

	}
	
	private Long addUpdateLabelID(Long blogID, String label) {
		if(blogID == null || StringUtils.isEmpty(label)) {
			return null;
		}
		
		Long labelID = getLabelID(blogID, label);
		if(labelID == null) {
			// create a new one
			PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
			try {
				BlogLabel blogLabel = new BlogLabel();
				blogLabel.setBlogID(blogID);
				blogLabel.setLabelName(label);
				manager.makePersistent(blogLabel);
				
				return blogLabel.getLabelID();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				manager.close();
			}
		}
		
		return labelID;
	}

	private void createNewPostLabelMapping(Long blogID, Long postID, Long labelID) {
		if(blogID == null || postID == null || labelID == null) {
			return;
		}
		
		// TODO: check if a mapping does not already exist
		// it should not, hence, we are safe for now
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			BlogPostLabelMapping mapping = new BlogPostLabelMapping();
			mapping.setBlogID(blogID);
			mapping.setPostID(postID);
			mapping.setLabelID(labelID);
			
			manager.makePersistent(mapping);
			
			// update count for label posts
			updateCountForLabel(blogID, labelID, 1);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
	}
	
	private void removePostLabelMapping(Long blogID, Long postID, String label) {
		if(blogID == null || postID == null || StringUtils.isEmpty(label)) {
			return;
		}
		
		// get all ids for these tokens
		BlogLabel blogLabel = this.getLabel(blogID, label);
		
		if(label != null) {
			// remove all mappings for this blog/post/ids
			removePostLabelMapping(blogID, postID, blogLabel.getLabelID());
			
			// reduce the count itself
			updateCountForLabel(blogID, blogLabel.getLabelID(), -1);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void removePostLabelMapping(Long blogID, Long postID, Long labelID) {
		if(blogID == null || postID == null || labelID == null) {
			return;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(BlogPostLabelMapping.class, "blogID == blogIDParam && postID == postIDParam && labelID == labelIDParam");
			query.declareParameters("Long blogID, Long postID, Long labelID");
			List<BlogPostLabelMapping> list = (List<BlogPostLabelMapping>) query.execute(blogID, postID, labelID);
			if(list != null && list.size() > 0) {
				for(BlogPostLabelMapping mapping : list) {
					manager.deletePersistent(mapping);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
	}

	@SuppressWarnings("unchecked")
	private void updateCountForLabel(Long blogID, Long labelID, int updateFactor) {
		if(blogID == null || labelID == null) {
			return;
		}
		
		PersistenceManager manager = PersistenceManagerFactoryImpl.getPersistenceManager();
		try {
			Query query = manager.newQuery(BlogLabel.class, "blogID == blogIDParam && labelID == labelIDParam ");
			query.declareParameters("Long blogIDParam, Long labelIDParam");
			
			List<BlogLabel> labels = (List<BlogLabel>) query.execute(blogID, labelID);
			if(labels != null && labels.size() == 1) {
				BlogLabel label = labels.get(0);
				int num = 0;
				if(label.getNumPosts() != null) {
					num = label.getNumPosts();
				}
				num = num + updateFactor;
				if(num > 0) {
					label.setNumPosts(num);
				} else {
					// delete this label
					manager.deletePersistent(label);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}
	}

}
