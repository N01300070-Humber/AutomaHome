package ca.humbermail.n01300070.automahome.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.LoginDataSource;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.data.model.Home;
import ca.humbermail.n01300070.automahome.ui.CustomActivity;

public class NavDrawerActivity extends CustomActivity {
	
	private LoginDataSource loginDataSource;
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	
	private boolean dataSourcesInitialized = false;
	private final ArrayList<Home> homes = new ArrayList<>();
	private OnHomeSpinnerItemChangedListener homeSpinnerListener = null;
	
	private AppBarConfiguration mAppBarConfiguration;
	private TextView navHeaderTextView;
	private Spinner homeSpinner;
	
	private ArrayAdapter<String> homesAdapter;
	
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
		homeSpinner = navigationView.getHeaderView(0).findViewById(R.id.spinner_home);
		
		homeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				homeSpinner_ItemSelected(adapterView, view, i, l);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				homeSpinner_NothingSelected(adapterView);
			}
		});
		
		homesAdapter = new ArrayAdapter<>(this, R.layout.text_view_auto_complete_label);
		homeSpinner.setAdapter(homesAdapter);
		
		setRealtimeDatabaseDataSource(new RealtimeDatabaseDataSource());
		realtimeDatabaseDataSource = getRealtimeDatabaseDataSource();
		
		setLoginDataSource(new LoginDataSource(new LoginDataSource.LoginStateListener() {
			@Override
			public void onLoginStateChanged(@NonNull FirebaseAuth firebaseAuth, boolean loggedIn) {
				Log.d("NavDrawerActivity", "detected login state change. Value is now " + loggedIn);
				if (loggedIn) {
					// TODO: Fix bug where name is not obtained correctly after register or login
					updateUI(Objects.requireNonNull(firebaseAuth.getCurrentUser()));
					setOnHomeValuesChangeListener();
					dataSourcesInitialized = true;
				} else {
					onLoggedOut();
				}
			}
		}));
		loginDataSource = getLoginDataSource();
		
		realtimeDatabaseDataSource.setCurrentHomeId("testhome"); // TODO: Replace with real data
	}
	
	@Override
	protected void onStart() {
		Log.d("NavDrawerActivity", "onStart called");
		super.onStart();
		
		if (dataSourcesInitialized) {
			setOnHomeValuesChangeListener();
		}
	}
	
	private void setOnHomeValuesChangeListener() {
		Log.d("NavDrawerActivity", "setOnHomeValuesChangeListener called");
		realtimeDatabaseDataSource.onHomeValuesChange(loginDataSource).observe(this, new Observer<List<Home>>() {
			@Override
			public void onChanged(List<Home> homes) {
				setHomesDataList(homes);
				setHomeSpinnerHome(realtimeDatabaseDataSource.getCurrentHomeId());
			}
		});
	}
	
	public void homeSpinner_ItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
		Log.d("NavDrawerActivity", "homeSpinner_ItemSelected called");
		
		Home selectedHome = homes.get(position);
		realtimeDatabaseDataSource.setCurrentHomeId(selectedHome.getId());
		
		if (homeSpinnerListener != null) {
			homeSpinnerListener.onHomeSpinnerItemChanged(adapterView, view, position, id);
		}
	}
	
	public void homeSpinner_NothingSelected(AdapterView<?> adapterView) {
		Log.d("NavDrawerActivity", "homeSpinner_NothingSelected called");
		handleCurrentHomeInaccessible();
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
	
	private void setHomesDataList(List<Home> homes) {
		Log.d("NavDrawerActivity", "setHomesDataList called");
		this.homes.clear();
		homesAdapter.clear();
		
		for (Home home : homes) {
			homesAdapter.add(home.getName());
		}
		
		this.homes.addAll(homes);
		homesAdapter.notifyDataSetChanged();
	}
	
	private void setHomeSpinnerHome(String homeId) {
		Log.d("NavDrawerActivity", "setHomeSpinnerHome called");
		int currentHomePosition = -1;
		
		for (int i = 0; i < homes.size(); i++) {
			if (homes.get(i).getId().equals(homeId)) {
				currentHomePosition = i;
				break;
			}
		}
		
		if (currentHomePosition == -1) {
			handleCurrentHomeInaccessible();
		} else {
			homeSpinner.setSelection(currentHomePosition);
		}
	}
	
	private void handleCurrentHomeInaccessible() {
		Log.d("NavDrawerActivity", "handleCurrentHomeInaccessible called");
		if (homes.size() > 0) {
			Toast.makeText(this, R.string.error_current_home_inaccessible, Toast.LENGTH_SHORT).show();
			realtimeDatabaseDataSource.setCurrentHomeId(homes.get(0).getId());
		} else {
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
			
			dialogBuilder.setTitle(R.string.prompt_no_homes_title);
			dialogBuilder.setMessage(R.string.prompt_no_homes_message);
			
			dialogBuilder.setPositiveButton(R.string.create_home, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					showCreateNewHomeDialog();
				}
			});
			dialogBuilder.setNeutralButton(R.string.check_invites, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					// TODO: Check invites
				}
			});
			
			dialogBuilder.show();
		}
	}
	
	public void showCreateNewHomeDialog() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle(R.string.prompt_create_home_title);
		dialogBuilder.setMessage(R.string.prompt_create_home_message);
		
		final EditText nameEditText = new EditText(this);
		nameEditText.setInputType(InputType.TYPE_CLASS_TEXT);
		dialogBuilder.setView(nameEditText);
		
		dialogBuilder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				realtimeDatabaseDataSource.addHome(nameEditText.getText().toString(), loginDataSource);
			}
		});
		dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.cancel();
			}
		});
		
		dialogBuilder.show();
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		Log.d("NavDrawerActivity", "onCreateOptionsMenu called");
		getMenuInflater().inflate(R.menu.nav_drawer, menu);
		return true;
	}*/
	
	@Override
	protected void onStop() {
		Log.d("NavDrawerActivity", "onStop called");
		super.onStop();
		
		realtimeDatabaseDataSource.removeHomesValueChangesListener();
	}
	
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
	
	
	public interface OnHomeSpinnerItemChangedListener {
		void onHomeSpinnerItemChanged(AdapterView<?> adapterView, View view, int position, long id);
	}
	
	public void setHomesSpinnerPosition(int position) {
		Log.d("NavDrawerActivity", "setHomesSpinnerPosition called");
		homeSpinner.setSelection(position);
	}
	
	public int getHomesSpinnerPosition() {
		Log.d("NavDrawerActivity", "getHomesSpinnerPosition called");
		return homeSpinner.getSelectedItemPosition();
	}
	
	public ArrayAdapter<String> getHomesAdapter() {
		Log.d("NavDrawerActivity", "getHomesAdapter called");
		return homesAdapter;
	}
	
	public void setOnHomeSpinnerItemChangedListener(OnHomeSpinnerItemChangedListener itemChangedListener) {
		Log.d("NavDrawerActivity", "setOnHomeSpinnerItemChangedListener called");
		homeSpinnerListener = itemChangedListener;
	}
	
	public void removeOnHomeSpinnerItemChangedListener() {
		Log.d("NavDrawerActivity", "removeOnHomeSpinnerItemChangedListener called");
		homeSpinnerListener = null;
	}
	
	public void removeHomeListeners() {
		removeOnHomeSpinnerItemChangedListener();
		realtimeDatabaseDataSource.removeHomesValueChangesListener();
	}
	
	@Override
	public void removeAllListeners() {
		removeHomeListeners();
		super.removeAllListeners();
	}
}