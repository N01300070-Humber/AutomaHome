package ca.humbermail.n01300070.automahome.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.ui.CustomActivity;
import ca.humbermail.n01300070.automahome.ui.login.LoginActivity;

public class WelcomeActivity extends CustomActivity {
	
	int REQUEST_LOGIN = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("WelcomeActivity", "onCreate called");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
	}
	
	public void buttonPressed_signIn(View view) {
		Log.d("WelcomeActivity", "buttonPressed_signIn called");
		
		Intent intent = new Intent(this, LoginActivity.class);
		intent.putExtra("Register", false);
		startActivityForResult(intent, REQUEST_LOGIN);
	}
	
	public void buttonPressed_register(View view) {
		Log.d("WelcomeActivity", "buttonPressed_register called");
		
		Intent intent = new Intent(this, LoginActivity.class);
		intent.putExtra("Registering", true);
		startActivityForResult(intent, REQUEST_LOGIN);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		Log.d("WelcomeActivity", "onActivityResult called");
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == REQUEST_LOGIN) {
			Log.d("WelcomeActivity", "ActivityResult from LoginActivity");
			if (resultCode == RESULT_OK) {
				Log.d("WelcomeActivity", "ActivityResult came back as OK");
				Log.d("WelcomeActivity", "launching NavDrawerActivity");
				startActivity(new Intent(this, NavDrawerActivity.class));
				Log.d("WelcomeActivity", "finish");
				finish();
			} else {
				Log.d("WelcomeActivity", "ActivityResult came back as NOT OK");
			}
		}
	}
	
	@Override
	public void finish() {
		Log.d("WelcomeActivity", "finish called");
		super.finish();
	}
}