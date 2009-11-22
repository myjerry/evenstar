package org.myjerry.evenstar.service;

public interface PreferenceService {
	
	public String getPreference(String key);
	
	public boolean checkPreferenceExists(String key);
	
	public boolean insertPreference(String key, String value);
	
	public boolean updatePreference(String key, String value);
	
	public boolean setPreference(String key, String value);

	public boolean deletePreference(String key);

}
