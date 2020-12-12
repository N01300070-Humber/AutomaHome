package ca.humbermail.n01300070.automahome.ui.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.ui.login.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {
	
	int REQUEST_LOGIN = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
	}
	
	public void buttonPressed_signIn(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.putExtra("Register", false);
		startActivityForResult(intent, REQUEST_LOGIN);
	}
	
	public void buttonPressed_register(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.putExtra("Registering", true);
		startActivityForResult(intent, REQUEST_LOGIN);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == REQUEST_LOGIN) {
			if (resultCode == RESULT_OK) {
				startActivity(new Intent(this, NavDrawerActivity.class));
				finish();
			}
		}
	}
}