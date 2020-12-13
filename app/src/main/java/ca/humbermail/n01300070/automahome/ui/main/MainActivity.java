package ca.humbermail.n01300070.automahome.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.LoginDataSource;
import ca.humbermail.n01300070.automahome.ui.CustomActivity;

public class MainActivity extends CustomActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("MainActivity", "onCreate called");
		// Display a splash screen while loading app
		setTheme(R.style.splashScreen);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setLoginDataSource(new LoginDataSource(new LoginDataSource.LoginStateListener() {
			@Override
			public void onLoginStateChanged(@NonNull FirebaseAuth auth, boolean loggedIn) {
				Log.d("MainActivity", "detected login state change. Value is now " + loggedIn);
				if (loggedIn) {
					continueToActivity(NavDrawerActivity.class);
				} else {
					continueToActivity(WelcomeActivity.class);
				}
			}
		}));
	}
	
	private void continueToActivity(Class<?> activityClass) {
		Log.d("MainActivity", "continueToActivity called. Opening " + activityClass.toString());
		startActivity(new Intent(this, activityClass));
		finish();
	}
	
	@Override
	public void finish() {
		Log.d("MainActivity", "finish called");
		super.finish();
	}
}