package ca.humbermail.n01300070.automahome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import ca.humbermail.n01300070.automahome.data.UserInfo;
import ca.humbermail.n01300070.automahome.ui.home.HomeFragment;
import ca.humbermail.n01300070.automahome.ui.home.InviteUserFragment;
import ca.humbermail.n01300070.automahome.ui.settings.AccountSetting;

public class NavDrawerActivity extends AppCompatActivity {
	
	private AppBarConfiguration mAppBarConfiguration;
	UserInfo userInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		
		userInfo = new UserInfo(this);
		
		TextView navHeaderTextView = navigationView.getHeaderView(0).findViewById(R.id.textView_navHeader);
		
		String fullName = userInfo.getFullName();
		if (fullName == null) {
			fullName = "ERROR!";
		}
		navHeaderTextView.setText(fullName);
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nav_drawer, menu);
		return true;
	}*/

	@Override
	public boolean onSupportNavigateUp() {
		NavController navController = Navigation
				.findNavController(this, R.id.nav_host_fragment);
		return NavigationUI.navigateUp(navController, mAppBarConfiguration)
				|| super.onSupportNavigateUp();
	}

	// Home fragment event handlers
	/**
	 * onClick event handler for InviteUserButton
	 * @param view Source view
	 */
	ImageButton inviteUserButton;

	public void inviteUserBtn_Clicked(View view){
		Toast.makeText(this, "Invite User Button Clicked", Toast.LENGTH_SHORT).show();
		Intent intent0 = new Intent(this, NavDrawerActivity.class);
		startActivity(intent0);
	}



	
	// Settings fragment event handlers
	/**
	 * onClick event handler for logoutButton
	 * @param view Source view
	 */
	public void logoutButton_onClick(View view) {
		SharedPreferences.Editor loginInfoEditor = getSharedPreferences(
				getString(R.string.Preference_Login), MODE_PRIVATE ).edit();
		loginInfoEditor.putBoolean(getString(R.string.Preference_Login_LoggedIn), false);
		loginInfoEditor.putString(getString(R.string.Preference_Login_FirstName), null);
		loginInfoEditor.putString(getString(R.string.Preference_Login_LastName), null);
		loginInfoEditor.putString(getString(R.string.Preference_Login_EmailAddress), null);
		loginInfoEditor.putString(getString(R.string.Preference_Login_Password), null);
		if (!loginInfoEditor.commit()) {
			Toast.makeText(this, "Failed to properly logout", Toast.LENGTH_LONG).show(); // TODO: Fix hardcoded string
		}
		else {
			Toast.makeText(this, "Successfully logged out", Toast.LENGTH_LONG).show(); // TODO: Fix hardcoded string
			startActivity(new Intent(this, WelcomeActivity.class));
			finish();
		}
	}
}