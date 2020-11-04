package ca.humbermail.n01300070.automahome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
	
	// Display a splash screen while loading app
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//WindowInsetsController.
		setTheme(R.style.splashScreen);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SharedPreferences loginInfo = getSharedPreferences(getString(R.string.Preference_Login), MODE_PRIVATE);
		Intent intent;
		
		// Automatically login from saved info
		boolean loggedIn = loginInfo.getBoolean(getString(R.string.Preference_Login_LoggedIn), false); // TODO: replace this with an actual login attempt
		if (loggedIn) {
			// Successfully logged in with saved info
			intent = new Intent(this, NavDrawerActivity.class);
		}
		else {
			// No saved info or login failed
			intent = new Intent(this, WelcomeActivity.class);
		}
		
		startActivity(intent);
		finish();
	}
}