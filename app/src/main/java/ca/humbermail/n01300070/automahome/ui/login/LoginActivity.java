package ca.humbermail.n01300070.automahome.ui.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import ca.humbermail.n01300070.automahome.R;

public class LoginActivity extends AppCompatActivity {
	
	private boolean registering;
	
	private TextInputLayout firstNameTextLayout;
	private TextInputLayout lastNameTextLayout;
	private TextInputLayout emailAddressTextLayout;
	private TextInputLayout passwordTextLayout;
	
	private TextInputEditText firstNameEditText;
	private TextInputEditText lastNameEditText;
	private TextInputEditText emailAddressEditText;
	private TextInputEditText passwordEditText;
	
	private ProgressBar loadingProgressBar;
	
	private Button confirmButton;
	private Button switchLoginButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		
		firstNameTextLayout = findViewById(R.id.textInputLayout_firstName);
		lastNameTextLayout = findViewById(R.id.textInputLayout_lastName);
		emailAddressTextLayout = findViewById(R.id.textInputLayout_email);
		passwordTextLayout = findViewById(R.id.textInputLayout_password);
		
		firstNameEditText = firstNameTextLayout.findViewById(R.id.textInputEditText_firstName);
		lastNameEditText = lastNameTextLayout.findViewById(R.id.textInputEditText_lastName);
		emailAddressEditText = emailAddressTextLayout.findViewById(R.id.textInputEditText_email);
		passwordEditText = passwordTextLayout.findViewById(R.id.textInputEditText_password);
		
		confirmButton = findViewById(R.id.button_confirm);
		
		loadingProgressBar = findViewById(R.id.loading);
		switchLoginButton = findViewById(R.id.button_switch_login);
		
		
		confirmButton.setEnabled(true); // TODO: Remove once field validity checking is handled when typing instead of on button press
		setLoginType(getIntent().getBooleanExtra("Registering", false));
	}
	
	/**
	 * Set activity to sign in mode or register mode
	 * @param registering True = register, False = sign in
	 */
	private void setLoginType(boolean registering) {
		this.registering = registering;
		
		if (registering) {
			setTitle(getString(R.string.title_activity_register));
			firstNameTextLayout.setVisibility(View.VISIBLE);
			lastNameTextLayout.setVisibility(View.VISIBLE);
			switchLoginButton.setText(R.string.button_question_have_account);
		} else {
			setTitle(getString(R.string.title_activity_login));
			firstNameTextLayout.setVisibility(View.GONE);
			lastNameTextLayout.setVisibility(View.GONE);
			switchLoginButton.setText(R.string.button_question_no_account);
		}
	}
	
	/**
	 * Handles back button onClick event
	 * @param item non-null MenuItem
	 * @return Boolean
	 */
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		finish();
		return true;
	}
	
	/**
	 * Handles switchLoginButton onClick event.
	 * @param view Source view.
	 */
	public void switchLoginButton_onClick(View view) {
		setLoginType(!registering);
	}
	
	/**
	 * Handles confirmButton onClick event.
	 * @param view Source view.
	 */
	public void confirmButton_onClick(View view) {
		String firstName;
		String lastName;
		String emailAddress;
		String password;
		
		boolean firstNameValid = false;
		boolean lastNameValid = false;
		boolean emailAddressValid = false;
		boolean passwordValid = false;
		
		// Set values of variables
		loadingProgressBar.setVisibility(View.VISIBLE);
		
		if (registering) {
			firstName = firstNameEditText.getText().toString();
			lastName = lastNameEditText.getText().toString();
		} else {
			firstName = "Jane";
			lastName = "Doe";
		}
		emailAddress = emailAddressEditText.getText().toString();
		password = passwordEditText.getText().toString();
		
		// Check input
		// TODO: Break checks into functions that can be called when typing
		if (registering) {
			if (TextUtils.isEmpty(firstName)) {
				firstNameEditText.setError(getString(R.string.error_required_field));
			}
			else {
				firstNameValid = true;
				firstNameEditText.setError(null);
			}
			
			if (TextUtils.isEmpty(lastName)) {
				lastNameEditText.setError(getString(R.string.error_required_field));
			}
			else {
				lastNameValid = true;
				lastNameEditText.setError(null);
			}
		}
		
		if (TextUtils.isEmpty(emailAddress)) {
			emailAddressEditText.setError(getString(R.string.error_required_field));
		}
		else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
			emailAddressEditText.setError(getString(R.string.error_email_invalid));
		}
		else {
			emailAddressValid = true;
			emailAddressEditText.setError(null);
		}
		
		if (TextUtils.isEmpty(password)) {
			passwordEditText.setError(getString(R.string.error_required_field));
		}
		else if (password.length() < 8 && registering) {
			passwordEditText.setError(getString(R.string.error_password_too_short));
		}
		else {
			passwordValid = true;
			passwordEditText.setError(null);
		}
		
		
		if ( !emailAddressValid && !passwordValid &&
				((!firstNameValid && !lastNameValid) || !registering) ) {
			loadingProgressBar.setVisibility(View.GONE);
			return;
		}
		
		
		// Attempt sign-in/register
		// TODO: Attempt to sign-in/register (After Milestone 2)
		
		// Successful sign-in/register
		SharedPreferences.Editor loginInfoEditor = getSharedPreferences(
				getString(R.string.Preference_Login), MODE_PRIVATE).edit();
		loginInfoEditor.putBoolean(getString(R.string.Preference_Login_LoggedIn), true);
		loginInfoEditor.putString(getString(R.string.Preference_Login_FirstName), firstName);
		loginInfoEditor.putString(getString(R.string.Preference_Login_LastName), lastName);
		loginInfoEditor.putString(getString(R.string.Preference_Login_EmailAddress), emailAddress);
		loginInfoEditor.putString(getString(R.string.Preference_Login_Password), password); // TODO: hash and encrypt password before storing OR replace with authorization token acquired during login attempt (After Milestone 2)
		if (!loginInfoEditor.commit()) {
			Toast.makeText(this, "Failed to save info", Toast.LENGTH_LONG).show();
			loadingProgressBar.setVisibility(View.GONE);
			return;
		}
		
		loadingProgressBar.setVisibility(View.GONE);
		
		setResult(Activity.RESULT_OK);
		finish();
	}
	
}
