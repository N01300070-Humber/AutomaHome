package ca.humbermail.n01300070.automahome.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.LoginDataSource;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.ui.CustomActivity;

public class NavDrawerActivity extends CustomActivity {
	
	private AppBarConfiguration mAppBarConfiguration;
	private TextView navHeaderTextView;
	private Spinner homeSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("NavDrawerActivity", "onCreate called");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nav_drawer);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		mAppBarConfiguration = new AppBarConfiguration.Builder(
				R.id.nav_favorites, R.id.nav_devices, R.id.nav_tasks, R.id.nav_home, R.id.nav_settings)
				.setOpenableLayout(drawer)
				.build();
		NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
		NavController navController = Objects.requireNonNull(navHostFragment).getNavController();
		NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
		NavigationUI.setupWithNavController(navigationView, navController);
		
		navHeaderTextView = navigationView.getHeaderView(0).findViewById(R.id.textView_navHeader);
		
		setRealtimeDatabaseDataSource(new RealtimeDatabaseDataSource());
		setLoginDataSource(new LoginDataSource(new LoginDataSource.LoginStateListener() {
			@Override
			public void onLoginStateChanged(@NonNull FirebaseAuth firebaseAuth, boolean loggedIn) {
				Log.d("NavDrawerActivity", "detected login state change. Value is now " + loggedIn);
				if (loggedIn) {
					updateUI(Objects.requireNonNull(firebaseAuth.getCurrentUser()));
				} else {
					onLoggedOut();
				}
			}
		}));
	}
	
	private void updateUI(FirebaseUser currentUser) {
		Log.d("NavDrawerActivity", "updateUI called");
		String displayName = currentUser.getDisplayName();
		if (displayName == null) {
			displayName = "NO NAME!";
		}
		navHeaderTextView.setText(displayName);
	}
	
	private void onLoggedOut() {
		Log.d("NavDrawerActivity", "onLoggedOut called");
		Log.d("NavDrawerActivity", "starting WelcomeActivity");
		startActivity(new Intent(this, WelcomeActivity.class));
		Log.d("NavDrawerActivity", "finishing");
		finish();
	}
	
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nav_drawer, menu);
		return true;
	}*/

	@Override
	public boolean onSupportNavigateUp() {
		Log.d("NavDrawerActivity", "onSupportNavigateUp called");
		NavController navController = Navigation
				.findNavController(this, R.id.nav_host_fragment);
		return NavigationUI.navigateUp(navController, mAppBarConfiguration)
				|| super.onSupportNavigateUp();
	}
	
	@Override
	public void finish() {
		Log.d("NavDrawerActivity", "finish called");
		super.finish();
	}
}