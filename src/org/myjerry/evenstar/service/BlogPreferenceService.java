package org.myjerry.evenstar.service;

public interface BlogPreferenceService {

	public String getPreference(Long blogID, String key);
	
	public boolean checkPreferenceExists(Long blogID, String key);
	
	public boolean insertPreference(Long blogID, String key, String value);
	
	public boolean updatePreference(Long blogID, String key, String value);
	
	public boolean putPreference(Long blogID, String key, String value);

	public boolean deletePreference(Long blogID, String key, String value);

}
