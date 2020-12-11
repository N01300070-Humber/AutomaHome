package ca.humbermail.n01300070.automahome.data;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ca.humbermail.n01300070.automahome.MainActivity;
import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
	
	private SharedPreferences loginInfo;
	private SharedPreferences userInfo;
	private SharedPreferences.Editor loginInfoEditor;
	private SharedPreferences.Editor userInfoEditor;
	
	public Result<LoggedInUser> login(String emailAddress, String password) {
		
		try {
			// TODO: handle loggedInUser authentication
			LoggedInUser fakeUser =
					new LoggedInUser(
							java.util.UUID.randomUUID().toString(),
							emailAddress,
							"Jane",
							"Doe");
			
			loginInfoEditor.putBoolean("", true);
			return new Result.Success<>(fakeUser);
		} catch (Exception e) {
			return new Result.Error(new IOException("Error logging in", e));
		}
	}
	
	public void logout() {
		// TODO: revoke authentication
	}
	
	public String getCurrentUID() {
		return "testuser"; // TODO: replace with actual data
	}
}