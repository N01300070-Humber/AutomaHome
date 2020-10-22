package ca.humbermail.n01300070.automahome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ca.humbermail.n01300070.automahome.ui.login.SignInActivity;

public class MainActivity extends AppCompatActivity {
	
	// Display a splash screen while loading app
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//WindowInsetsController.
		setTheme(R.style.splashScreen);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent;
		
		// Automatically login from saved info
		boolean loggedIn = false; // TODO: replace this with an actual login attempt
		if (loggedIn) {
			// Successfully logged in with saved info
			intent = new Intent(this, WelcomeActivity.class);
		}
		else {
			// No saved info or login failed
			intent = new Intent(this, WelcomeActivity.class);
		}
		
		startActivity(intent);
		finish();
	}
}