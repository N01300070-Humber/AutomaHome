package ca.humbermail.n01300070.automahome;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

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

public class NavDrawerActivity extends AppCompatActivity  {

	private Spinner spnList;
	
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
		Log.d("myTagDebug", "Mydebug");
		System.out.println("inviteUserBtn_Clicked");
		/*Toast.makeText(this, "Invite User Button Clicked", Toast.LENGTH_SHORT).show();
		Intent intent0 = new Intent(this, NavDrawerActivity.class);
		startActivity(intent0);*/

		/*if (findViewById(R.id.nav_host_fragment) != null) {
			// Set the Main Fragment
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.nav_host_fragment, inviteUserFragment)
					.addToBackStack(null)
					.commit();
			getSupportFragmentManager().executePendingTransactions();
		}*/
	}

	// Home fragment event handlers
	/**
	 * onClick event handler for AddNetworkButton
	 * @param view Source view
	 */
	ImageButton addNetworkButton;

	public void addNetworkBtn_Clicked(View view){

		/*if (findViewById(R.id.nav_host_fragment) != null) {
			// Set the Main Fragment
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.nav_host_fragment, addNetworkFragment )
					.addToBackStack(null)
					.commit();
			getSupportFragmentManager().executePendingTransactions();
		}*/
	}

	// Home fragment event handlers
	/**
	 * onClick event handler for deleteHomeButton
	 * @param view Source view
	 */
	Button deleteHomeButton;

	public void deleteHomeBtn_Clicked(View view){

		if (findViewById(R.id.nav_host_fragment) != null) {
			// Set the Main Fragment
			/*getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.nav_host_fragment, deleteHomeFragment )
					.addToBackStack(null)
					.commit();
			getSupportFragmentManager().executePendingTransactions();*/
		}
	}
	
	// Settings fragment event handlers
	/**
	 * onClick event handler for general setting Button
	 * @param view Source view
	 */
	ImageButton generalSettingButton;

	public void generalBtn_Clicked(View view){

		if (findViewById(R.id.nav_host_fragment) != null) {
			// Set the Main Fragment
			/*getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.nav_host_fragment, settingFragment )
					.addToBackStack(null)
					.commit();
			getSupportFragmentManager().executePendingTransactions();*/
		}
	}

	ImageButton celsiusButton;

	public void celsiusButton_Clicked(View view){

		if (findViewById(R.id.nav_host_fragment) != null) {
			// Set the Main Fragment
			/*getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.nav_host_fragment, settingFragment )
					.addToBackStack(null)
					.commit();
			getSupportFragmentManager().executePendingTransactions();*/
		}
	}

	ImageButton fahrenheitButton;

	public void fahrenheitButton_Clicked(View view){

		if (findViewById(R.id.nav_host_fragment) != null) {
			// Set the Main Fragment
			/*getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.nav_host_fragment, settingFragment )
					.addToBackStack(null)
					.commit();
			getSupportFragmentManager().executePendingTransactions();*/
		}
	}

	ImageButton accountSettingButton;

	public void accountBtn_Clicked(View view){

		if (findViewById(R.id.nav_host_fragment) != null) {
			// Set the Main Fragment
			/*getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.nav_host_fragment, accountSettingFragment )
					.addToBackStack(null)
					.commit();
			getSupportFragmentManager().executePendingTransactions();*/
		}
	}

	Button confirmAccountButton;

	public void confirmBtn_Clicked(View view){

		if (findViewById(R.id.nav_host_fragment) != null) {
			// Set the Main Fragment
			/*getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.nav_host_fragment, confirmAccountFragment )
					.addToBackStack(null)
					.commit();
			getSupportFragmentManager().executePendingTransactions();*/
		}
	}

	Button signOutButton;

	public void signOutBtn_Clicked(View view){

		if (findViewById(R.id.nav_host_fragment) != null) {
			// Set the Main Fragment
			/*getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.nav_host_fragment, settingFragment )
					.addToBackStack(null)
					.commit();
			getSupportFragmentManager().executePendingTransactions();*/
		}
	}

	Button deleteAccountButton;

	public void deleteButton_onClick(View view){
		if (findViewById(R.id.nav_host_fragment) != null) {
			// Set the Main Fragment
			/*getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.nav_host_fragment, homeFragment )
					.addToBackStack(null)
					.commit();
			getSupportFragmentManager().executePendingTransactions();*/
		}
	}
}