package ca.humbermail.n01300070.automahome.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.LoginDataSource;

public class MainActivity extends AppCompatActivity {
	LoginDataSource loginDataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Display a splash screen while loading app
		setTheme(R.style.splashScreen);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		loginDataSource = new LoginDataSource(new LoginDataSource.LoginStateListener() {
			@Override
			public void onLoginStateChanged(@NonNull FirebaseAuth auth, boolean loggedIn) {
				if (loggedIn) {
					continueToActivity(NavDrawerActivity.class);
				} else {
					continueToActivity(WelcomeActivity.class);
				}
			}
		});
	}
	
	private void continueToActivity(Class<?> activityClass) {
		startActivity(new Intent(this, activityClass));
		finish();
	}
}