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
package org.myjerry.evenstar.service;

import java.util.Collection;

import org.myjerry.evenstar.model.BlogLabel;
import org.myjerry.evenstar.model.BlogPost;

public interface BlogLabelService {
	
	public Collection<BlogLabel> getBlogLabels(Long blogID);
	
	public void updatePostLabels(Long blogID, Long postID, String oldLabels, String newLabels);
	
	public Integer getNumPostsForLabel(Long blogID, String label);

	public void deleteLabelsForPost(Long blogID, Long postID, String labels);
	
	public Collection<BlogPost> getPostsForLabel(Long blogID, String label);
	
	public Collection<Long> getPostIDsForLabel(Long blogID, String label);
	
	public Long getLabelID(Long blogID, String label);
	
	public BlogLabel getLabel(Long blogID, String label);
}
