package ca.humbermail.n01300070.automahome.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ca.humbermail.n01300070.automahome.data.LoginDataSource;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;

public class CustomActivity extends AppCompatActivity {
	LoginDataSource loginDataSource;
	RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO: set selected theme
		super.onCreate(savedInstanceState);
	}
	
	public LoginDataSource getLoginDataSource() {
		return loginDataSource;
	}
	
	public void setLoginDataSource(LoginDataSource loginDataSource) {
		this.loginDataSource = loginDataSource;
	}
	
	public RealtimeDatabaseDataSource getRealtimeDatabaseDataSource() {
		return realtimeDatabaseDataSource;
	}
	
	public void setRealtimeDatabaseDataSource(RealtimeDatabaseDataSource realtimeDatabaseDataSource) {
		this.realtimeDatabaseDataSource = realtimeDatabaseDataSource;
	}
}
