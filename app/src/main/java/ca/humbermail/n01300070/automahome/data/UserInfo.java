package ca.humbermail.n01300070.automahome.data;

import android.content.Context;
import android.content.SharedPreferences;

import ca.humbermail.n01300070.automahome.R;

public class UserInfo {
	private Context appContext;
	private SharedPreferences userInfo;
	
	public UserInfo(Context appContext) {
		this.appContext = appContext;
		userInfo = appContext.getSharedPreferences(PreferenceKeys.LOGIN, Context.MODE_PRIVATE);
	}
	
	public String getFirstName() {
		return userInfo.getString(PreferenceKeys.LOGIN_FIRST_NAME, null);
	}
	
	public String getLastName() {
		return userInfo.getString(PreferenceKeys.LOGIN_LAST_NAME, null);
	}
	
	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}
	
	public String getEmailAddress() {
		return userInfo.getString(PreferenceKeys.LOGIN_EMAIL_ADDRESS, null);
	}
}
