package ca.humbermail.n01300070.automahome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import ca.humbermail.n01300070.automahome.data.LoginDataSource;
import ca.humbermail.n01300070.automahome.data.PreferenceKeys;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Display a splash screen while loading app
		setTheme(R.style.splashScreen);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LoginDataSource loginDataSource = new LoginDataSource();
		Intent intent;
		
		// Automatically login from saved info
		if (loginDataSource.isLoggedIn()) {
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