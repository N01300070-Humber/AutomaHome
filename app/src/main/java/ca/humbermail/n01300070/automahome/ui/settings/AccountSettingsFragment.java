package ca.humbermail.n01300070.automahome.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.WelcomeActivity;
import ca.humbermail.n01300070.automahome.data.PreferenceKeys;

public class AccountSettingsFragment extends Fragment {
	Context context;
	
	EditText firstNameEditText;
	EditText lastNameEditText;
	EditText emailEditText;
	EditText currentPasswordEditText;
	EditText newPasswordEditText;
	Button logoutButton;
	Button deleteAccountButton;
	Button confirmButton;
	
	public AccountSettingsFragment() {
		// Required empty public constructor
	}
	
	public static AccountSettingsFragment newInstance() {
		return new AccountSettingsFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_settings_account, container, false);
		context = getActivity().getApplicationContext();
		
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
		SharedPreferences.Editor loginInfoEditor = context.getSharedPreferences(
				PreferenceKeys.LOGIN, Context.MODE_PRIVATE).edit();
		loginInfoEditor.putBoolean(PreferenceKeys.LOGIN_LOGGED_IN, false);
		loginInfoEditor.putString(PreferenceKeys.LOGIN_FIRST_NAME, null);
		loginInfoEditor.putString(PreferenceKeys.LOGIN_LAST_NAME, null);
		loginInfoEditor.putString(PreferenceKeys.LOGIN_EMAIL_ADDRESS, null);
		loginInfoEditor.putString(PreferenceKeys.LOGIN_PASSWORD, null);
		if (!loginInfoEditor.commit()) {
			Toast.makeText(context, "Failed to properly logout", Toast.LENGTH_LONG).show(); // TODO: Fix hardcoded string
		} else {
			Toast.makeText(context, "Successfully logged out", Toast.LENGTH_LONG).show(); // TODO: Fix hardcoded string
			startActivity(new Intent(context, WelcomeActivity.class));
			getActivity().finish();
		}
	}
	
	private void deleteAccountButton_onClick(View view) {
		// TODO: delete user account
		SharedPreferences.Editor loginInfoEditor = context.getSharedPreferences(
				PreferenceKeys.LOGIN, Context.MODE_PRIVATE).edit();
		loginInfoEditor.putBoolean(PreferenceKeys.LOGIN_LOGGED_IN, false);
		loginInfoEditor.putString(PreferenceKeys.LOGIN_FIRST_NAME, null);
		loginInfoEditor.putString(PreferenceKeys.LOGIN_LAST_NAME, null);
		loginInfoEditor.putString(PreferenceKeys.LOGIN_EMAIL_ADDRESS, null);
		loginInfoEditor.putString(PreferenceKeys.LOGIN_PASSWORD, null);
		if (!loginInfoEditor.commit()) {
			Toast.makeText(context, "Failed to properly delete account", Toast.LENGTH_LONG).show(); // TODO: Fix hardcoded string
		} else {
			Toast.makeText(context, "Successfully deleted account", Toast.LENGTH_LONG).show(); // TODO: Fix hardcoded string
			startActivity(new Intent(context, WelcomeActivity.class));
			getActivity().finish();
		}
	}
	
	private void confirmButton_onClick(View view) {
		// TODO: save changes
		Toast.makeText(context, "Changes saved", Toast.LENGTH_SHORT).show(); // TODO: Fix hardcoded string
	}
}