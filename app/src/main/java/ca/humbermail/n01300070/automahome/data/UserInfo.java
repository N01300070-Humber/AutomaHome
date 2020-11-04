package ca.humbermail.n01300070.automahome.data;

import android.content.Context;
import android.content.SharedPreferences;

import ca.humbermail.n01300070.automahome.R;

public class UserInfo {
	private Context appContext;
	private SharedPreferences userInfo;
	
	public UserInfo(Context appContext) {
		this.appContext = appContext;
		userInfo = appContext.getSharedPreferences(
				(String) appContext.getText(R.string.Preference_Login), Context.MODE_PRIVATE);
	}
	
	public String getFirstName() {
		return userInfo.getString(
				(String) appContext.getText(R.string.Preference_Login_FirstName), null);
	}
	
	public String getLastName() {
		return userInfo.getString(
				(String) appContext.getText(R.string.Preference_Login_LastName), null);
	}
	
	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}
	
	public String getEmailAddress() {
		return userInfo.getString(
				(String) appContext.getText(R.string.Preference_Login_EmailAddress), null);
	}
}
