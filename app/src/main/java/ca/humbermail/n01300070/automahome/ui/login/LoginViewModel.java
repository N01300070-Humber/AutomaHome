package ca.humbermail.n01300070.automahome.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import ca.humbermail.n01300070.automahome.data.LoginRepository;
import ca.humbermail.n01300070.automahome.data.Result;
import ca.humbermail.n01300070.automahome.data.model.LoggedInUser;
import ca.humbermail.n01300070.automahome.R;

public class LoginViewModel extends ViewModel {
	
	private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
	private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
	private final LoginRepository loginRepository;
	
	LoginViewModel(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}
	
	LiveData<LoginFormState> getLoginFormState() {
		return loginFormState;
	}
	
	LiveData<LoginResult> getLoginResult() {
		return loginResult;
	}
	
	public void login(String username, String password) {
		// can be launched in a separate asynchronous job
		Result<LoggedInUser> result = loginRepository.login(username, password);
		
		if (result instanceof Result.Success) {
			LoggedInUser loggedInUser = ((Result.Success<LoggedInUser>) result).getData();
			loginResult.setValue(new LoginResult(new LoggedInUserView(
					loggedInUser.getEmailAddress(), loggedInUser.getFirstName(),
					loggedInUser.getLastName() )));
		} else {
			loginResult.setValue(new LoginResult(R.string.error_login_failed));
		}
	}
	
	public void loginDataChanged(String username, String password) {
		if (!isEmailAddressValid(username)) {
			loginFormState.setValue(new LoginFormState(R.string.error_email_invalid, null));
		} else if (!isPasswordValid(password)) {
			loginFormState.setValue(new LoginFormState(null, R.string.error_password_too_short));
		} else {
			loginFormState.setValue(new LoginFormState(true));
		}
	}
	
	// A placeholder emailAddress validation check
	private boolean isEmailAddressValid(String emailAddress) {
		if (emailAddress == null) {
			return false;
		}
		if (emailAddress.contains("@")) {
			return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
		} else {
			return !emailAddress.trim().isEmpty();
		}
	}
	
	// A placeholder password validation check
	private boolean isPasswordValid(String password) {
		return password != null && password.trim().length() > 5;
	}
}