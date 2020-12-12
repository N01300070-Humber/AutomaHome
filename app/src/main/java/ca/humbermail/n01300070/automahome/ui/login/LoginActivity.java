package ca.humbermail.n01300070.automahome.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.data.LoginDataSource;
import ca.humbermail.n01300070.automahome.ui.CustomActivity;

public class LoginActivity extends CustomActivity {
	
	private boolean registering;
	
	LoginDataSource loginDataSource;
	
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
		Log.d("LoginActivity", "onCreate called");
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
	 *
	 * @param registering True = register, False = sign in
	 */
	private void setLoginType(boolean registering) {
		this.registering = registering;
		
		if (registering) {
			setTitle(getString(R.string.title_activity_register));
			firstNameTextLayout.setVisibility(View.VISIBLE);
			lastNameTextLayout.setVisibility(View.VISIBLE);
			confirmButton.setText(getString(R.string.button_register));
			switchLoginButton.setText(R.string.button_question_have_account);
		} else {
			setTitle(getString(R.string.title_activity_sign_in));
			firstNameTextLayout.setVisibility(View.GONE);
			lastNameTextLayout.setVisibility(View.GONE);
			confirmButton.setText(getString(R.string.button_sign_in));
			switchLoginButton.setText(R.string.button_question_no_account);
		}
	}
	
	/**
	 * Handles back button onClick event
	 *
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
	 *
	 * @param view Source view.
	 */
	public void switchLoginButton_onClick(View view) {
		setLoginType(!registering);
	}
	
	/**
	 * Handles confirmButton onClick event.
	 *
	 * @param view Source view.
	 */
	public void confirmButton_onClick(View view) {
		Log.d("LoginActivity", "confirmButton_onClick called");
		
		String firstName;
		String lastName;
		final String emailAddress;
		final String password;
		
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
		// TODO: Break input checks into functions that can be called when typing
		if (registering) {
			if (TextUtils.isEmpty(firstName)) {
				firstNameEditText.setError(getString(R.string.error_required_field));
			} else {
				firstNameValid = true;
				firstNameEditText.setError(null);
			}
			
			if (TextUtils.isEmpty(lastName)) {
				lastNameEditText.setError(getString(R.string.error_required_field));
			} else {
				lastNameValid = true;
				lastNameEditText.setError(null);
			}
		}
		
		if (TextUtils.isEmpty(emailAddress)) {
			emailAddressEditText.setError(getString(R.string.error_required_field));
		} else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
			emailAddressEditText.setError(getString(R.string.error_email_invalid));
		} else {
			emailAddressValid = true;
			emailAddressEditText.setError(null);
		}
		
		if (TextUtils.isEmpty(password)) {
			passwordEditText.setError(getString(R.string.error_required_field));
		} else if (password.length() < 8 && registering) {
			passwordEditText.setError(getString(R.string.error_password_too_short));
		} else {
			passwordValid = true;
			passwordEditText.setError(null);
		}
		
		
		if (!emailAddressValid && !passwordValid &&
				((!firstNameValid && !lastNameValid) || !registering)) {
			loadingProgressBar.setVisibility(View.GONE);
			return;
		}
		
		
		// Attempt sign-in/register
		final String displayName = (firstName + " " + lastName).trim();
		setLoginDataSource(new LoginDataSource(new LoginDataSource.LoginStateListener() {
			@Override
			public void onLoginStateChanged(@NonNull FirebaseAuth auth, boolean loggedIn) {
				Log.d("LoginActivity", "detected login state change. Value is now " + loggedIn);
				if (!loggedIn) {
					attemptLogin(emailAddress, password, displayName);
				}
			}
		}));
		loginDataSource = getLoginDataSource();
	}
	
	private void attemptLogin(String emailAddress, String password, final String displayName) {
		Log.d("LoginActivity", "attemptLogin called");
		if (registering) {
			Log.d("LoginActivity", "registering for new account");
			loginDataSource.register(this.getMainExecutor(), emailAddress, password,
					new OnCompleteListener<AuthResult>() {
						@Override
						public void onComplete(@NonNull Task<AuthResult> task) {
							Log.d("LoginActivity", "register task called");
							if (task.isSuccessful()) {
								Log.d("LoginActivity", "register task successful");
								loginDataSource.setDisplayName(displayName);
								// TODO: Add user info to users in database
								loginFinished();
							} else {
								Log.d("LoginActivity", "register task failed");
								Objects.requireNonNull(task.getException()).printStackTrace();
								passwordEditText.setError(getString(R.string.error_registration_failed, task.getException().toString()));
								loadingProgressBar.setVisibility(View.GONE);
							}
							Log.d("LoginActivity", "register task finished");
						}
					});
		} else {
			Log.d("LoginActivity", "logging in to existing account");
			loginDataSource.login(this.getMainExecutor(), emailAddress, password,
					new OnCompleteListener<AuthResult>() {
						@Override
						public void onComplete(@NonNull Task<AuthResult> task) {
							Log.d("LoginActivity", "sign in task called");
							if (task.isSuccessful()) {
								Log.d("LoginActivity", "sign in task successful");
								loginFinished();
							} else {
								Log.d("LoginActivity", "sign in task failed");
								Objects.requireNonNull(task.getException()).printStackTrace();
								passwordEditText.setError(getString(R.string.error_login_failed, task.getException().toString()));
								loadingProgressBar.setVisibility(View.GONE);
							}
							Log.d("LoginActivity", "sign in task finished");
						}
					});
		}
	}
	
	private void loginFinished() {
		// Successful sign-in/register
		Log.d("LoginActivity", "loginFinished called");
		loadingProgressBar.setVisibility(View.GONE);
		
		Log.d("LoginActivity", "closing LoginActivity");
		setResult(Activity.RESULT_OK);
		finish();
	}
	
	@Override
	public void finish() {
		Log.d("LoginActivity", "finish called");
		super.finish();
	}
}
