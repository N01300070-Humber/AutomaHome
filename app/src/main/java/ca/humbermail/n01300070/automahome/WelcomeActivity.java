package ca.humbermail.n01300070.automahome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ca.humbermail.n01300070.automahome.ui.login.SignInActivity;
import ca.humbermail.n01300070.automahome.ui.login.RegisterActivity;

public class WelcomeActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
	}
	
	public void buttonPressed_signIn(View view) {
		startActivity(new Intent(this, SignInActivity.class));
	}
	
	public void buttonPressed_register(View view) {
		startActivity(new Intent(this, RegisterActivity.class));
	}
}