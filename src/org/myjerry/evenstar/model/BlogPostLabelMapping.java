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
package org.myjerry.evenstar.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class BlogPostLabelMapping {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long mappingID;
	
	@Persistent
	private Long blogID;
	
	@Persistent
	private Long postID;
	
	@Persistent
	private Long labelID;

	/**
	 * @return the mappingID
	 */
	public Long getMappingID() {
		return mappingID;
	}

	/**
	 * @param mappingID the mappingID to set
	 */
	public void setMappingID(Long mappingID) {
		this.mappingID = mappingID;
	}

	/**
	 * @return the blogID
	 */
	public Long getBlogID() {
		return blogID;
	}

	/**
	 * @param blogID the blogID to set
	 */
	public void setBlogID(Long blogID) {
		this.blogID = blogID;
	}

	/**
	 * @return the postID
	 */
	public Long getPostID() {
		return postID;
	}

	/**
	 * @param postID the postID to set
	 */
	public void setPostID(Long postID) {
		this.postID = postID;
	}

	/**
	 * @return the labelID
	 */
	public Long getLabelID() {
		return labelID;
	}

	/**
	 * @param labelID the labelID to set
	 */
	public void setLabelID(Long labelID) {
		this.labelID = labelID;
	}

}
