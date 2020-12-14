package ca.humbermail.n01300070.automahome.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.LoginDataSource;
import ca.humbermail.n01300070.automahome.data.RealtimeDatabaseDataSource;
import ca.humbermail.n01300070.automahome.ui.main.NavDrawerActivity;

public class AccountSettingsFragment extends Fragment {
	private Context context;
	
	private NavDrawerActivity navDrawerActivity;
	private LoginDataSource loginDataSource;
	private RealtimeDatabaseDataSource realtimeDatabaseDataSource;
	
	private EditText firstNameEditText;
	private EditText lastNameEditText;
	private EditText emailEditText;
	private EditText currentPasswordEditText;
	private EditText newPasswordEditText;
	private Button logoutButton;
	private Button deleteAccountButton;
	private Button confirmButton;
	
	public AccountSettingsFragment() {
		// Required empty public constructor
	}
	
	public static AccountSettingsFragment newInstance() {
		return new AccountSettingsFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		Log.d("AccountSettingsFragment", "onCreateView called");
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_settings_account, container, false);
		context = requireActivity().getApplicationContext();
		
		navDrawerActivity = (NavDrawerActivity) requireActivity();
		loginDataSource = navDrawerActivity.getLoginDataSource();
		realtimeDatabaseDataSource = navDrawerActivity.getRealtimeDatabaseDataSource();
		
		firstNameEditText = root.findViewById(R.id.editText_firstName);
		lastNameEditText = root.findViewById(R.id.editText_lastName);
		emailEditText = root.findViewById(R.id.editText_email);
		currentPasswordEditText = root.findViewById(R.id.editText_currPassword);
		newPasswordEditText = root.findViewById(R.id.editText_newPassword);
		logoutButton = root.findViewById(R.id.button_signOut);
		deleteAccountButton = root.findViewById(R.id.button_deleteAccount);
		confirmButton = root.findViewById(R.id.button_confirm);
		
		logoutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				logoutButton_onClick(view);
			}
		});
		deleteAccountButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				deleteAccountButton_onClick(view);
			}
		});
		confirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				confirmButton_onClick(view);
			}
		});
		
		return root;
	}
	
	public void logoutButton_onClick(View view) {
		Log.d("AccountSettingsFragment", "logoutButton_onClick called");
		loginDataSource.logout();
	}
	
	private void deleteAccountButton_onClick(View view) {
		Log.d("AccountSettingsFragment", "deleteAccountButton_onClick called");
		
		navDrawerActivity.removeHomeListeners();
		loginDataSource.deleteAccount(realtimeDatabaseDataSource);
	}
	
	private void confirmButton_onClick(View view) {
		Log.d("AccountSettingsFragment", "confirmButton_onClick called");
		// TODO: save changes
		Toast.makeText(context, "Changes saved", Toast.LENGTH_SHORT).show(); // TODO: Fix hardcoded string
	}
}