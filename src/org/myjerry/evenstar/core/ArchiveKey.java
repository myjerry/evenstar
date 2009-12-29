package org.myjerry.evenstar.core;

public class ArchiveKey implements Comparable<ArchiveKey> {
	
	private String name;
	
	private Integer key;
	
	public ArchiveKey(Integer key) {
		this.key = key;
	}
	
	public ArchiveKey(Integer key, String name) {
		this.key = key;
		this.name = name;
	}
	
	public String getUrl() {
		return "/showPosts.html";
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

}
