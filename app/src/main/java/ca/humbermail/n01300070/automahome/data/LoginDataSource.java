package ca.humbermail.n01300070.automahome.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.concurrent.Executor;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
	
	private final FirebaseAuth authentication = FirebaseAuth.getInstance();
	
	private FirebaseUser currentUser;
	
	ArrayList<LoginStateListener> loginStateListeners = new ArrayList<>(1);
	FirebaseAuth.AuthStateListener authStateListener;
	
	public interface LoginStateListener {
		void onLoginStateChanged(@NonNull FirebaseAuth auth, boolean loggedIn);
	}
	
	
	public LoginDataSource(final LoginStateListener loginStateListener) {
		Log.d("LoginDataSource", "constructor called");
		authStateListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				currentUser = firebaseAuth.getCurrentUser();
				for (LoginStateListener listener : loginStateListeners) {
					listener.onLoginStateChanged(firebaseAuth, currentUser != null);
				}
			}
		};
		
		addLoginStateListener(loginStateListener);
		authentication.addAuthStateListener(authStateListener);
	}
	
	public void addLoginStateListener(LoginStateListener loginStateListener) {
		this.loginStateListeners.add(loginStateListener);
	}
	
	public void removeLoginStateListener(LoginStateListener loginStateListener) {
		this.loginStateListeners.remove(loginStateListener);
	}
	
	public void removeAllLoginStateListeners() {
		Log.d("LoginDataSource", "removeAllLoginStateListeners called");
		this.loginStateListeners = new ArrayList<>();
	}
	
	public void removeAllListeners() {
		Log.d("LoginDataSource", "removeAllListeners called");
		removeAllLoginStateListeners();
		authentication.removeAuthStateListener(authStateListener);
	}
	
//	public boolean isLoggedIn() {
//		return currentUser != null;
//	}
	
//	public void updateCurrentUser() {
//		currentUser = authentication.getCurrentUser();
//	}
	
	public void login(Executor executor, String emailAddress, String password,
					  OnCompleteListener<AuthResult> onCompleteListener) {
		Log.d("LoginDataSource", "login called");
		
		authentication.signInWithEmailAndPassword(emailAddress, password)
				.addOnCompleteListener(executor, onCompleteListener);
	}
	
	public void register(Executor executor, String emailAddress, String password,
						 OnCompleteListener<AuthResult> onCompleteListener) {
		Log.d("LoginDataSource", "register called");
		
		authentication.createUserWithEmailAndPassword(emailAddress, password)
				.addOnCompleteListener(executor, onCompleteListener);
	}
	
	public void logout() {
		Log.d("LoginDataSource", "logout called");
		
		authentication.signOut();
	}
	
	public void setDisplayName(String displayName) {
		Log.d("LoginDataSource", "setDisplayName called");
		currentUser.updateProfile(
				new UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
		);
	}
	
	public void deleteAccount(RealtimeDatabaseDataSource realtimeDatabaseDataSource) {
		Log.d("LoginDataSource", "deleteAccount called");
		
		realtimeDatabaseDataSource.removeCurrentUser(this);
		currentUser.delete();
	}
	
	public String getUserID() {
		return currentUser.getUid();
	}
	
	public String getDisplayName() {
		return currentUser.getDisplayName();
	}
	
	public String getEmailAddress() {
		return currentUser.getEmail();
	}
}