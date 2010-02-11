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
package org.myjerry.evenstar.core;

public class ArchiveKey implements Comparable<ArchiveKey> {
	
	private String name;
	
	private Integer key;
	
	private String url;
	
	public ArchiveKey(Integer key) {
		this.key = key;
	}
	
	public ArchiveKey(Integer key, String name) {
		this.key = key;
		this.name = name;
	}
	
	public ArchiveKey(Integer key, String name, String url) {
		this.key = key;
		this.name = name;
		this.url = url;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	@Override
	public int hashCode() {
		return this.key.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(obj instanceof ArchiveKey) {
			ArchiveKey nsik = (ArchiveKey) obj;
			return this.key.equals(nsik.key);
		}
		
		if(obj instanceof Integer) {
			return this.key.equals((Integer) obj);
		}
		
		return this.key.equals(obj);
	}

	public int compareTo(ArchiveKey o) {
		// inverse this sorting
		// we need chronological descending order
		return (0 - this.key.compareTo(o.key));
	}
	
	public int compareTo(Integer i) {
		// inverse this sorting
		// we need chronological descending order
		return (0 - this.key.compareTo(i));
	}

	/**
	 * @return the name
	 */
	public String getName() {
		if(this.name != null) {
			return name;
		}
		return this.key.toString();
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the key
	 */
	public Integer getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(Integer key) {
		this.key = key;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

}
